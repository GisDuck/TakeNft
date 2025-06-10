function submitFriendInvite(form) {
    const formData = new FormData(form);

    fetch(form.action, {
      method: "POST",
      body: new URLSearchParams(formData)
    })
    .then(resp => {
      if (!resp.ok) throw new Error("Invite failed");
      form.style.display = "none"; // скрываем форму
    })
    .catch(err => console.error(err));

    return false; // отменяем обычную отправку формы
  }