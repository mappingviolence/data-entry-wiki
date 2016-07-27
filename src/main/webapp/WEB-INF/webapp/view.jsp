<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mapping Violence</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Crimson+Text:400i,400|Montserrat:400,700" rel="stylesheet">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="static/css/animate.min.css" type="text/css">

    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-material-design.css">
    <link rel="stylesheet" type="text/css" href="static/css/ripples.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="static/css/corrections.css" type="text/css">
    <link rel="stylesheet" href="static/css/mappingviolence.css" type="text/css">
    
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <script src="static/script/objects.js"></script>

    <jsp:include page="/WEB-INF/tags/mapjs.jspf"/>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/showdown/1.4.2/showdown.min.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script>
    	$(document).ready(function() {
    		$("button#save").on("click", function(e) {
    			e.preventDefault();
    			var loading = "<div id='loading' class='text-center' style='width:100%;position:fixed;top:10px;'><div style='width:30%;margin:auto;background:#fa9797;height:100px;'>Loading. Please Wait</div></div>";
    			$("body").children().last().after(loading);
    			var poi = new POI();
    			var title = buildTitle();
    			var date = buildDate();
    			var description = buildDescription();
    			var location = buildLocation();
    			var locationRationale = buildLocationRationale();
    			
    			var tags = buildTags();
    			var primarySources = buildPrimarySources();
    			var secondarySources = buildSecondarySources();
    			var researchNotes = buildResearchNotes();
    			
    			poi.title = title;
    			//poi.date = date;
    			poi.description = description;
    			poi.location = location;
    			poi.locationRationale = locationRationale;
    			
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
    				$("#loading").remove();
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
    		var id = $("#date input[type='hidden']").val();
    		var text = $("input[name='date']").val();
    		
    		var date = new MyDate();
    		
    		var dateField = new SimpleFormField(id, "Date", date);
    		return dateField;
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
    		console.log(lat, lng);
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
    	
    	var buildTags = function() {
    		var ids = [];
    		var $tags = $("#tagContainer div[data-id='tag']");
    		var $tagsId = $tags.children("input[type='hidden']");
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
    		var $primarySourcesId = $primarySources.children("input[type='hidden']");
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
    		var $secondarySources = $("#secondarysourceContainer div[data-id='secondarysource']");
    		var $secondarySourcesId = $secondarySources.children("input[type='hidden']");
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
    </script>
</head>


<!-- js -->
<script>
  $(document).ready(function() {
	  
	// pull out to random.js
	var randomId = function(callback) {
		$.ajax({
			url: "/mapviz/random"
		}).done(function(data) {
			callback(data.data);
		});
	}

    /* make edit button appear */
    $("#editbutton").on("click", function() {
      $("#edit").toggleClass("hidden");
      $("#view").toggleClass("hidden");
      google.maps.event.trigger(map1, 'resize');
      if ($("#latEdit").val() != "" && $("#lngEdit").val() != "") {
	      m1.updateMap({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) }, setZoom);
		  m1.updateAutocomplete({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) });
      } else {
    	  m1.updateMap({ "lat" : 31.9686, "lng" : -99.9018 }, unsetZoom);
		  //m1.updateAutocomplete({ "lat" : 31.9686, "lng" : -99.9018 });
      }
    });

    /* help button text */
    var helpBtns = $("label>span[role='helpBtn']");
    for (var i = 0; i < helpBtns.length; i++) {
        var helpBtn = helpBtns[i];
        $(helpBtn).on("click", function(e) {
            e.preventDefault();
            var id = this.id;
            $("#" + id + "Text").toggleClass("hidden");
        });
    }
    
    // Configure remove
    $(".removebutton").on("click", function(e) { 
      e.preventDefault();
      $(this).parent().remove(); 
    });
    
    /* add new victim */ 
    $("#victimBtn").on("click", function(e) { 
      e.preventDefault();
      var $victim = $("div.hidden div[data-id='victim']").clone();
      $("#victimContainer").append($victim);
      $(".victimidentityBtn").off(); // so that event handlers don't build up 

      /* add victim identity */
      $(".victimidentityBtn").on("click", function(e, i, callback) {
          e.preventDefault(); 
          var $victimidentity = $("div.hidden div[data-id='victimidentity']").clone();
          var $this = $(this);
          randomId(function(id) {
        	  $victimidentity.children("input[type='hidden']").val(id);
              console.log($victimidentity);
              $this.before($victimidentity);
              $(".removebutton").off();
              $(".removebutton").on("click", function(e) { 
                e.preventDefault();
                $this.parent().remove(); 
              });
              if (callback) {
            	  callback(i);
              }
          });        
          
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
      });
      
      /* remove button */ 
      $(".removebutton").off();
      $(".removebutton").on("click", function(e) { 
        e.preventDefault();
        $(this).parent().remove(); 
      });
    });
    
    /* add new aggressor */ 
    $("#aggressorBtn").on("click", function(e) { 
      e.preventDefault();
      var $aggressor = $("div.hidden div[data-id='aggressor']").clone();
      $("#aggressorpersons").append($aggressor);
      $(".aggressoridentityBtn").off(); // so that event handlers don't build up 

      /* add aggressor identity */
      $(".aggressoridentityBtn").on("click", function(e) {
          e.preventDefault(); 
          var $aggressoridentity = $("div.hidden div[data-id='aggressoridentity']").clone();
          $(this).parent().before($aggressoridentity);

          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
      });

      /* remove button */
      $(".removebutton").off();
      $(".removebutton").on("click", function(e) { 
        e.preventDefault();
        $(this).parent().remove(); 
      });
    });

    /* add new tags */
    $("#tagBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='tag']").clone();
      randomId(function(id) {
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#tagContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
          if (callback) {
        	  callback(i);
          }
      })
    })
    
    /* add new primary sources */
    $("#primarysourceBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='primarysource']").clone();
      randomId(function(id) {
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#primarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
          if (callback) {
        	  callback(i);
          }
      })
    })

    /* add new secondary sources */ 
    $("#secondarysourceBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='secondarysource']").clone();
      randomId(function(id) {
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#secondarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
          if (callback) {
        	  callback(i);
          }
      })
    })
    
    /* populating the form */
    var title = $("#title h1").text(); 
    $("input[name='title']").val(title);
    var date = $("#date h3 strong").text();
    $("input[name='date']").val(date); 
    var description = $("#description p").text();
    $("textarea[name='description']").text(description);
    var latitude = $("#lat").text();
    $("input[name='lat']").val(latitude);
    var longitude = $("#lng").text();
    $("input[name='lng']").val(longitude);
    var locationrationale = $("#locationrationale").text();
    $("textarea[name='locationrationale']").text(locationrationale);
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
    
    
    var researchnotes = $("#researchnotes p").text();
    $("textarea[name='researchnotes']").text(researchnotes);
    
    var converter = new showdown.Converter();
    var text = $("#description p").text();
    var html = converter.makeHtml(text);
    $("#description p").html(html);
  });
