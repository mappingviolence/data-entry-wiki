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
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script> 
        $(document).ready(function(){
            $("#newdraft").on("click", function(e){
                e.preventDefault(); 
                $.ajax({
                    type: "POST",
                    url: "/mapviz/wikipage",
                    success: function(success){
                        var url = "wikipage?id="+success.data;
                        window.location.replace(url);
                    }, 
                });
            });
        });
    </script> 

</head>

<body id="page-top">

	<t:header page="dashboard"/> 

    <div class="container">
    	<h1>Hello ${currentUser.givenName}, welcome to your dashboard.</h1>

        <p> This is where you can keep track of POIs that you're working on.</p>

        <p> Drafts are only visible to you. When you think you draft is ready, you can send it to the database. Anyone on the team can see and contribute to POIs in the Database. When Professor Martinez decides that a POI is ready for publication, she will publish it to the Mapping Violence website. Our goal this summer is to get many POIs in the "published" category.    </p> 
    	<button class="btn btn-default btn-large" id="newdraft"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  New Draft</button>
    	<c:if test="${not empty currentUser and currentUser.isEditor()}">
            <div class=""></div>
    		<t:wikiTable name="Draft POIs" wikiList="${draftPOIs}" /> 
    		<t:wikiTable name="POIs in Database" wikiList="${reviewPOIs}" />
    		<t:wikiTable name="Pubished POIs" wikiList="${publishedPOIs}" /> 
    	</c:if>
    </div> 
		
</body>

</html>