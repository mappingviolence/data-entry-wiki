<%-- form element allows you to add sources --%> 
<%@ tag description="edit sources" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>

<div class="form-group">
    <label for="${name}"> ${label}
    	<span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
    </label>
    
    <div class="hidden"> 
        <div data-id="${name}">
            <input class="form-control" type="text" name="${name}" placeholder="type source here">
            <input type="hidden" class="hidden" />
            <a class="removebutton" href="#">remove</a> 
        </div> 
    </div>
    <div id="${name}Container"></div> 
    <div class="text-center">
        <button id="${name}Btn" type="button" class="btn btn-default add_field_button">Add ${label}</button>
    </div>
    <span id="${name}HelpText" class="help-block hidden">
	    ${helpText}
    </span>
</div> 
