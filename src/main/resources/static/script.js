function dropdown(index) {
    console.log(index);
    document.getElementById(`dropdown-${index}`).classList.toggle("show");
}

window.onclick = function (event) {
    if (!event.target.matches('.dropdown')) {
        let dropdowns = document.getElementsByClassName("dropdown-content");
        for (let i = 0; i < dropdowns.length; i++) {
            let openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}