const backdrop = document.querySelector(".backdrop");
const toggleButton = document.querySelector(".toggle-button");
const sideNav = document.querySelector(".side-nav");

toggleButton.addEventListener("click", function () {
  sideNav.classList.add("open");
  backdrop.style.display = "block";
  setTimeout(function () {
    backdrop.classList.add("open");
  }, 10);
});

backdrop.addEventListener("click", function () {
  sideNav.classList.remove("open");
  closeModal();
});

function closeModal() {
  backdrop.classList.remove("open");
  setTimeout(function () {
    backdrop.style.display = "none";
  }, 200);
}
