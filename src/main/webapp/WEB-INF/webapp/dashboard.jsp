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
    <script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB3rwW9biPQiijfhhR9YZagdNrf_f3duvM&callback=initMap"></script>

    <jsp:include page="/WEB-INF/tags/mapjs.jspf"/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top">

	<t:header /> 

    <div class="container">
    	<h1>Hello ${currentUser.email}, welcome to your dashboard.</h1>

        <p> This is where you can keep track of POIs that you're working on. </p> 
    	
    	<c:if test="${not empty user and not empty user.isEditor and isEditor}">
            <div class=""></div>
    		<t:wikiTable name="Draft POIs" wikiList="${draftPOIs}" /> 
    		<t:wikiTable name="Created POIs" wikiList="${createdPOIs}" />
    		<t:wikiTable name="Pubished POIs" wikiList="${publishedPOIs}" /> 
    	</c:if>
    </div> 
		
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>

</body>

</html>