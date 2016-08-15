$(document).ready(function() {
    $("button#save").on("click", function(e) {
		e.preventDefault();
		startLoading($("body"));
		var poi = new POI();
		var title = buildTitle();
		var date = null;
		buildDate()
			.done(function(data) {
				console.log("in done");
				date = data.data;
			}).complete(function() {
				console.log("in complete");
				var id = $("#date input[type='hidden']").val();
				var dateField = new SimpleFormField(id, "Date", date);
				date = dateField;
				
				var description = buildDescription();
				var location = buildLocation();
				var locationRationale = buildLocationRationale();
				var victims = buildVictims();
				var aggressors = buildAggressors();
				var tags = buildTags();
				var primarySources = buildPrimarySources();
				var secondarySources = buildSecondarySources();
				var researchNotes = buildResearchNotes();
				
				poi.title = title;
				poi.date = date;
				poi.description = description;
				poi.location = location;
				poi.locationRationale = locationRationale;
				poi.victims = victims;
				poi.aggressors = aggressors;
				poi.tags = tags;
				poi.primarySources = primarySources;
				poi.secondarySources = secondarySources;
				poi.researchNotes = researchNotes;
				
				var poiString = JSON.stringify(poi);
				console.log(poi, poiString);
				$.ajax({
					method: "PUT",
					data: poiString
				}).done(function(data) {
					console.log(data);
					var id = data.data;
					var url = "/mapviz/wikipage?id=" + id;
					window.location = url;
				}).fail(function(data) {
					console.log(data);
					alert("Data not saved.\nPlease fix errors and try again.");
				}).complete(function() {
					endLoading($("#loading"));
				});
			});
	});
});

var buildTitle = function() {
	var id = $("#title input[type='hidden']").val();
	var text = $("input[name='title']").val();
	
	var title = new SimpleFormField(id, "Title", text);
	return title;
}

var buildDate = function() {
	var text = $("input[name='date']").val();
	
	return $.ajax({
		method: "GET",
		url: "isValidDate?dateStr=" + text
	})
}

var buildDescription = function() {
	var id = $("#description input[type='hidden']").val();
	var text = $("textarea[name='description']").val();
	
	var description = new SimpleFormField(id, "Description", text);
	return description;
}

var buildLocation = function() {
	var id = $("#location input[type='hidden']").val();
	var lat = $("input#latEdit").val();
	var lng = $("input#lngEdit").val();
	if (lat == "" || lng == "") {
		return;
	}
	var point = new Point(lat, lng);
	
	var location = new SimpleFormField(id, "Location", point);
	console.log(location);
	return location;
}

var buildLocationRationale = function() {
	var id = $("#locationrationale input[type='hidden']").val();
	var text = $("textarea[name='locationrationale']").val();
	
	var locationRationale = new SimpleFormField(id, "Location Rationale", text);
	return locationRationale;
}

var buildVictims = function() {
	var victims = [];
	var $victims = $("#victimContainer").find("div[data-id='victim']");
	for (var i = 0; i < $victims.size(); i++) {
		var identities = [];
		var $victim = $($victims[i]);
		var $victimIdentities = $victim.find("div[data-id='victimidentity']");
		for (var j = 0; j < $victimIdentities.size(); j++) {
			var $victimIdentity = $($victimIdentities[j]);
			var id = $victimIdentity.find("input[data-type='hiddenvictim']").val();
			var category = $victimIdentity.find("select[name='victimCategory']").val();
			var value = $victimIdentity.find("input[name='victimIdentity']").val();
			var formField = new SimpleFormField(id, "Identity", value);
			var identity = new Identity(category, formField);
			identities.push(identity);
		}
		var victim = new Person();
		victim.identities = identities;
		victims.push(victim);
	}
	
	return victims;
}

var buildAggressors = function() {
	var aggressors = [];
	var $aggressors = $("#aggressorContainer").find("div[data-id='aggressor']");
	for (var i = 0; i < $aggressors.size(); i++) {
		var identities = [];
		var $aggressor = $($aggressors[i]);
		var $aggressorIdentities = $aggressor.find("div[data-id='aggressoridentity']");
		for (var j = 0; j < $aggressorIdentities.size(); j++) {
			var $aggressorIdentity = $($aggressorIdentities[j]);
			var id = $aggressorIdentity.find("input[data-type='hiddenaggressor']").val();
			var category = $aggressorIdentity.find("select[name='aggressorCategory']").val();
			var value = $aggressorIdentity.find("input[name='aggressorIdentity']").val();
			var formField = new SimpleFormField(id, "Identity", value);
			var identity = new Identity(category, formField);
			identities.push(identity);
		}
		var aggressor = new Person();
		aggressor.identities = identities;
		aggressors.push(aggressor);
	}
	
	return aggressors;
}

var buildTags = function() {
	var ids = [];
	var $tags = $("#tagContainer div[data-id='tag']");
	var $tagsId = $tags.find("input[type='hidden'][data-source]");
	for (var i = 0; i < $tagsId.size(); i++) {
		ids.push($($tagsId[i]).val());
	}
	var texts = [];
	var newTags = $("#tagContainer div[data-id='tag'] input[name='tag']");
	for (var i = 0; i < newTags.size(); i++) {
		texts.push($(newTags[i]).val());
	}
	
	var tags = [];
	for (var i = 0; i < texts.length; i++) {
		tags.push(new SimpleFormField(ids[i], "Tag", texts[i]));
	}
	
	return tags;
}

var buildPrimarySources = function() {
	var ids = [];
	var $primarySources = $("#primarysourceContainer div[data-id='primarysource']");
	var $primarySourcesId = $primarySources.find("input[type='hidden'][data-source]");
	for (var i = 0; i < $primarySourcesId.size(); i++) {
		ids.push($($primarySourcesId[i]).val());
	}
	var texts = [];
	var newPrimarySources = $("#primarysourceContainer div[data-id='primarysource'] input[name='primarysource']");
	for (var i = 0; i < newPrimarySources.size(); i++) {
		texts.push($(newPrimarySources[i]).val());
	}
	
	var primarySources = [];
	for (var i = 0; i < texts.length; i++) {
		primarySources.push(new SimpleFormField(ids[i], "Primary Source", texts[i]));
	}
	
	return primarySources;
}

var buildSecondarySources = function() {
	var ids = [];
	var $secondarySources = $("#secondarysourceContainer div[data-id='secondarysource'][data-source]");
	var $secondarySourcesId = $secondarySources.find("input[type='hidden']");
	for (var i = 0; i < $secondarySourcesId.size(); i++) {
		ids.push($($secondarySourcesId[i]).val());
	}
	var texts = [];
	var newSecondarySources = $("#secondarysourceContainer div[data-id='secondarysource'] input[name='secondarysource']");
	for (var i = 0; i < newSecondarySources.size(); i++) {
		texts.push($(newSecondarySources[i]).val());
	}
	
	var secondarySources = [];
	for (var i = 0; i < texts.length; i++) {
		secondarySources.push(new SimpleFormField(ids[i], "Secondary Source", texts[i]));
	}
	
	return secondarySources;
}

var buildResearchNotes = function() {
	var id = $("#researchnotes input[type='hidden']").val();
	var text = $("textarea[name='researchnotes']").val();
	
	var researchNotes = new SimpleFormField(id, "Research Notes", text);
	return researchNotes;
}