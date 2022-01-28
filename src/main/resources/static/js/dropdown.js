function dropdown(index) {
    closeAllDropdown();
    let el = document.getElementById(`dropdown-${index}`);
    el.classList.add("show");
}

function closeAllDropdown() {
    let dropdowns = document.getElementsByClassName("dropdown-content");
    for (let i = 0; i < dropdowns.length; i++) {
        let openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
            openDropdown.classList.remove('show');
        }
    }
}

window.onclick = function (event) {
    if (!event.target.matches('.dropdown')) {
        closeAllDropdown();
    }
}
