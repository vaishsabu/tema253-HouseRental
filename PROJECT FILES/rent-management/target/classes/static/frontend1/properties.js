
const locationFilter = document.getElementById('location-filter');
const budgetFilter = document.getElementById('budget-filter');
const minBudgetLabel = document.getElementById('min-budget');
const maxBudgetLabel = document.getElementById('max-budget');
const applyFilterBtn = document.getElementById('apply-filter');
const slideValue = document.querySelector("span");
const inputSlider = document.querySelector("input");
inputSlider.oninput = (()=>{
    let value = inputSlider.value;
    slideValue.textContent = value;
    slideValue.textContent = value;
    slideValue.style.left = (value/2) + "%";
});

budgetFilter.addEventListener('input', updateBudgetLabels);
applyFilterBtn.addEventListener('click', applyFilters);


function updateBudgetLabels() {
  const minBudget = parseFloat(budgetFilter.min);
  const maxBudget = parseFloat(budgetFilter.max);
  const selectedMinBudget = parseFloat(budgetFilter.value);
  const selectedMaxBudget = parseFloat(budgetFilter.max) - selectedMinBudget;
  
  minBudgetLabel.textContent = selectedMinBudget.toLocaleString();
  maxBudgetLabel.textContent = (maxBudget - selectedMaxBudget).toLocaleString();
}

function applyFilters() {
  const locationValue = locationFilter.value.toLowerCase();
  const budgetValue = parseFloat(budgetFilter.value);

  const propertyBoxes = document.querySelectorAll('.box');


  propertyBoxes.forEach((box) => {
    const location = box.querySelector('.text p').textContent.toLowerCase();
    const price = parseFloat(box.querySelector('h2').textContent.replace('Rs ', '').replace(',', ''));

    if (
      (locationValue === '' || location.includes(locationValue)) &&
      (isNaN(budgetValue) || price <= budgetValue)
    ) {

      box.style.display = 'block';
    } else {

      box.style.display = 'none';
    }
  });

const urlParams = new URLSearchParams(window.location.search);
const locationFilter = urlParams.get('location');

const filterPropertiesByLocation = () => {
    propertyCards.forEach(card => {
        const cardLocation = card.querySelector('.location-name').textContent.toLowerCase();
        const isLocationMatch = cardLocation.includes(locationFilter);

        if (isLocationMatch) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
};


filterPropertiesByLocation();

}
