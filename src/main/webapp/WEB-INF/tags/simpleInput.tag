<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
<%-- <%@ attribute name="poi" required="true" type="java.util.Map" %> --%>

 
<div class="form-group">
    <label for="title">${label}: 
        <span id="titleHelp" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
    </label>
    <c:choose>
        <c:when test="${not empty poi and not empty poi[name]}">
            <input class="form-control" type="text" name="${name}" value="${poi[name]}">
        </c:when>
        <c:when test="${not empty originals and not empty originals[name]}">
            <input class="form-control" type="text" name="${name}" value="${originals[name]}">
        </c:when>
        <c:otherwise>
            <input class="form-control" type="text" name="${name}">
        </c:otherwise>
    </c:choose>
        <span id="titleHelpText" class="help-block hidden">
            ${helpText}
        </span>
</div>