function collapseNavbar() {
    let navbar = document.getElementById("navbar");
    if (navbar.className === "") {
        navbar.className = " responsive";
    } else {
        navbar.className = "";
    }
}