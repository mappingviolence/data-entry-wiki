<%-- form element allows you to add sources --%> 
<%@ tag description="edit sources" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
<%@ attribute name="dataList" required="true" type="java.util.List"%>

<div class="form-group">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
            <label for="${name}hiddeninputforlabel"> ${label}
            	<span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
            </label>
            <input class="form-control hidden" type="text" name="${name}hiddeninputforlabel">
        </div>
    </div>
    
    <!-- hidden input --> 
    <div class="hidden"> 
        <div data-id="${name}">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">    
                    <div class="form-group">
                        <label for="${name}"> <br> </label> <!-- empty label for style -->
                        <input class="form-control" type="text" name="${name}" placeholder="type source here">
                        <input type="hidden" class="hidden" />
                        <a class="removebutton" data-parent="4" href="#">remove</a>
                    </div> 
                </div> 
                <div class="col-sm-4 col-md-3">
                    <t:comments name="locationrationale" comments="${thisPOI.locationRationale.comments}" />
                </div>
            </div> 
        </div>
    </div>
    <!-- END hidden input --> 

    <!-- populate items --> 
    <div id="${name}Container">
        <c:forEach var="item" items="${dataList}">
            <div data-id="${name}">
                <div class="row">
                    <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">    
                        <div class="form-group">
                            <label for="${name}"> <br> </label> <!-- empty label for style -->
                            <input class="form-control" type="text" name="${name}" placeholder="type source here">
                            <input type="hidden" class="hidden" />
                            <a class="removebutton" data-parent="4" href="#">remove</a>
                        </div> 
                    </div> 
                    <div class="col-sm-4 col-md-3">
                        <t:comments name="locationrationale" comments="${thisPOI.locationRationale.comments}" />
                    </div>
                </div> 
            </div>
        </c:forEach> 
    </div> 
    <!-- END populate items --> 

    <div class="row">
        <div class="text-center col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
            <button id="${name}Btn" type="button" class="btn btn-default add_field_button">Add ${label}</button>
            <span id="${name}HelpText" class="help-block hidden">
                ${helpText}
            </span>
        </div>
    </div> 

</div> 
