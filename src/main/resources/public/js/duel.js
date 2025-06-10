const inventoryEl = document.getElementById('inventory');
const opponentSelectionEl = document.getElementById('opponent-selection');
const confirmButton = document.getElementById('confirm-button');
const opponentNameEl = document.getElementById('opponent-name');
const opponentAvatarEl = document.getElementById('opponent-avatar');
const selectedNFTs = new Set();

const roomId = new URLSearchParams(window.location.search).get('roomId');
let stompClient = null;
let currentPlayer = null;
let mockInventory = null;

fetch('/api/duel/inventory')
      .then(response => {
        if (!response.ok) {
          throw new Error(`Ошибка в получении инвентаря`);
        }
        // читаем тело как строку
        return response.text();
      })
      .then(inventory => {
      mockInventory = inventory

    });

function sendSelection() {
  if (!stompClient) return;
  const nfts = mockInventory.filter(nft => selectedNFTs.has(nft.id));
  stompClient.send(`/app/duel/${roomId}/select-nft`, {}, JSON.stringify({ nfts }));
}

function renderInventory() {
  inventoryEl.innerHTML = '';
  mockInventory.forEach(nft => {
    const card = document.createElement('div');
    card.className = 'nft-card';
    card.dataset.id = nft.id;

    const img = document.createElement('img');
    img.src = nft.imgUrl;
    img.alt = 'NFT';

    card.appendChild(img);
    card.onclick = () => {
      if (selectedNFTs.has(nft.id)) {
        selectedNFTs.delete(nft.id);
        card.classList.remove('selected');
      } else {
        selectedNFTs.add(nft.id);
        card.classList.add('selected');
      }
      sendSelection();
    };
    inventoryEl.appendChild(card);
  });
}

function renderOpponentSelection(nfts) {
  opponentSelectionEl.innerHTML = '';
  nfts.forEach(nft => {
    const card = document.createElement('div');
    card.className = 'nft-card';
    const img = document.createElement('img');
    img.src = nft.imgUrl;
    img.alt = 'NFT';
    card.appendChild(img);
    opponentSelectionEl.appendChild(card);
  });
}

function updateOpponentInfo(name, avatarUrl) {
  opponentNameEl.textContent = name;
  opponentAvatarEl.src = avatarUrl;
}

renderInventory();
