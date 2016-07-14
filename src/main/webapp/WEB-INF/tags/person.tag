<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>


<div class="form-group">
    <div id="%{name}Wrapper" class="input_fields_wrap">
        <label for="${name}"> ${label}
        	<span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
        </label>
        <span id="${name}HelpText" class="help-block hidden">
		    ${helpText}
	    </span>
        <c:choose>
            <c:when test="${not empty poi and not empty poi.[name]}">
                <c:forEach var="${name}" items="${poi.[name]}" varStatus="num">

                	<%-- comments 
                    <c:set var="comment" value="secondarySource${num.count}Comments"></c:set>
                    <c:choose>
                        <c:when test="${poi.rejected and not empty comments.comments[comment]}">
                            <p> <b>Admin Comment:</b> ${comments.comments[comment]}</p>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose> --%>
                    <div class="form-group">
                        <input type="text" class="form-control" name="${name}[]" value="${source}" />
                        <a href="#" class="remove_field">Remove</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:when test="${fn:length(orgSecondarySources) gt 0}">
                <c:forEach var="source" items="${orgSecondarySources}" varStatus="num">
                    <c:set var="comment" value="secondarySource${num.count}Comments"></c:set>
                    <c:choose>
                        <c:when test="${poi.rejected and not empty comments.comments[comment]}">
                            <p><b>Admin Comment:</b> ${comments.comments[comment]}</p>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                    <div class="form-group">
                        <input type="text" class="form-control" name="secondarySource[]" value="${secondarySource}" />
                        <a href="#" class="remove_field">Remove</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <input type="text" class="form-control" name="secondarySource[]" />
                    <a href="#" class="remove_field">Remove</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>