document.addEventListener('DOMContentLoaded', () => {
    const duelBtn = document.getElementById('duel-button');
    if (duelBtn) {
      duelBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const walletId = duelBtn.getAttribute('data-wallet');
        const response = await fetch('/duel/invite', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ walletId })
        });
        if (response.ok) {
          duelBtn.textContent = 'Invite sent';
          duelBtn.disabled = true;
        }
      });
    }

    const friendBtn = document.getElementById('friend-button');
    if (friendBtn) {
      friendBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const walletId = friendBtn.getAttribute('data-wallet');
        const response = await fetch('/api/friends/invite', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ walletId })
        });
        if (response.ok) {
          friendBtn.textContent = 'Invite sent';
          friendBtn.disabled = true;
          friendBtn.style.background = '#79C3FF';
          friendBtn.style.color = '#000';
        }
      });
    }
  });