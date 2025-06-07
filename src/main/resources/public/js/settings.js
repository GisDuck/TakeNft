document.getElementById('avatar-upload-button').addEventListener('click', () => {
  document.getElementById('avatar-file').click();
});

let avatarUrl = 'img/no_avatar.png';

document.getElementById('avatar-file').addEventListener('change', async (e) => {
  const file = e.target.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append('avatar', file);

  const res = await fetch('/api/upload-avatar', {
    method: 'POST',
    credentials: 'include',
    body: formData
  });

  const result = await res.json();

  if (res.ok) {
    avatarUrl = result.avatarUrl;
    document.getElementById('player-avatar').src = avatarUrl;
  } else {
    alert(result.message || 'Ошибка загрузки');
  }
});





document.getElementById('save-button').addEventListener('click', async () => {
  const registerForm = {
    username: document.getElementById('nickname').value,
    email: document.getElementById('email').value,
    avatarUrl: avatarUrl,
    duelPermission: document.getElementById('duel-permission').value.toUpperCase(),
    friendPermission: document.getElementById('friend-permission').value.toUpperCase()
  };
  const res = await fetch('/api/player/register', {
    method: 'POST',
    credentials: 'include',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(registerForm)
  });
  if (res.ok) {
    window.location.href = '/';
  } else {
    const { message } = await res.json();
      alert(message || 'Ошибка при сохранении настроек');
  }
});
