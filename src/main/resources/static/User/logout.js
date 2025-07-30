console.log("logout.js is loading");

localStorage.removeItem("userId");
localStorage.removeItem("userName");
localStorage.removeItem("password");
localStorage.removeItem("email");

localStorage.clear();

setTimeout(() => {
    window.location.href = "login.html";
}, 3000);