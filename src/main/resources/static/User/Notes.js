console.log("Notes are loading");

const userId = localStorage.getItem("userId");
const userName = localStorage.getItem("userName");

const params = new URLSearchParams(window.location.search);
const taskId = params.get("taskId");
const action = params.get("action");


document.addEventListener("DOMContentLoaded", async() => {
    console.log("DOM is loading");
     try{
        const res = await fetch(`/api/note/${taskId}?userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}`, {
            method : "GET"
        });

        if(res.ok){
            const notestable =  await res.json();
            if(notestable.length === 0){
                document.getElementById("myTable").innerHTML = "<p>No notes found for task</p>";
            }
            else{
                let table = `
                    <table border="1" cellpadding="8" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Note ID</th>
                                <th>Task ID</th>
                                <th>User ID</th>
                                <th>Note</th>
                                <th>Date</th>
                                <th>Actions</th>
                            </tr>
                          </thead>
                        <tbody>
                    `;

                notestable.forEach(data => {
                    table += `
                        <tr>
                            <td>${data.notesId}</td>
                            <td>${data.task.taskId}</td>
                            <td>${data.user.userId}</td>
                            <td>${data.content}</td>
                            <td>${data.createdAt}</td>
                            <td>
                                <button class="updateNoteBtn" data-taskid="${data.task.taskId}" data-notesid="${data.notesId}">Update</button>
                                <button class="deleteNoteBtn" data-taskid="${data.task.taskId}" data-notesid="${data.notesId}">Delete</button>
                            </td>
                        </tr>
                    `;
                });

                table += "</tbody></table>";
                console.log("Tables for notes exist", document.getElementById("myTable"));
                document.getElementById("myTable").innerHTML = table;

                document.getElementById("myTable").addEventListener("click", (event) => {
                    event.preventDefault();

                    const target = event.target;

                    //When update is clicked
                    if(target.classList.contains("updateNoteBtn")){
                        const taskId = target.dataset.taskid;

                        const notesId = target.dataset.notesid;

                        console.log("Clicked element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);
                        console.log("Notes ID from dataset:", target.dataset.notesid);
                    
                        window.location.href = `updateNdelete.html?action=update&taskId=${taskId}&notesId=${notesId}`;
                    }

                    //When delete is clicked
                    if(target.classList.contains("deleteNoteBtn")){
                        const taskId = target.dataset.taskid;

                        const notesId = target.dataset.notesid;

                        console.log("Clicked element:", target);
                        console.log("Task ID from dataset:", target.dataset.taskid);
                        console.log("Notes ID from dataset:", target.dataset.notesid)
                    
                    window.location.href = `updateNdelete.html?action=delete&taskId=${taskId}&notesId=${notesId}`;
                    }
                })
            }          
        }
        else{
            const errorMessage = await res.json();
            console.log(errorMessage);
            document.getElementById("notesMessage").innerText = errorMessage;
        }
    }
    catch(error){
        console.log("Server Error", error);
        document.getElementById("notesMessage").innerText = "Server Error, try again later";
    }

    const BtntoDashboard = document.getElementById("dashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", () => {
            window.location.href = "dashboard.html";
        })
    }   
}) 