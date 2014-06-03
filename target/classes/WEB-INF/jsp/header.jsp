<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<!-- META -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />



<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/kickstart.css"
	media="all" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/style.css" media="all" />
	
<!-- Javascript -->
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/kickstart.js"></script>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<!-- Load jQuery UI Main JS  -->
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- Load SCRIPT.JS which will create datepicker for input field  -->

<!-- KICKSTART -->


<title><%=request.getParameter("pageTitle")%></title>
</head>
<body>
	<ul class="navbar menu grid flex">
		<!-- <ul> -->
		<li><a href="${pageContext.request.contextPath}"
			style="font-size: 1.0em;"><span>Status Report</span>&nbsp;Portal</a></li>
		<li><span style="font-size: 0.8em;">Beta</span></li>
		<li class="right"><a
			href="${pageContext.request.contextPath}/logout"> <i
				class="icon-signout"></i><span>Log</span>&nbsp; out
		</a></li>
		<c:if test="${role == 'admin' }">

			<li class="right"><a
				href="${pageContext.request.contextPath}/admin/showUsers"><i
					class="icon-group"></i><span>Reso</span>urces</a>
				<ul style="display: none;">
					<li><a
						href="${pageContext.request.contextPath}/admin/createUser"
						style="text-transform: capitalize; border: 1px solid #fff;">
							Create User </a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/showUsers"
						style="text-transform: capitalize; border: 1px solid #fff;">
							Show Users </a></li>
				</ul></li>
			<li class="right"><a
				href="${pageContext.request.contextPath}/admin/createProject"><i
					class="icon-inbox"></i><span>Pro</span>jects</a>
				<ul style="display: none;">
					<li><a
						href="${pageContext.request.contextPath}/admin/createProject"
						style="text-transform: capitalize; border: 1px solid #fff;">
							Create Project </a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/assignProject"
						style="text-transform: capitalize; border: 1px solid #fff;">
							Assign Project </a></li>
				</ul></li>
		</c:if>
		<li class="right"><a href="${pageContext.request.contextPath}"><i
				class="icon-tasks"></i><span>Ta</span>sk</a>
			<ul style="display: none; min-width: 110px;">
				<c:if test="${currEmpid != 0 }">
				<li><a href="${pageContext.request.contextPath}"
					style="text-transform: capitalize; border: 1px solid #fff; min-width: 100px;">New
						Task </a></li>
				
				<li><a href="${pageContext.request.contextPath}/admin/viewTask"
					style="text-transform: capitalize; border: 1px solid #fff; min-width: 100px;">View
						Task </a></li>
				</c:if>
				<c:if test="${role == 'admin' }">
					<li><a
						href="${pageContext.request.contextPath}/admin/configureTaskPage"
						style="text-transform: capitalize; border: 1px solid #fff; min-width: 100px;">Configure
							Task</a></li>
				</c:if>
				<c:if test="${role == 'admin' }">
					<li><a href="${pageContext.request.contextPath}/admin/report"
						style="text-transform: capitalize; border: 1px solid #fff; min-width: 100px;">Save
							Report</a></li>
				</c:if>
			</ul></li>
		<c:set var="str1" value="${fn:substring(username, 0, 2)}" />
		<c:set var="str2" value="${fn:substringAfter(username,str1)}" />
		<li class="right"><a href="#"><i class="icon-user"></i><span>${str1}</span>${str2}</a></li>

		<!-- </ul> -->
	</ul>
	<div class="grid">
		<div class="col_12"
			style="margin-top: 100px; margin-bottom: 10%; padding: 10px;">
			<div class="center">