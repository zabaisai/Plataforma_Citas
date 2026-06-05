import { initAlerts } from "./modules/alerts.js";
import { initForms } from "./modules/forms.js";
import { initSidebar } from "./modules/sidebar.js";
import { initCitas } from "./modules/citas.js";

document.addEventListener("DOMContentLoaded", () => {
    initAlerts();
    initForms();
    initSidebar();
    initCitas();
});