export function initCitas() {
    confirmarAccionesCriticas();
    limitarFechasPasadas();
    cargarHorariosDisponibles();
}

function confirmarAccionesCriticas() {
    const botonesPeligro = document.querySelectorAll(".danger");

    botonesPeligro.forEach((button) => {
        button.addEventListener("click", (event) => {
            const confirmado = confirm("¿Estás seguro de realizar esta acción?");

            if (!confirmado) {
                event.preventDefault();
            }
        });
    });
}

function limitarFechasPasadas() {
    const dateInputs = document.querySelectorAll("input[type='date']");
    const hoy = new Date().toISOString().split("T")[0];

    dateInputs.forEach((input) => {
        input.setAttribute("min", hoy);
    });
}

function cargarHorariosDisponibles() {
    const profesionalSelect = document.getElementById("profesionalId");
    const fechaInput = document.getElementById("fechaCita");
    const horaSelect = document.getElementById("horaDisponible");

    if (!profesionalSelect || !fechaInput || !horaSelect) {
        console.warn("No se encontraron los campos para cargar horarios disponibles.");
        return;
    }

    async function actualizarHorarios() {
        const profesionalId = profesionalSelect.value;
        const fecha = fechaInput.value;

        console.log("Profesional seleccionado:", profesionalId);
        console.log("Fecha seleccionada:", fecha);

        horaSelect.innerHTML = `<option value="">Cargando horarios...</option>`;

        if (!profesionalId || !fecha) {
            horaSelect.innerHTML = `<option value="">Primero selecciona profesional y fecha</option>`;
            return;
        }

        try {
            const url = `/api/profesionales/${profesionalId}/horarios-disponibles?fecha=${fecha}`;
            console.log("Consultando:", url);

            const respuesta = await fetch(url);

            if (!respuesta.ok) {
                throw new Error("Respuesta incorrecta del servidor.");
            }

            const horas = await respuesta.json();

            console.log("Horas recibidas:", horas);

            if (!horas || horas.length === 0) {
                horaSelect.innerHTML = `<option value="">No hay horarios disponibles para esta fecha</option>`;
                return;
            }

            horaSelect.innerHTML = `<option value="">Selecciona una hora disponible</option>`;

            horas.forEach((hora) => {
                const option = document.createElement("option");
                option.value = hora;
                option.textContent = hora;
                horaSelect.appendChild(option);
            });

        } catch (error) {
            console.error("Error cargando horarios:", error);
            horaSelect.innerHTML = `<option value="">Error cargando horarios</option>`;
        }
    }

    profesionalSelect.addEventListener("change", actualizarHorarios);
    fechaInput.addEventListener("change", actualizarHorarios);

    // Esto ayuda si ya hay profesional y fecha seleccionados al cargar la página
    actualizarHorarios();
}