const roomsContainer = document.getElementById("rooms-container");
const invitesContainer = document.getElementById("invites-container");

function renderRooms(rooms) {
  roomsContainer.innerHTML = "";
  rooms.forEach(room => {
    const div = document.createElement("div");
    div.classList.add("room-card");
    div.innerHTML = `
      <img class="avatar" src="${room.playerAvatarUrl}" alt="avatar">
      <div class="room-info">
        <h3>${room.username}</h3>
        <button onclick="location.href='/duel-room.html?roomId=${room.roomId}'">Join</button>
      </div>
    `;
    roomsContainer.appendChild(div);
  });
}

function renderInvites(invites) {
  invitesContainer.innerHTML = "";
  invites.forEach(invite => {
    const div = document.createElement("div");
    div.classList.add("room-card");
    div.innerHTML = `
      <img class="avatar" src="${invite.playerAvatarUrl}" alt="avatar">
      <div class="room-info">
        <h3>${invite.username}</h3>
        <button onclick="location.href='/duel-room.html?roomId=${invite.roomId}'">Accept</button>
      </div>
    `;
    invitesContainer.appendChild(div);
  });
}

function loadRooms() {
  fetch("/api/duel/rooms")
    .then(res => res.json())
    .then(data => {
      renderRooms(data.rooms);
      renderInvites(data.invites);
    });
}

loadRooms();
setInterval(loadRooms, 5000);