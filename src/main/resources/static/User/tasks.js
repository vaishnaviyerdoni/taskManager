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
                        </tr>  
                    `;
                });

                table += "</tbody></table>";
                console.log("table exists ?", document.getElementById("taskTable"));
                document.getElementById("taskTable").innerHTML = table;
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
    
})