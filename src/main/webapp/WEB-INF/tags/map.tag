<%@ tag description="A map with the POI location" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

  <script src="/static/script/geocoding-places.js"></script>
            <script src="/static/script/map.js"></script>
            <script src="/static/script/update-geoinfo.js"></script>
            <script>
            	var m;
            	var ready = function() {
            		$(document).ready(function() {
            			var preLoad = false;
            			if ($("#lat").val() != "" && $("#lng").val() != "") {
            				preLoad = true;
            			}
            			//
            			initMap("map");
            			//
            			m = new Mapping();
            			m.init("lat", "lng", "locationInput", map);
            			m.marker.setDraggable(true);
            			m.marker.setVisible(true);
            			//
            			initGCP();
            			var g = new Geocoding();
            			/* 
            			 * Texas Center:
            			 * 31.9686° N, 99.9018° W
            			 */
            			g.autocomplete(31.9686 - 2.0, -99.9018 - 2.0, 31.9686 + 2.0, -99.9018 + 2.0, "locationInput", null, function(autocomplete) {
            				
            				m.marker.addListener('dragend', function() {
            					m.updateAutocomplete(m.marker);
            					m.updateLatLng(m.marker);
            				});
            				autocomplete.addListener('place_changed', function() {
            					m.updateMap(autocomplete);
            					m.updateLatLng(autocomplete);
            				});
            				
            				$("#lat").on("change", function() {
            					if (isNaN($(this).val())) {
            						return;
            					}
            					m.updateMap({ "lat" : parseFloat($(this).val()), "lng" : parseFloat($("#lng").val()) });
            					m.updateAutocomplete({ "lat" : parseFloat($(this).val()), "lng" : parseFloat($("#lng").val()) });
            				});
            				
            				$("#lng").on("change", function() {
            					if (isNaN($(this).val())) {
            						return;
            					}
            					m.updateMap({ "lng" : parseFloat($(this).val()), "lat" : parseFloat($("#lat").val()) });
            					m.updateAutocomplete({ "lat" : parseFloat($(this).val()), "lng" : parseFloat($("#lng").val()) });
            				});
            			});
            			
            			if (preLoad) {
            				m.updateMap({ "lat" : parseFloat($("#lat").val()), "lng" : parseFloat($("#lng").val()) });
        					m.updateAutocomplete({ "lat" : parseFloat($("#lat").val()), "lng" : parseFloat($("#lng").val()) });
            			}
            		});
            	}
            </script>
            <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCVj2Hr9SExCNWU3aA2kCSmIRDZ27QrqV0&libraries=places&callback=ready" async defer></script>
