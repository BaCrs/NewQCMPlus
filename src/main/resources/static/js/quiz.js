const MAX_SLIDE = document.querySelectorAll('.slide').length;
const MIN_SLIDE = 0;
let slideCount = MIN_SLIDE;

function next() {
    if (slideCount < MAX_SLIDE) {
        let el = document.getElementById(`slide-${slideCount}`);
        el.classList.add("hidden");
        slideCount++;
        el = document.getElementById(`slide-${slideCount}`);
        el.classList.remove("hidden");
        if (slideCount == MAX_SLIDE - 1) {
            document.getElementById("submit-button").classList.remove("hidden");
            document.getElementById("next-button").classList.add("hidden");
        }
        if (slideCount > MIN_SLIDE) {
            document.getElementById("previous-button").classList.remove("hidden");
        }
    }
}

function previous() {
    if (slideCount > MIN_SLIDE) {
        let el = document.getElementById(`slide-${slideCount}`);
        el.classList.add("hidden");
        slideCount--;
        el = document.getElementById(`slide-${slideCount}`);
        el.classList.remove("hidden");
        if (slideCount == MIN_SLIDE) {
            document.getElementById("previous-button").classList.add("hidden");
        }
        if (slideCount < MAX_SLIDE) {
            document.getElementById("submit-button").classList.add("hidden");
            document.getElementById("next-button").classList.remove("hidden");
        }
    }
}