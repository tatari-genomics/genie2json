<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="type" required="false" rtexprvalue="true"
              description="Type of input field" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true"
              description="Optional placeholder text" %>
<%@ attribute name="min" required="false" rtexprvalue="true"
              description="Sets min number on number fields" %>
<%@ attribute name="max" required="false" rtexprvalue="true"
              description="Sets max number on number fields" %>
<%@ attribute name="disabled" required="false" rtexprvalue="true"
              description="Sets greyed out fields" %>
              
<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
    	 <c:if test="${not valid}">
        	<label for="${name}" >${label}</label>
        </c:if>
        <c:if test="${valid}">
        	<label for="${name}" class="green">${label} <span class="glyphicon glyphicon-ok"></span></label>
       	</c:if>
       	<form:input class="form-control" aria-label="${label}" type="${type}" id="${name}" path="${name}" min="${min}" max="${max}" placeholder="${placeholder}" disabled="${disabled}" aria-describedby="desc_${name}"/>
       	<span id="desc_${name}">${placeholder}</span>
            <c:if test="${valid}">
                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
            </c:if>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="red">${status.errorMessage}</span>
            </c:if>
     </div>
</spring:bind>
