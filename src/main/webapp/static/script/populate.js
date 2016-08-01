$(document).ready(function() {
	/* populating the form */
    var title = $("#title h1").text(); 
    $("input[name='title']").val(title);
    var date = $("h3#date strong").text();
    $("input[name='date']").val(date); 
    var description = $("#description p").text();
    $("textarea[name='description']").text(description);
    var latitude = $("#lat").text();
    $("input[name='lat']").val(latitude);
    var longitude = $("#lng").text();
    $("input[name='lng']").val(longitude);
    var locationrationale = $("#locationrationale").text();
    $("textarea[name='locationrationale']").text(locationrationale);
    /* var $victims = $(".tag");
    for (var i = 0; i < $tags.size(); i++) {
    	$("#tagBtn").trigger("click", [i, function(j) {
    		var tagText = $($tags[j]).text();
        	var tagId = $($tags[j]).next().val();
    		var $tag = $("#tagContainer").children().last();
        	$tag.children("input[name='tag']").val(tagText);
        	$tag.children("input[type='hidden']").val(tagId);
    	}]);
    } */

 /* not necessary due to JSP for loops 

    var $tags = $(".tag");
    for (var i = 0; i < $tags.size(); i++) {
    	$("#tagBtn").trigger("click", [i, function(j) {
    		var tagText = $($tags[j]).text();
        	var tagId = $($tags[j]).next().val();
    		var $tag = $("#tagContainer").children().last();
        	$tag.children("input[name='tag']").val(tagText);
        	$tag.children("input[type='hidden']").val(tagId);
    	}]);
    }
    var $primarySources = $(".primarySource");
    for (var i = 0; i < $primarySources.size(); i++) {
    	$("#primarysourceBtn").trigger("click", [i, function(j) {
    		var primarySourceText = $($primarySources[j]).text();
        	var primarySourceId = $($primarySources[j]).next().val();
    		var $primarySource = $("#primarysourceContainer").children().last();
        	$primarySource.children("input[name='primarysource']").val(primarySourceText);
        	$primarySource.children("input[type='hidden']").val(primarySourceId);
    	}]);
    }
    var $secondarySources = $(".secondarySource");
    for (var i = 0; i < $secondarySources.size(); i++) {
    	$("#secondarysourceBtn").trigger("click", [i, function(j) {
    		var secondarySourceText = $($secondarySources[j]).text();
        	var secondarySourceId = $($secondarySources[j]).next().val();
    		var $secondarySource = $("#secondarysourceContainer").children().last();
        	$secondarySource.children("input[name='secondarysource']").val(secondarySourceText);
        	$secondarySource.children("input[type='hidden']").val(secondarySourceId);
    	}]);
    }

    */
    
    
    var researchnotes = $("#researchnotes p").text();
    $("textarea[name='researchnotes']").text(researchnotes);
})