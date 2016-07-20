var Mapping = function() {
	var latId, lngId, addressId, map, geocoder, marker;
	var ZOOM_LEVEL = 16;
}

Mapping.prototype.init = function(latId, lngId, addressId, map) {
	this.latId = latId;
	this.lngId = lngId;
	this.addressId = addressId;
	this.map = map;
	this.geocoder = new google.maps.Geocoder;
	this.marker = new google.maps.Marker({
		position: this.map.getCenter(),
		draggable: false,
		map: map
	});
	this.marker.setVisible(false);
}

Mapping.prototype.updateLatLng = function(autocompleteOrMarker) {
	if (autocompleteOrMarker.getPlace && autocompleteOrMarker.getPlace()) {
		var autocomplete = autocompleteOrMarker;
		$("#" + this.latId).val(autocomplete.getPlace().geometry.location.lat());
		$("#" + this.lngId).val(autocomplete.getPlace().geometry.location.lng());
	} else {
		var marker = autocompleteOrMarker;
		$("#" + this.latId).val(marker.getPosition().lat());
		$("#" + this.lngId).val(marker.getPosition().lng());
	}
}

Mapping.prototype.updateMap = function(autocompleteOrLatLng) {
	var location;
	if (autocompleteOrLatLng.lat) {
		location = autocompleteOrLatLng;
		this.map.setCenter(location);
		this.map.setZoom(16);  // Why 16? Because it looks good.
	} else {
		var place = autocomplete.getPlace();
	    if (!place.geometry) {
	      window.alert("Autocomplete's returned place contains no geometry");
	      return;
	    }

	    // If the place has a geometry, then present it on a map.
	    if (place.geometry.viewport) {
	      this.map.fitBounds(place.geometry.viewport);
	    } else {
	      this.map.setCenter(place.geometry.location);
	      this.map.setZoom(16);  // Why 16? Because it looks good.
	    }
	    location = place.geometry.location;
	}
	
    this.marker.setPosition(location);
    this.marker.setVisible(true);
}

Mapping.prototype.updateAutocomplete = function(markerOrLatLng) {
	var asdfsad;
	if (markerOrLatLng.lat) {
		asdfsad = new google.maps.LatLng(markerOrLatLng.lat, markerOrLatLng.lng);
	} else {
		asdfsad = markerOrLatLng.getPosition();
	}
	var addressDisplayId = this.addressId;
	this.geocoder.geocode({'location': asdfsad}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			if (results[0]) {
				var locationAddress = $("#" + addressDisplayId);
				if (locationAddress.prop("tagName") == "INPUT") {
					locationAddress.val(results[0].formatted_address);
				} else {
					locationAddress.html(results[0].formatted_address);
				}
			} else {
				window.alert('No results found');
			}
		} else {
			window.alert('Geocoder failed due to: ' + status);
		}
	});
}