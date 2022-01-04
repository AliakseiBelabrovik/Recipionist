

let redElements = document.querySelectorAll(".red-section");
let whiteElements = document.querySelectorAll(".white-section");



//let myStorage = window.localStorage;
let isDarkMode;
if(localStorage.getItem('theme') === 'dark') {
    isDarkMode = true;
    redElements.forEach(item => {
        item.classList.add("dark-mode-red-section");
    });
    whiteElements.forEach(item => {
        item.classList.add("dark-mode-white-section");
    });
} else {
    localStorage.setItem('theme', 'light');
    isDarkMode = false;
    redElements.forEach(item => {
        item.classList.remove("dark-mode-red-section");
    });
    whiteElements.forEach(item => {
        item.classList.remove("dark-mode-white-section");
    });
}

let changeModeBtn = document.querySelector(".change-mode");

changeModeBtn.addEventListener("click", function () {
    isDarkMode = !isDarkMode;
    changeMode();
})


function changeMode() {
    redElements = document.querySelectorAll(".red-section");
    whiteElements = document.querySelectorAll(".white-section");

    if (isDarkMode) {
        localStorage.setItem('theme', 'dark');
        redElements.forEach(item => {
            item.classList.add("dark-mode-red-section");
        });
        whiteElements.forEach(item => {
            item.classList.add("dark-mode-white-section");
        });
    } else {
        localStorage.setItem('theme', 'light');
        redElements.forEach(item => {
            item.classList.remove("dark-mode-red-section");
        });
        whiteElements.forEach(item => {
            item.classList.remove("dark-mode-white-section");
        });
    }
};



