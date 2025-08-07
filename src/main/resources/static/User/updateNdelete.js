console.log("update and delete your notes");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");

    const params = new URLSearchParams(window.location.search);
    const action = params.get("action");
    const taskID = params.get("taskId");
    const notesID = params.get("notesId");

    document.querySelector(".updateForm").style.display = "none";
    document.querySelector(".deleteForm").style.display = "none";

    if(action === "update"){
        document.querySelector(".updateForm").style.display = "block";
        document.getElementById("updateTaskId").value = taskID;
        document.getElementById("updateNotesId").value = notesID;
    }

    if(action === "delete"){
        document.querySelector(".deleteForm").style.display = "block";
        document.getElementById("deleteTaskId").value = taskID;
        document.getElementById("deleteNotesId").value = notesID;
    }

    //Update Note logic
    const username = localStorage.getItem("userName");
    if(username){
        document.getElementById("updateUserName").value = username;
    }

    const userid = localStorage.getItem("userId");
    if(userid){
        document.getElementById("updateUserId").value = userid;
    }

    const updateForm = document.getElementById("myUpdateForm");
    updateForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userName = document.getElementById("updateUserName").value.trim();
        const userId = parseInt(document.getElementById("updateUserId").value.trim());
        const taskId = parseInt(document.getElementById("updateTaskId").value.trim());
        const notesId = document.getElementById("updateNotesId").value.trim();
        const content = document.getElementById("content").value.trim();

        console.log("Sending update:", {
            userId,
            taskId,
            userName,
            content
        });

        try{
            const res = await fetch(`/api/note/${notesId}`, {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userId,
                    taskId,
                    userName,
                    content
                })
            });

            const result = await res.text();
            if(res.ok){
                alert(result);
                setTimeout(() => {
                    window.location.href = "Notes.html?taskId=" + taskId;
                }, 3000);
            }
            else{
                console.log("Server response: ", result);
                document.getElementById("updateMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response: ", error);
            document.getElementById("updateMessage").innerText = "Server Error, try again later!";
        }
    })

    //Delete Note logic
    const useriD = localStorage.getItem("userId");
    if(useriD){
        document.getElementById("deleteUserId").value = useriD;
    }
    const UserName = localStorage.getItem("userName");
    if(UserName){
        document.getElementById("deleteUserName").value = UserName;
    }

    const deleteForm = document.getElementById("myDeleteForm");
    deleteForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("deleteUserId").value.trim();
        const userName = document.getElementById("deleteUserName").value.trim();
        const notesId = document.getElementById("deleteNotesId").value.trim();
        const taskId = document.getElementById("deleteTaskId").value.trim();

        console.log("Sending update:", {
            notesId,
            userId,
            taskId,
            userName
        });

        try{
            const res = await fetch(`/api/note/${notesId}?userId=${encodeURIComponent(userId)}&taskId=${encodeURIComponent(taskId)}&userName=${userName}`, {
                method : "DELETE"
            });

            const result = await res.text();
            if(res.ok){
                alert(result);
                setTimeout(() => {
                    window.location.href = "Notes.html?taskId=" + taskId;
                }, 3000);
            }
            else{
                console.log("Server response: ", result);
                document.getElementById("confirmationMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response: ", error);
            document.getElementById("confirmationMessage").innerText = "Server Error, try again later!";
        }
    })

    //Buttons to go to dashboard and view Notes
    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }

    const BtntoLogout = document.getElementById("logout");
    if(BtntoLogout){
        BtntoLogout.addEventListener("click", () => {
            window.location.href = "Notes.html?taskId=" + taskID;
        })
    }
})