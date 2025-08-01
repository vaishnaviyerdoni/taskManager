console.log("Add task is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");
    
    const userNameFromStorage = localStorage.getItem("userName");
    if(userNameFromStorage){
        document.getElementById("userNameforTasks").value = userNameFromStorage;
    }

    const userIdFromStorage = localStorage.getItem("userId");
    if(userIdFromStorage){
        document.getElementById("userIdfortasks").value = userIdFromStorage;
    }

    const addtaskForm = document.getElementById("addForm");
    addtaskForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userIdfortasks").value.trim();
        const title = document.getElementById("title").value.trim();
        const taskContent = document.getElementById("content").value.trim();
        const createdAt = document.getElementById("createdAt").value.trim();
        const userName = document.getElementById("userNameforTasks").value.trim();

        console.log("Received Information: ", userId, title, taskContent, createdAt, userName);

        try{
            const res = await fetch("/api/task/addtask", {
                method : "POST",
                headers :   {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userId, 
                    title,
                    taskContent, 
                    createdAt,
                    userName
                })
            });

            const result = await res.text();

            if(res.ok) {
                alert(result);
            }
            else{
                console.log("Server responser: ", result);
                document.getElementById("addTaskMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response: ", error);
            document.getElementById("addTaskMessage").innerText = "Server Error, try again later!";
        }
    })

    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }
})