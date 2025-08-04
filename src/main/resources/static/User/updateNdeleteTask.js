console.log("update and delete tasks loading!");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");
     
    const params = new URLSearchParams(window.location.search);
    const action = params.get("action");
    const taskID = params.get("taskId");

    if(action === "update"){
        document.querySelector(".updateForm").style.display = "block";
        document.getElementById("taskIdForupdate").value = taskID;
    }
    else if(action === "delete"){
        document.querySelector(".deleteForm").style.display = "block";
        document.getElementById("taskIdForupdate").value = taskID;
    }

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
            const res = await fetch(`/api/task/${userId}`, {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    taskId,
                    userName,
                    taskContent
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
})