</script>

<body id="page-top">
    <t:header page="pool"/> 

    <!-- edit form --> 
    <div class="container hidden" id="edit" style="width:800px;">
      <t:edit thisPOI="thisPOI"/>
    </div>

    <!-- wiki view --> 
    <div class="container" id="view" style="padding-bottom:40px;"> 
      <div class="container" id="header">     
        <div class="data-element"> 
          <div id="title">
          	<c:choose>
          		<c:when test="${empty thisPOI.title.value}">
          			<h1 style="display: inline-block;">Untitled</h1>
          		</c:when>
          		<c:otherwise>
          			<h1 style="display: inline-block;">${thisPOI.title.value}</h1>
          		</c:otherwise>
          	</c:choose>
            <input type="hidden" class="hidden" value="${thisPOI.title.id}" /> 
            <button type="button" class="btn btn-default btn-lg" id="editbutton" style="bottom: 10px">
              <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
            </button> 
          </div> 
          <div id="subtitle" class="grey"> 
            <h4> ID: ${id} </h4> 
            <h4><em> created by </em>${creator} | <em> last edited by </em>${lasteditor}<br><br></h4> 
          </div> 
        </div> 
      </div> 
      <!-- wiki data--> 
      <div class="container" id="main">
        <div class="row">
         <!-- left side view--> 
         <div class="col-md-7 offset-md-1">
          <div class="data-element" id="description">
            <p>${thisPOI.description.value}</p>
            <input type="hidden" class="hidden" value="${thisPOI.description.id}" />
          </div>
          <div class="data-element" id="citations"> 
          	<h2>Citations</h2>
            <t:citations typeClass="primarySource" title="Primary Sources" citations="${thisPOI.primarySources}"/>
            <t:citations typeClass="secondarySource" title="Secondary Sources" citations="${thisPOI.secondarySources}"/>
          </div> 
          <div class="data-element" id="researchnotes"> 
            <h2>Research Notes</h2> 
            <p>${thisPOI.researchNotes.value}</p>
            <input type="hidden" class="hidden" value="${thisPOI.researchNotes.id}" />
          </div>
        <!-- right side infobox--> 
        </div>
        <div class= "col-md-5">
          <t:infoBox thisPOI="${thisPOI}"/>
        </div> 
      </div>
    </div>
  </div> 
  
</body>

</html>