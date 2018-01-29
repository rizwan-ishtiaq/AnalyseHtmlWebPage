function hideOthers() {
	// Hiding result div
	div = document.getElementById('resultDiv');
	if (div) {
		div.style.display = 'none'
	}
	// hiding error div
	div = document.getElementById('errorDiv');
	if (div) {
		div.style.display = 'none'
	}
}