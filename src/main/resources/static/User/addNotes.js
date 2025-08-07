console.log("Add notes is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("Dom is loading");

    const userID = localStorage.getItem("userId");
    if(userID){
        document.getElementById("userId").value = userID;
    }

    const username = localStorage.getItem("userName");
    if(username){
        document.getElementById("userName").value = username;
    }

    const params = new URLSearchParams(window.location.search);
    const taskID = params.get("taskId");
    const action = params.get("action");

    if(taskID){
        document.getElementById("taskId").value = taskID;
    }

    const addNotesForm = document.getElementById("noteForm");
    addNotesForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userId").value.trim();
        const userName = document.getElementById("userName").value.trim();
        const taskId = document.getElementById("taskId").value.trim();
        const content = document.getElementById("content").value.trim();
        const createdAt = document.getElementById("createdAt").value.trim();

        console.log("Received informatiomn is: ", userId, userName, taskId, content, createdAt);

        try{
            const res = await fetch(`/api/note?userName=${encodeURIComponent(userName)}`, {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify({
                userId,
                taskId,
                content,
                createdAt
            })
        });

        const result = await res.text();
            if(res.ok){
                alert(result);
                setTimeout(() => {
                    window.location.href = "tasks.html";
                }, 3000)
            }
            else{
                console.log("Server response: ",result);
                document.getElementById("notesMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response: ", error);
            document.getElementById("notesMessage").innerText = "Server Error, try again!";
        }
    })

    //Buttons to go to dashboard and view Notes
    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }
})