document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("rooms-container");
  

    fetch("/data/duel_rooms.json")
      .then(res => res.json())
      .then(data => {
       
        if (data.invites.length > 0) {
          const title = document.createElement("div");
          title.classList = "subtitle"
          title.textContent = "Invitation to a duel";
          container.appendChild(title);

            data.invites.forEach(room => {
                container.appendChild(createRoomCard(room, true));
              });
  
         
          const sep = document.createElement("div");
          sep.id = "invite-separator";
          container.appendChild(sep);
        }
  
    
        const title = document.createElement("div");
          title.classList = "subtitle"
          title.textContent = "Room list";
          container.appendChild(title);

        if (data.rooms.length > 0) {
        data.rooms.forEach(room => {
        const roomCard = createRoomCard(room, false);
        container.appendChild(roomCard);
        });
      } else {
          const title = document.createElement("p");
          title.textContent = "No rooms yet...";
          container.appendChild(title);
      }

        }
      )
      .catch(err => {
        console.error("Ошибка загрузки комнат дуэлей:", err);
        container.textContent = "Не удалось загрузить список комнат.";
      });
  

    function createRoomCard(room, isInvite) {
      const card = document.createElement("div");
      card.classList.add("room-card");
      if (isInvite) card.classList.add("duel-invite");
  
      
      const header = document.createElement("div");
      header.classList.add("room-header");
   
      const img = document.createElement("img");
      img.src = room.playerAvatarUrl;
      img.alt = "avatar";
      header.appendChild(img);
  
      
      const info = document.createElement("div");
      info.classList.add("info");
      const name = document.createElement("div");
      name.classList.add("name");
      name.textContent = room.username;
      info.appendChild(name);
  
      if (isInvite) {
        const join = document.createElement("div");
        join.classList.add("join-text");
        join.textContent = "Join!";
        info.appendChild(join);
      }
      header.appendChild(info);
      card.appendChild(header);
  
      const nfts = document.createElement("div");
      nfts.classList.add("nfts");
      (room.nfts || []).forEach(nft => {
        const idx = document.createElement("img");
        idx.src = nft.imgUrl;
        idx.alt = "";
        nfts.appendChild(idx);
      });
      card.appendChild(nfts);
  
      return card;
    }
  });