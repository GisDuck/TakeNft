const inventoryEl = document.getElementById('inventory');
    const confirmButton = document.getElementById('confirm-button');
    const selectedNFTs = new Set();

    const mockInventory = [
      { id: 1, imgUrl: '/img/nft1.png' },
      { id: 2, imgUrl: '/img/nft2.png' },
      { id: 3, imgUrl: '/img/nft3.png' },
      { id: 4, imgUrl: '/img/nft4.png' },
      { id: 5, imgUrl: '/img/nft5.png' },
      { id: 6, imgUrl: '/img/nft6.png' },
      { id: 7, imgUrl: '/img/nft7.png' },
      { id: 2, imgUrl: '/img/nft2.png' },
      { id: 3, imgUrl: '/img/nft3.png' },
      { id: 4, imgUrl: '/img/nft4.png' },
      { id: 5, imgUrl: '/img/nft5.png' },
      { id: 6, imgUrl: '/img/nft6.png' },
      { id: 7, imgUrl: '/img/nft7.png' },
      { id: 2, imgUrl: '/img/nft2.png' },
      { id: 3, imgUrl: '/img/nft3.png' },
      { id: 4, imgUrl: '/img/nft4.png' },
      { id: 5, imgUrl: '/img/nft5.png' },
      { id: 6, imgUrl: '/img/nft6.png' },
      { id: 7, imgUrl: '/img/nft7.png' }
    ];

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
        };
        inventoryEl.appendChild(card);
      });
    }

    renderInventory();

    confirmButton.onclick = () => {
      console.log('Selected NFTs:', Array.from(selectedNFTs));
      // TODO: send via WebSocket
    };