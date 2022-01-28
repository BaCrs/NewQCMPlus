const MAX_ITEMS = 5;
const MIN_ITEMS = 2;
let itemsCount = document.querySelectorAll('.item:not(.hidden)').length;
let el;

function addItem() {
    if (itemsCount < MAX_ITEMS) {
        el = document.getElementById(`item-${itemsCount}`);
        el.classList.remove("hidden");
        itemsCount++;
    }
}

function removeItem() {
    if (itemsCount > MIN_ITEMS) {
        itemsCount--;
        document.querySelector(`#item-${itemsCount} input[type='text']`).value = "";
        document.querySelector(`#item-${itemsCount} input[type='checkbox']`).checked = false;
        el = document.getElementById(`item-${itemsCount}`);
        el.classList.add("hidden");
    }
}