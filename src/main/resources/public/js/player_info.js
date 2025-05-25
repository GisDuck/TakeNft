document.addEventListener("DOMContentLoaded", () => {
  const playerAvatar = document.getElementById("player-avatar"),
  username = document.getElementById("username"),
  inventoryGrid = document.querySelector('.inventory-grid'),
  inventorySection = document.getElementById("inventory-section")

fetch("/data/player_info.json")
  .then(res => res.json())
  .then(data => {
    playerAvatar.src = data.playerAvatarUrl;
    username.textContent = data.username;

    const ammountNft = document.createElement('h3');
    ammountNft.textContent = "Ammount nft: " + data.ammountNft;
    inventorySection.prepend(ammountNft)
  
    data.inventory.forEach(item => {
      const card = document.createElement('div');
      card.classList.add('nft-card');
    
      const img = document.createElement('img');
      img.src = item.imgUrl;
      img.alt = 'NFT';
    
      card.appendChild(img);
      inventoryGrid.appendChild(card);
    });
    
  })
  .catch(err => {
    console.error("Ошибка загрузки данных пользователя:", err);
    title.textContent = "Ошибка загрузки данных";
  });
});



  document.getElementById('duel-button').addEventListener('click', () => {
    alert('Приглашение в дуэль отправлено!');
  });
  

  document.getElementById('friend-button').addEventListener('click', () => {
    alert('Запрос на дружбу отправлен!');
  });