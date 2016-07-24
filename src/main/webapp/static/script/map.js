function initMap(id) {
	return new google.maps.Map(document.getElementById(id), {
		// Center: Texas
		center: {lat: 31.9686, lng: -99.9018},
		zoom: 5
	});
}