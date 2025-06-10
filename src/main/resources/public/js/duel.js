const inventoryEl = document.getElementById('inventory');
const opponentSelectionEl = document.getElementById('opponent-selection');
const confirmButton = document.getElementById('confirm-button');
const opponentNameEl = document.getElementById('opponent-name');
const opponentAvatarEl = document.getElementById('opponent-avatar');
const selectedNFTs = new Set();

const roomId = new URLSearchParams(window.location.search).get('roomId');
let stompClient = null;
let currentPlayer = null;

const mockInventory = [
  { id: 1, imgUrl: '/img/nft1.png' },
  { id: 2, imgUrl: '/img/nft2.png' },
  { id: 3, imgUrl: '/img/nft3.png' },
  { id: 4, imgUrl: '/img/nft4.png' },
  { id: 5, imgUrl: '/img/nft5.png' },
  { id: 6, imgUrl: '/img/nft6.png' },
  { id: 7, imgUrl: '/img/nft7.png' }
];

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

function connectWebSocket() {
  const socket = new SockJS('/duel-ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, () => {
    stompClient.subscribe(`/topic/duel/${roomId}`, message => {
      const msg = JSON.parse(message.body);

      if (msg.type === 'nft_select' && msg.player !== currentPlayer) {
        renderOpponentSelection(msg.nfts);
      } else if ((msg.type === 'joined' || msg.type === 'opponent_rejoined') && msg.opponent !== currentPlayer) {
        updateOpponentInfo(msg.opponentName || 'Opponent', msg.opponentAvatar || '/img/default_avatar.png');
      }
    });
  });
}

fetch('/api/ton-proof/status')
  .then(res => res.json())
  .then(data => {
    if (data.loggedIn) {
      currentPlayer = data.address;
      connectWebSocket();
    } else {
      alert('Please login via wallet.');
    }
  });

renderInventory();
