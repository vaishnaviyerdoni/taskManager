console.log("dashboard.js is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");

    document.getElementById("gotoTasksBtn").addEventListener("click", () => {
        window.location.href = "tasks.html";
    })

    document.getElementById("logout").addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "logout.html";
    });
})