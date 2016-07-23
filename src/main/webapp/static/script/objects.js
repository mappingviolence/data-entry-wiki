var POI = function() {
	this.title;
	this.date;
	this.description;
	this.location;
	this.locationRationale;
	this.victims = [];
	this.aggressors = [];
	this.tags = [];
	this.primarySources = [];
	this.secondarySources = [];
	this.researchNotes;
}

var SimpleFormField = function(id, name, value) {
	this.id = id;
	this.name = name;	
	this.value = value;
	this.comments = [];
}

var Comment = function(id, author, commentText) {
	this.id = id;
	this.author = author;
	this.commentText = commentText;
}

var User = function(id, email, role) {
	this.id = id;
	this.email = email;
	this.role = role;
}

var Date = function(year, month, day, modifier) {
	this.year = year;
	this.month = month;
	this.day = day;
	this.modifier = modifier;
}

var Point = function(lat, lng) {
	this.point = [lat, lng];
}

var Person = function() {
	this.identities = [];
}

var Identity = function(category, simpleFormField) {
	this.category = category;
	this.name = simpleFormField.name;
	this.id = simpleFormField.id;
	this.value = simpleFormField.value;
	this.comments = simpleFormField.comments;
}