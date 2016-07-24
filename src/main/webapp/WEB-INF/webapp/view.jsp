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
    			var poi = new POI();
    			var title = buildTitle();
    			
    			poi.title = title;
    			
    			var poiString = JSON.stringify(poi);
    			$.ajax({
    				method: "PUT",
    				data: poiString
    			}).done(function(data) {
    				console.log(data);
    			}).fail(function(data) {
    				console.log(data);
    			});
    		});
    	});
    	
    	var buildTitle = function() {
    		var id = $("#title input[type='hidden']").val();
    		var text = $("input[name='title']").val();
    		
    		var title = new SimpleFormField(id, "Title", text);
    		return title;
    	}
    </script>
</head>


<!-- js -->
<script>
  $(document).ready(function() {

    /* make edit button appear */
    $("#editbutton").on("click", function() {
      $("#edit").toggleClass("hidden");
      $("#view").toggleClass("hidden");
      google.maps.event.trigger(map1, 'resize');
      if ($("#latEdit").val() != "" && $("#lngEdit").val() != "") {
	      m1.updateMap({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) }, 12);
		  m1.updateAutocomplete({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) });
      } else {
    	  m1.updateMap({ "lat" : 31.9686, "lng" : -99.9018 }, 5);
		  //m1.updateAutocomplete({ "lat" : 31.9686, "lng" : -99.9018 });
      }
    })

    /* populating the form */
    var title = $("#title h1").text(); 
    $("input[name='title']").val(title);
    var date = $("#date").text();
    $("input[name='date']").val(date); 
    var description = $("#description p").text();
    $("textarea[name='description']").text(description);
    var latitude = $("#lat").text(); 
    $("input[name='lat']").val(latitude);
    var longitude = $("#lng").text(); 
    $("input[name='lng']").val(longitude);
    var locationrationale = $("#locationrationale").text();
    $("textarea[name='locationrationale']").text(locationrationale);
    var researchnotes = $("#researchnotes p").text();
    $("textarea[name='researchnotes']").text(researchnotes);

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
  });
</script>

<body id="page-top">
    <t:header page="pool"/> 

    <!-- edit form --> 
    <div class="container hidden" id="edit" style="width:800px;">
      <t:edit/>
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
            <t:citations title="Primary Sources" citations="${thisPOI.primarySources}"/>
            <t:citations title="Secondary Sources" citations="${thisPOI.secondarySources}"/>
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