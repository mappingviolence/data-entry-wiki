<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
<%@ attribute name="location" required="true" %>

<div class="form-group">
    <div id="%{name}Wrapper" class="input_fields_wrap">
        <label for="${name}"> ${label}
        	<span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
        </label>
        <span id="${name}HelpText" class="help-block hidden">
		    ${helpText}
	    </span>
	</div> 
</div> 
<div class="form-group">
    <div class="text-center">
	    <button id="primarySourcesBtn" type="button" class="btn btn-default add_field_button">Add ${label}</button>
	</div>
</div>