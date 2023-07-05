document.getElementById("lets").addEventListener("click", function(event) {
    event.preventDefault(); // Prevent the default behavior of the anchor tag
    document.body.classList.add("fade-out-animation");
    setTimeout(function() {
      window.location.href = "login.html";
    }, 1000); // Adjust the timeout duration as needed to match the animation duration
  });
  