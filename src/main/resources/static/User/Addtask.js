console.log("Add task is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");
    const userIdFromStorage = localStorage.getItem("userId");
    if(userIdFromStorage){
        document.getElementById("userIdfortasks").value = userIdFromStorage;
    }

    const addtaskForm = document.getElementById("addForm");
    addtaskForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        
        const userId = document.getElementById("userIdfortasks").value.trim();
        const title = document.getElementById("title").value.trim();
        const taskContent = document.getElementById(content).value.trim();
        const createdAt = document.getElementById('createdAt').value.trim();
    })


    

    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }
})