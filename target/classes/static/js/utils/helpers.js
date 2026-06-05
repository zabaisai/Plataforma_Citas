export function formatDate(dateString) {
    if (!dateString) return "";

    const date = new Date(dateString);

    return new Intl.DateTimeFormat("es-CO", {
        year: "numeric",
        month: "long",
        day: "numeric"
    }).format(date);
}

export function formatCurrency(value) {
    if (!value) return "$0";

    return new Intl.NumberFormat("es-CO", {
        style: "currency",
        currency: "COP"
    }).format(value);
}