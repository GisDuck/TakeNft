export function submitDuel(button) {
  const walletId = button.getAttribute('data-wallet');

  fetch('/duel/invite', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: `walletId=${encodeURIComponent(walletId)}`
  }).then(response => {
    if (response.ok) {
      button.textContent = 'Duel Proposed';
      button.disabled = true;
    } else {
      alert('Error proposing duel');
    }
  });
}

export function submitFriendInvite(button) {
  const walletId = button.getAttribute('data-wallet');

  fetch('/api/friends/invite', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: `walletId=${encodeURIComponent(walletId)}`
  }).then(response => {
    if (response.ok) {
      button.textContent = 'Invite Sent';
      button.disabled = true;
    } else {
      alert('Error sending invite');
    }
  });
}
