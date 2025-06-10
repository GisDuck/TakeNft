document.addEventListener("DOMContentLoaded", function () {
document.querySelectorAll("button[data-wallet]").forEach(button => {
  button.addEventListener("click", function (event) {
    event.stopPropagation();

    const walletId = this.getAttribute("data-wallet");
    const url = this.getAttribute("data-url");
    const card = this.closest(".friend-card");

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: new URLSearchParams({ walletId })
    }).then(response => {
      if (response.ok) {
        location.reload(true)
      }
    }).catch(error => console.error(error));
  });
});
});