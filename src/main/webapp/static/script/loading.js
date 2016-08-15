var loadingScreen = "<div id='loading' class='text-center' style='width:100%;position:fixed;top:10px;'><div style='width:30%;margin:auto;background:#fa9797;height:100px;'>Loading...<br>Please Wait</div></div>";
var startLoading = function($body) {
	console.log("test");
	$body.children().last().after(loadingScreen);
}
var endLoading = function($loading) {
	$loading.remove();
}