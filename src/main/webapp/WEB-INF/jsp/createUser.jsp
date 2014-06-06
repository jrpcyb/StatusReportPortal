<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Create/Edit User";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<c:choose>
		<c:when test="${param.empid > -1}">
			<h5>Edit Resource</h5>
		</c:when>

		<c:otherwise>
			<h5>Create A Resource</h5>
		</c:otherwise>
	</c:choose>
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
<form:form method="post" modelAttribute="user" action="showUsers">
	<div class="col_3"></div>
	<div class="col_9" style="text-align: left;">
		<c:choose>
			<c:when test="${param.empid > -1}">

			</c:when>

			<c:otherwise>
				<div class="col_3">
					<form:label path="empid">Employee ID<span class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:input path="empid" cssClass="col_4" type="number" />
					&nbsp;<span style="color: #9B089B;">(*Numbers only, Ex - 12166)</span>
				</div>
			</c:otherwise>
		</c:choose>

		<div class="col_3">
			<form:label path="firstname">First Name<span class="req">*</span>
			</form:label>
		</div>
		<div class="col_9">
			<form:input path="firstname" cssClass="col_4" />
		</div>
		<div class="col_3">
			<form:label path="lastname">Last Name<span class="req">*</span>
			</form:label>
		</div>
		<div class="col_9">
			<form:input path="lastname" cssClass="col_4" />
		</div>
		<div class="col_3">
			<form:label path="username">Username<span class="req">*</span>
			</form:label>
		</div>
		<div class="col_9">
			<form:input path="username" cssClass="col_4" />
		</div>
		<c:choose>
			<c:when test="${param.empid > -1}">

			</c:when>

			<c:otherwise>
				<div class="col_3">
					<form:label path="password">Password<span class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:password path="password" cssClass="col_4" />
				</div>
			</c:otherwise>
		</c:choose>

		<div class="col_3">
			<form:label path="userType">Resource Type<span
					class="req">*</span>
			</form:label>
		</div>
		<div class="col_9">
			<form:select path="userType" cssClass="col_4">
				<form:option value="" label="---Select---" />
				<form:option value="2">User</form:option>
				<form:option value="1">Manager/Lead</form:option>
			</form:select>
		</div>
	</div>
	<div class="col_12" style="height:3em;">
		<div class="col_3"></div>
		<div class="col_6" style="text-align:left;">
			<span class="req" style="font-weight:normal;">(*Mandatory)</span>
		</div>
		<div class="col_3"></div>
	</div>
	<div class="col_12">
		<c:choose>
			<c:when test="${param.empid > -1}">

				<form:button class="blue">
					<i class="icon-upload">&nbsp;Update</i>
				</form:button>
				<input type="hidden" name="hidempid" value="${param.empid }" />
				<input type="hidden" name="hideOldUserType"
					value="${user.userType }" />
			</c:when>

			<c:otherwise>

				<form:button class="blue">
					<i class="icon-user">&nbsp;Create</i>
				</form:button>
			</c:otherwise>
		</c:choose>
	</div>
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$("#empid").val("");
		$("#empid").attr('required', '');
		$("#firstname").attr('required', '');
		$("#lastname").attr('required', '');
		$("#username").attr('required', '');
		$("#password").attr('required', '');
		$("#userType").attr('required', '');
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>
<!-- Footer -->
<jsp:include page="footer.jsp" />
