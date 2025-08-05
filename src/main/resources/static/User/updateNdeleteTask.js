console.log("update and delete tasks loading!");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");
     
    const params = new URLSearchParams(window.location.search);
    const action = params.get("action");
    const taskID = params.get("taskId");

    document.querySelector(".updateForm").style.display = "none";
    document.querySelector(".deleteForm").style.display = "none";


    if(action === "update"){
        document.querySelector(".updateForm").style.display = "block";
        document.getElementById("taskIdForupdate").value = taskID;
    }
    else if(action === "delete"){
        document.querySelector(".deleteForm").style.display = "block";
        document.getElementById("taskId").value = taskID;
    }

    //update task logic
    const userNamefromStorage = localStorage.getItem("userName");
    if(userNamefromStorage){
        document.getElementById("userNameforUpdate").value = userNamefromStorage;
    }

    const userIdFromStorage =localStorage.getItem("userId")
    if(userIdFromStorage){
        document.getElementById("userIdForUpdate").value = userIdFromStorage;
    }

    const updateForm = document.getElementById("myUpdateForm");
    updateForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userName = document.getElementById("userNameforUpdate").value.trim();
        const userId = document.getElementById("userIdForUpdate").value.trim();
        const taskId = document.getElementById("taskIdForupdate").value.trim();
        const taskContent = document.getElementById("taskContent").value.trim();

        console.log("Received Information is: ", userId, userName, taskId, taskID, taskContent);

        try{
            const res = await fetch(`/api/task/${taskId}`, {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userName,
                    taskContent,
                    userId
                })
            })

            const result = await res.text();
            if(res.ok){
                alert(result);
                setTimeout(() => {
                    window.location.href = "tasks.html";
                }, 3000);
            }
            else{
                document.getElementById("updateMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response: ", error);
            document.getElementById("updateMessage").innerText = "Server Error, try again later";
        }
    })

    //delete task logic
    const userIDStorage = localStorage.getItem("userId");
    if(userIDStorage){
        document.getElementById("userID").value = userIDStorage;
    }

    const userNameStorage = localStorage.getItem("userName");
    if(userNameStorage){
        document.getElementById("userName").value = userNameStorage;
    }

    const deleteForm = document.getElementById("myDeleteForm");
    deleteForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const userName = document.getElementById("userName").value.trim();
        const taskId = document.getElementById("taskId").value.trim();

        console.log("Received information: ", userId, userName, taskId);

        try{
            const res = await fetch(`/api/task/${taskId}?userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}`, {
                method : "DELETE"
            });

            const result = await res.text();
            if(res.ok){
                alert(result);
                setTimeout(() => {
                    window.location.href = "tasks.html";
                }, 3000)
            }
            else{
                document.getElementById("confirmationMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server Response", error);
            document.getElementById("confirmationMessage").innerText = "Server Error, try again later!";
        }
    })

    //dashboard button logic
    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }
})