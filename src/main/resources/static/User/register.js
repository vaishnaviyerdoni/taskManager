console.log("register.js is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM is loading");

    const registerForm = document.getElementById("registrationForm");
    registerForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userName = document.getElementById("userName").value.trim();
        const password = document.getElementById("password").value.trim();
        const email = document.getElementById("email").value.trim();

        console.log("The values received are:", userName, password, email);

        try{
            const res = await fetch("/api/user/register", {
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    userName,
                    password,
                    email
                })
            });

            const result = await res.text();
            console.log("Server response: ", result);

            if(res.ok){
                alert(result);

                setTimeout(() => {
                    window.location.href = "login.html";
                }, 3000);
            }
            else{
                document.getElementById("registerMessage").innerText = result;
            }
        }
        catch(error){
            console.log(error);
            document.getElementById("registerMessage").innerText = "Server Error, try again later!";
        }
    })
})