const MIN_ITEMS = 2
const MAX_ITEMS = 5
let itemsCount = MIN_ITEMS;

const form = document.getElementById("add-question-form");

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function addItem() {
    /*
    if (itemsCount < MAX_ITEMS) {
        for (i = 0; i < MAX_ITEMS; i++) {
            let response = document.getElementById(`items${i}.response1`).checked;
            let title = document.getElementById(`items${i}.title`).value;
            console.log(i, title, response);
            if (!isBlank(title)) {
                validQuestions++;
                isOneCorrectItem = response || isOneCorrectItem;
            }
        }
    }
    */
}