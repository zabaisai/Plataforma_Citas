export function initForms() {
    validarFormularioHorario();
    bloquearBotonesAlEnviar();
}

function validarFormularioHorario() {
    const formHorario = document.querySelector("#formHorario");

    if (!formHorario) {
        return;
    }

    formHorario.addEventListener("submit", (event) => {
        const horaInicio = document.querySelector("#horaInicio")?.value;
        const horaFin = document.querySelector("#horaFin")?.value;
        const errorHorario = document.querySelector("#errorHorario");

        if (!horaInicio || !horaFin) {
            return;
        }

        if (horaFin <= horaInicio) {
            event.preventDefault();

            if (errorHorario) {
                errorHorario.style.display = "block";
                errorHorario.textContent = "La hora de fin debe ser posterior a la hora de inicio.";
            }
        }
    });
}

function bloquearBotonesAlEnviar() {
    const forms = document.querySelectorAll("form");

    forms.forEach((form) => {
        form.addEventListener("submit", () => {
            const button = form.querySelector("button[type='submit']");

            if (!button) return;

            const errorHorario = document.querySelector("#errorHorario");

            if (errorHorario && errorHorario.style.display === "block") {
                return;
            }

            button.dataset.originalText = button.textContent;
            button.textContent = "Procesando...";
            button.disabled = true;

            setTimeout(() => {
                button.disabled = false;
                button.textContent = button.dataset.originalText || "Enviar";
            }, 4000);
        });
    });
}