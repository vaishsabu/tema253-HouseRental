// Retrieve the selected dates from the URL parameters
const urlParams = new URLSearchParams(window.location.search);
const startDate = urlParams.get('startDate');
const endDate = urlParams.get('endDate');
// Update the elements with the selected dates
document.querySelector('.home-desc em:nth-child(1)').textContent = startDate;
document.querySelector('.home-desc em:nth-child(2)').textContent = endDate;
