<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Admin : Dashboard";
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

<a href="${pageContext.request.contextPath}/admin/configureTaskPage">
<div class="col_12">
	<h4>Customize User's Task Page&nbsp;&nbsp;<i class="icon-chevron-right icon-large"></i></h4>
</div>
</a>
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

<c:if test="${currEmpid != 0}">
<hr class="alt1 col_5" style="text-align:center;margin-left:30%;"/>
<div class="col_12">
	<h4 style="color:rgba(194, 15, 15, 0.86);">Log Your Task</h4>
</div>
<jsp:include page="chooseProject.jsp" />
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>
<!-- Footer -->
<jsp:include page="footer.jsp" />
