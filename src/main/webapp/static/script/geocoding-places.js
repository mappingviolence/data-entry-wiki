var Geocoding;

function initGCP() {
	Geocoding = function() {
		
	};

	Geocoding.prototype.autocomplete = function(swLat, swLng, neLat, neLng, inputId, options, callback) {
		var defaultBounds = new google.maps.LatLngBounds(
				new google.maps.LatLng(swLat, swLng),
				new google.maps.LatLng(neLat, neLng)
		);
		
		var inputField = document.getElementById(inputId);

		if (options) {
			autocomplete = new google.maps.places.Autocomplete(inputField, options);
		} else {
			autocomplete = new google.maps.places.Autocomplete(inputField);
		}
		
		if (callback && typeof callback == "function") {
			callback(autocomplete);
		}
	}
}