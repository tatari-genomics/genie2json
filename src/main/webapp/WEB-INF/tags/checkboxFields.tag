<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.util.List"
              description="Names in the list" %>
<%@ attribute name="itemValue" required="false" rtexprvalue="true"
              description="itemValue attribute" %>

<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error has-feedback' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    
    <div class="${cssGroup}" id="nomargin">    	
            <form:checkboxes path="${name}" items="${names}" itemValue="${itemValue}" />
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                <div class="alert alert-danger">In order to submit a survey sheet at least one street must be surveyed. Please check at least one of the above boxes.</div>
            </c:if>
</div>
</spring:bind>