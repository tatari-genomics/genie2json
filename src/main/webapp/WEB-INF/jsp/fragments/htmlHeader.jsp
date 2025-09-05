<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="pragma" content="no-cache"> 
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="Cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0">   

	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <spring:url value="/resources/css/cosd.css" var="cosdCss"/>
    <link href="${cosdCss}" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
  	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
  		<script src="<c:url value="/resources/js/Chart.js" />"></script>
  		<script src="<c:url value="/resources/js/chartjs-plugin-datalabels.min.js" />"></script>


	<script>
	
	
	function showLoading() {
    		document.getElementById('loadingmsg').style.display = 'block';
    		document.getElementById('loadingover').style.display = 'block';
	}
	

	
	function myAccFunc(elementId) {
    		var x = document.getElementById(elementId);
    		if (x.className.indexOf("w3-show") == -1) {
        		x.className += " w3-show";
        		x.previousElementSibling.className += " w3-green";
    		} else { 
        		x.className = x.className.replace(" w3-show", "");
        		x.previousElementSibling.className = 
        		x.previousElementSibling.className.replace(" w3-green", "");
    		}
	}
$('.tooltip-r').tooltip();

</script>


 </head>
