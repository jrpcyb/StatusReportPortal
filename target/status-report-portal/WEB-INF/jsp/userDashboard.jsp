<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "User : Dashboard";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Home</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>
<!-- Notification -->
<c:if test="${fn:length(notifymsg) > 0}">
	<div class="col_3"></div>
	<div class="col_6">
		<div class="notice success" id="deleteNotify">
			<i class="icon-ok icon-large"></i> ${notifymsg } <a href="#close"
				class="icon-remove"></a>
		</div>
	</div>
	<div class="col_3"></div>
	<div class="clearfix"></div>
</c:if>
<!-- End of Notification -->
<jsp:include page="chooseProject.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>
<!-- Footer -->
<jsp:include page="footer.jsp" />
