<%-- simple input form element --%> 
<%@ tag description="A simple text area" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
<%-- <%@ attribute name="poi" required="true" type="java.util.Map" %> --%>

 
<div class="form-group">
    <label for="${name}">${label} 
        <span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
    </label>
    <textarea class="form-control" name="${name}" rows="6"></textarea>
    <span id="${name}HelpText" class="help-block hidden">${helpText}</span>
</div>