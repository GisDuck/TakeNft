document.getElementById('avatar-upload-button').addEventListener('click', () => {
  document.getElementById('avatar-file').click();
});

let avatarUrl = 'img/no_avatar.png';
let playerAvatar = document.getElementById('player-avatar')
let playerAvatarLoader = document.getElementById('player-avatar-loader')


document.getElementById('avatar-file').addEventListener('change', async (e) => {
  const file = e.target.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append('avatar', file);

  try {
    showAvatarLoader();

    const res = await fetch('/api/upload-avatar', {
      method: 'POST',
      credentials: 'include',
      body: formData
    });

    // Если размер файла слишком большой (413), выводим alert и не пытаемся парсить JSON
    if (res.status === 413) {
      hideAvatarLoader();
      alert('Файл слишком большой. Пожалуйста, загрузите файл размером до 10MB.');
      return;
    }

    // Пробуем прочитать тело ответа, если оно есть
    let result = {};
    try {
      result = await res.json();
    } catch (jsonError) {
      hideAvatarLoader();
      // Тело ответа пустое — можно проигнорировать или логгировать
    }

    avatarUrl = result.avatarUrl
    if (res.ok) {
      document.getElementById('player-avatar').src = avatarUrl;
    } else {
      alert(result.message || 'Ошибка загрузки аватара');
    }
    hideAvatarLoader();

  } catch (error) {
    hideAvatarLoader();
    alert('Ошибка сети или сервера. Повторите попытку позже.');
    console.error('Ошибка загрузки файла:', error);
  }
});

function showAvatarLoader() {
    playerAvatar.style.display = "none";
    playerAvatarLoader.style.display = "block";
 }

function hideAvatarLoader() {
    playerAvatar.style.display = "block";
    playerAvatarLoader.style.display = "none";
 }



document.getElementById('save-button').addEventListener('click', async () => {
  const registerForm = {
    username: document.getElementById('nickname').value,
    email: document.getElementById('email').value,
    avatarUrl: avatarUrl,
    duelPermission: document.getElementById('duel-permission').value,
    friendPermission: document.getElementById('friend-permission').value
  };
  const res = await fetch('/api/player/change-settings', {
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
