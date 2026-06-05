export function initSidebar() {
    const links = document.querySelectorAll(".sidebar a");
    const currentPath = window.location.pathname;

    links.forEach((link) => {
        const href = link.getAttribute("href");

        if (href === currentPath) {
            link.classList.add("active");
        }
    });
}