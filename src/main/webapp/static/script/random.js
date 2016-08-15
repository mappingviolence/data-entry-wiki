var randomId = function(callback) {
	$.ajax({
		method: "GET",
		url: "/mapviz/random"
	}).done(function(data) {
		callback(data.data);
	});
}