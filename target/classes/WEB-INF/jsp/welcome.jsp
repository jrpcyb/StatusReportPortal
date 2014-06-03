<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<!-- META -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- Javascript -->
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/kickstart.js"></script>

<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/kickstart.css"
	media="all" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/style.css" media="all" />
<title>Welcome : Daily Status Report Portal</title>
</head>
<body>
	<nav class="navbar">
		<ul>
			<li><a href="${pageContext.request.contextPath}"
				style="font-size: 1.1em;"><span>Status Report</span>&nbsp;Portal</a></li>
			<li><span style="font-size: 0.8em;">Beta</span></li>
		</ul>
	</nav>
	<div class="callout callout-top clearfix">
		<div class="grid">
			<div class="col_12" style="margin-top: 100px; background: none;">
				<div class="center">
					<c:choose>
						<c:when test="${role == 'admin'}">
							<jsp:forward page="adminDashboard.jsp" />
						</c:when>

						<c:when test="${role == 'user'}">
							<jsp:forward page="userDashboard.jsp" />
						</c:when>
					</c:choose>

					<!-- Notification -->
					<c:if test="${fn:length(param.message) > 0}">
						<div class="col_3"></div>
						<div class="col_6" style="background: none;">
							<div class="notice error" style="color: rgb(194, 5, 5);">
								<i class="icon-remove-sign icon-large"></i>${param.message} <a
									href="#close" class="icon-remove"></a>
							</div>
						</div>
						<div class="col_3"></div>
						<div class="clearfix"></div>
					</c:if>
					<!-- End of Notification -->

					<div class="col_3"></div>
					<div class="col_6"
						style="background: none; background-color: rgba(0, 0, 0, 0.27); padding-top: 3%; padding-bottom: 3%">
						<form:form method="post" modelAttribute="user">
							<p>
								<form:label path="username">Username</form:label>
								&nbsp;
								<form:input path="username" cssClass="col_7"
									cssStyle="background:#fff;" />
							</p>
							<p>
								<form:label path="password">Password</form:label>
								&nbsp;
								<form:password path="password" cssClass="col_7"
									cssStyle="background:#fff;" />
							</p>
							<p>
								<form:button class="medium orange" style="margin:2% 0 0 4%;">
									<i class="icon-signin">&nbsp;Login</i>
								</form:button>
							</p>
						</form:form>
					</div>
					<div class="col_3"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#username").attr('required', '');
		$("#password").attr('required', '');
	</script>

	<!-- Footer -->
	<div class="clear"></div>
	<div id="footer">
		Copyright&nbsp;&copy;2014 Cybage Software Pvt. Ltd. All Rights
		Reserved. <span style="float: right; margin-right: 20px;">*Best
			viewed in Mozilla Firefox and Google Chrome</span>
	</div>
</body>
</html>