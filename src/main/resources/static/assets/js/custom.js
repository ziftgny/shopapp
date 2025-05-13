
window.addEventListener('DOMContentLoaded', () => {
    const title = document.querySelector('.animated-title');
    const delay = Math.random() * 8;
    title.style.animationDelay = `-${delay}s`;
});
