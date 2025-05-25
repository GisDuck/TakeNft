document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("friends-container");
  
    fetch("/data/friends.json")
      .then(res => res.json())
      .then(data => {
        if (data.invites.length > 0) {
        const title = document.createElement("p");
          title.textContent = "Bee friends!";
          container.appendChild(title);

          data.invites.forEach(inv => {
            const card = createCard(inv, true);
            container.appendChild(card);
          });
        
          const sep = document.createElement("div");
          sep.id = "invite-separator";
          container.appendChild(sep);
        }
  
      
          if (data.friends.length > 0) {
            const title = document.createElement("p");
            title.textContent = "Your Friends";
          container.appendChild(title);
        data.friends.forEach(fr => {
        const card = createCard(fr, false);
        container.appendChild(card);
        });
    } else {
        const title = document.createElement("p");
        title.textContent = "You no have any friends...";
          container.appendChild(title);
    }
             
        })
      .catch(err => {
        console.error("Ошибка загрузки списка друзей:", err);
        container.textContent = "Ошибка загрузки данных.";
      });
  
    function createCard(item, isInvite) {
      const card = document.createElement("div");
      card.className = "friend-card";
      if (isInvite) {
        card.className = "friend-card invite";
      }
  
      
      const info = document.createElement("div");
      info.className = "friend-info";
      const img = document.createElement("img");
      img.src = item.avatar;
      img.alt = "avatar";
      const name = document.createElement("div");
      name.className = "name";
      name.textContent = item.username;
      const count = document.createElement("div");
      count.className = "ammount-nft";
      count.textContent = "ammount nft: " + item.ammountNft;
      info.append(img, name, count);
  
    
      const actions = document.createElement("div");
      actions.className = "friend-actions";
      if (isInvite) {
        const acceptBtn = document.createElement("button");
        acceptBtn.textContent = "Add friend";
        const ignoreBtn = document.createElement("button");
        ignoreBtn.textContent = "Ignore";
        actions.append(acceptBtn, ignoreBtn);
      } else {
        const duelBtn = document.createElement("button");
        duelBtn.textContent = "Invite in Duel";
        actions.append(duelBtn);
      }
  
      card.append(info, actions);
      return card;
    }
  });
  