console.log("profile.js is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM loading");

    //Update password form

    const userIdFromStorage = localStorage.getItem("userId");
    if(userIdFromStorage){
        document.getElementById("userIdForPassword").value = userIdFromStorage;
    }

    const passwordForm = document.getElementById("updatePassword");
    passwordForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userIdForPassword").value.trim();
        const userName = document.getElementById("userNameForPassword").value.trim();
        const password = document.getElementById("passwordUpdate").value.trim();

        console.log("Information received is: ", userId, userName, password);

        try{
            const res = await fetch(`/api/user/${userId}/password`, {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userName,
                    password
                })
            })

            const result = await res.text();
            if(res.ok){
                alert(result);
            }
            else{
                console.log("server Response:", result);
                document.getElementById("updatePasswordMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response :", error);
            document.getElementById("updatePasswordMessage").innerText = "Server Error, try again later!";
        }
    })

    //Update Email form

    const userIdFromStorageforEmail = localStorage.getItem("userId");
    if(userIdFromStorageforEmail){
        document.getElementById("userIDforEmail").value = userIdFromStorageforEmail;
    }

    const emailForm = document.getElementById("updateEmail");
    emailForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userIDforEmail").value.trim();
        const userName = document.getElementById("userNameForEmail").value.trim();
        const email = document.getElementById("emailUpdate").value.trim();

        console.log("Received value are: ", userId, userName, email);

        try{
            const res = await fetch(`/api/user/${userId}/email`, {
                method : "PUT", 
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userName,
                    email
                })
            });

            const result = await res.text();

            if(res.ok){
                alert(result);
            }
            else{
                console.log("Server response: ", result);
                document.getElementById("updateEmailMessage").innerText = result;
            }
        }
        catch(error){
            console.log("Server response:", error);
            document.getElementById("updateEmailMessage").innerText = "Server Error, try again later";
        }
    })

    //Delete my account form

    const deleteformuserIdStorage = localStorage.getItem("userId");
    
})