console.log("Notes are loading");
document.addEventListener("DOMContentLoaded", () => {
    const viewNotesForm = document.getElementById("viewNotesForm");
    viewNotesForm.addEventListener("submit", async(e) => {
        e.preventDefault();
    })
}) 