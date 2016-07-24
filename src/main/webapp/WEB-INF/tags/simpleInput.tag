<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
 
<div class="form-group">
    <label for="${name}">${label}
        <span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
    </label>
    <input id="${id}" class="form-control" type="text" name="${name}">
    <span id="${name}HelpText" class="help-block hidden">${helpText}</span>
</div>