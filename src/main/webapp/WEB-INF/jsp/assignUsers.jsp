<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Assign Users To Project";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Assign Resources</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>
<div class="col_3"></div>
<div class="col_9" style="text-align: left;">
	<div class="col_4" style="text-align: right;">Name of Manager :</div>
	<div class="col_8">
		${managerName }
		<c:choose>
			<c:when test="${managerId == currEmpid }">
			(You)
			</c:when>
			<c:otherwise>
				<a
					href="createProject?project_code=${projectCode }&&project_id=${projectId}"
					style="padding-left: 3%;">Change Manager</a>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<c:choose>
	<c:when test="${fn:length(usersList) > 0}">
		<div class="col_12" style="padding-top: 2%;">Select the
			resources from below list for the project&nbsp;${projectName }&nbsp;:</div>
		<form:form method="post" modelAttribute="project" action="assignUsers">
			<div class="col_3"></div>
			<div class="col_6">
				<fieldset>
					<legend>Resources</legend>
					<c:forEach items="${usersList}" var="user">
						<form:checkbox path="empid" value="${user.empid}"
							id="${user.empid}" />
						<label for="${user.empid}" class="inline">${user.firstname}&nbsp;${user.lastname}</label>
						<br />
					</c:forEach>
				</fieldset>
			</div>
			<div class="col_3"></div>
			<div class="col_12">
				<form:button class="blue">
					<i class="icon-save">&nbsp;Submit</i>
				</form:button>
				<form:hidden path="project_name" value="${projectName }" />
			</div>
		</form:form>
		
	</c:when>

	<c:otherwise>
		<div class="col_12">${noUsersUnderManager }</div>
	</c:otherwise>
</c:choose>

<!-- footer -->
<jsp:include page="footer.jsp" />
