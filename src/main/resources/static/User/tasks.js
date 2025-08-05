console.log("tasks are loading");
document.addEventListener("DOMContentLoaded", async() => {
   
    const userId = localStorage.getItem("userId");
    const userName = localStorage.getItem("userName");

    console.log("userId and userName are: ",userId, userName);

    try{
        const res = await fetch(`/api/task/${userId}?userName=${encodeURIComponent(userName)}`, {
            method : "GET"
        })

        if(res.ok){
            const tasks = await res.json();
            if(tasks.length === 0){
                document.getElementById("taskTable").innerHTML = "<p>No tasks found</p>";
            }
            else{
                let table = `
                    <table border="1" cellpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>Task ID</th>
                              <th>User ID</th>
                              <th>Title</th>
                              <th>Task</th>
                              <th>Date</th>
                              <th>Actions</th>
                              <th>Notes</th>
                            </tr>
                          </thead>
                        <tbody>
                `;

                tasks.forEach(data => {
                    table += 
                        `
                        <tr>
                            <td>${data.taskId}</td>
                            <td>${data.user.userId}</td>
                            <td>${data.title}</td>
                            <td>${data.taskContent}</td>
                            <td>${data.createdAt}</td>  
                            <td>
                                <button class="updateTaskBtn" data-taskid="${data.taskId}">Update</button>
                                <button class="deleteTaskBtn" data-taskid="${data.taskId}">Delete</button>
                            </td>
                            <td>
                                <button class="addNotes" data-taskid="${data.taskId}">+ Add Notes</button>
                                <button class="viewNotes" data-taskid="${data.taskId}">View Notes</button>
                            </td>
                        </tr>  
                    `;
                });

                table += "</tbody></table>";
                console.log("table exists ?", document.getElementById("taskTable"));
                document.getElementById("taskTable").innerHTML = table;

                document.getElementById("taskTable").addEventListener("click", (event) => {
                    event.preventDefault();
                    const target = event.target;
                    
                    //When update is clicked
                    if(target.classList.contains("updateTaskBtn")){
                        const taskId = target.dataset.taskid;

                        console.log("Clicked element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);


                        window.location.href = `updateNdeleteTask.html?action=update&taskId=${taskId}`;
                    }

                    //When delete is clicked
                    if(target.classList.contains("deleteTaskBtn")){
                        const taskId = target.dataset.taskid;

                        console.log("Clicked element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);


                        window.location.href = `updateNdeleteTask.html?action=delete&taskId=${taskId}`;
                    }

                    //When +Add notes is clicked
                    if(target.classList.contains("addNotes")){
                        const taskId = target.dataset.taskid;

                        console.log("Clicked element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);

                        window.location.href = `notes.html?action=post&taskId=${taskId}`;
                    }

                    //When view notes is clicked 
                    if(target.classList.contains("viewNotes")){
                        const taskId = target.dataset.taskid;

                        console.log("Clicked Element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);

                        window.location.href = `viewNotes.html?action=get&taskId=${taskId}`;
                    }
                })
            }
        }
        else{
            const errorMessage = await res.json();
            console.log(errorMessage);
            document.getElementById("tasksMessage").innerHTML = errorMessage;
        }
    }
    catch(error){
        console.log("Server response: ", error);
        document.getElementById("tasksMessage").innerText = "Server Error, try again later!";
    }

    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }
})