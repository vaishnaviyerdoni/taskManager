console.log("login.js is loaded!");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM loaded!");

    const loginForm = document.getElementById("loginForm");
    loginForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userId").value.trim();
        const userName = document.getElementById("userName").value.trim();
        const password = document.getElementById("password").value.trim();
        const email = document.getElementById("email").value.trim();

        console.log("The received values are: ",userId, userName, password, email);

        try{
            const res = await fetch("/api/user/login", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userId,
                    userName,
                    password,
                    email                    
                })
            });

            const result = await res.text();
            console.log("Server Response: ", result);

            if(res.ok){
                alert(result);

                localStorage.setItem("userId", userId);
                localStorage.setItem("userName", userName);
                localStorage.setItem("password", password);
                localStorage.setItem("email", email);

                setTimeout(() => {
                    window.location.href = "dashboard.html";
                }, 1000);
            }
            else{
                document.getElementById("loginMessage").innerText = result;
            }
        }
        catch(error){
            console.error("Login Error:", error);
            document.getElementById("loginMessage").innerText = "Server Error, try again later!";
        }
    })
});