const nickInput = document.getElementById("nickname"),
emailInput = document.getElementById("email"),
playerAvatarImg = document.getElementById("player-avatar"),
duelSel   = document.getElementById("duel-permission"),
friendSel = document.getElementById("friend-permission");

fetch("/data/account_settings.json")
  .then(res => res.json())
  .then(data => {

    playerAvatarImg.src = data.playerAvatarUrl;
    nickInput.value = data.nickname;
    emailInput.value = data.email;

duelSel.value   = data.duelPermission;
friendSel.value = data.friendPermission;

})
.catch(err => {
  console.error("Ошибка загрузки данных пользователя:", err);
  title.textContent = "Ошибка загрузки данных";
});


const avatarInput = document.getElementById("avatar-upload");
const avatarImg = document.getElementById("settings-avatar-img");

avatarInput.addEventListener("change", e => {
  const file = e.target.files[0];
  if (!file) return;
  const reader = new FileReader();
  reader.onload = ev => {
    avatarImg.src = ev.target.result;

  };
  reader.readAsDataURL(file);
});


document.getElementById("logout-button").addEventListener("click", async () => {

  await tonConnectUI.disconnect();

  localStorage.clear();
  window.location.href = "index.html";
});
