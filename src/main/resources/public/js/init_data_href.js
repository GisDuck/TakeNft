document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('[data-href]').forEach(el => {
    el.addEventListener('click', () => {
      const url = el.getAttribute('data-href');
      if (url) {
        window.location.href = url;
      }
    });
  });
});
