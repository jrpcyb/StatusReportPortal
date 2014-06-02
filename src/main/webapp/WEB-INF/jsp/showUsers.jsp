<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Admin : Show Users";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>All Resources you have created</h5>
	<hr class="alt1">
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

<c:choose>
	<c:when test="${fn:length(users) > 0}">
		<c:set var="count" value="0" />
		<div class="col_12">
			<table class="striped tight sortable">
				<thead>
					<tr>
						<th>#</th>
						<th>Emp Id</th>
						<th>Username</th>
						<th>First Name</th>
						<th>Last name</th>
						<th>Type</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${users }" var="user">
						<tr>
							<td><c:out value="${count }" /></td>
							<td><c:out value="${user.empid }" /></td>
							<td>${user.username }</td>
							<td>${user.firstname }</td>
							<td>${user.lastname}</td>
							<td><c:choose>
									<c:when test="${user.userType == 1}">
										Admin
									</c:when>

									<c:when test="${user.userType == 2}">
										User
									</c:when>
								</c:choose></td>
							<td><a href="edit?empid=${user.empid }"><i
									class="icon-pencil"></i></a>&nbsp; <a
								href="delete?empid=${user.empid }" class="confirm"><i
									class="icon-minus-sign"></i></a></td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>

	<c:otherwise>
		<div class="col_12">
			<p>${noUsersMsg }</p>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#deleteNotify').delay(3000).fadeOut();
						$('.confirm')
								.click(
										function() {
											var answer = confirm("Are you sure you want to delete this resource?");
											if (answer) {
												return true;
											} else {
												return false;
											}
										});

					});
</script>
<!-- <script type="text/javascript"> 
      $(document).ready( function() {
        $('#deleteNotify').delay(1000).fadeOut();
      });
</script> -->
<!-- Footer -->
<jsp:include page="footer.jsp" />
