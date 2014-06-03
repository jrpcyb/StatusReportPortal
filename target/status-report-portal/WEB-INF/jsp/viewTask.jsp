<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- Header -->
<%
	String pageTitle = "View your Task Details";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>All Task you have submitted</h5>
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
<div class="col_12">
	<form:form action="${pageContext.request.contextPath}/admin/viewTask"
		method="get" modelAttribute="task">
Check for previous date<span class="req">*</span> : <input type="text"
			id="datepicker" name="for_date" />
		<form:button class="blue small">
			<i class="icon-calendar">&nbsp;Submit</i>
		</form:button>
	</form:form>
</div>
<c:if test="${day != 'today'}">
	<div class="col_12" style="margin: 10px;">
		<a href="${pageContext.request.contextPath}/admin/viewTask">Check
			Today's Task Details</a>
	</div>
</c:if>
<hr class="alt2" />
<c:choose>
	<c:when test="${fn:length(taskList) > 0}">
		<c:set var="count" value="0" />
		<div class="col_12">
			<c:if test="${day == 'today'}">
				<h6 style="text-align: left; margin-bottom: 10px;">Today's task
					details&nbsp;:</h6>
			</c:if>
			<table class="striped tight sortable">
				<thead>
					<tr>
						<th>#</th>
						<th>Ticket#</th>
						<th>Role</th>
						<th>Plan-Unplanned</th>
						<th>Work Done Yesterday</th>
						<th>Work Done Today</th>
						<th>Impediments</th>
						<th>Comment</th>
						<th>Status</th>
						<th>Plan for Next Day</th>
						<th>Other Work</th>
						<c:if test="${day == 'today'}">
							<th>Action</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${taskList}" var="task">
						<tr>
							<td>${count}</td>
							<td>${task.ticketid}</td>
							<td><c:choose>
									<c:when test="${task.roleid == 1}">None</c:when>
									<c:when test="${task.roleid == 2}">Dev</c:when>
									<c:when test="${task.roleid == 3}">QA</c:when>
									<c:when test="${task.roleid == 4}">UI</c:when>
								</c:choose></td>
							<td>${task.planUnplan}</td>
							<td>${task.workDoneYesterday}</td>
							<td>${task.workDoneToday}</td>

							<c:choose>
								<c:when test="${fn:length(task.impediments) > 0}">
									<td>${task.impediments}</td>
								</c:when>
								<c:otherwise>
									<td style="text-align: center">-</td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${fn:length(task.comment) > 0}">
									<td>${task.comment}</td>
								</c:when>
								<c:otherwise>
									<td style="text-align: center">-</td>
								</c:otherwise>
							</c:choose>

							<td><c:choose>
									<c:when test="${task.statusCode == 'IP'}">In Progress</c:when>
									<c:when test="${task.statusCode == 'WL'}">On hold</c:when>
									<c:when test="${task.statusCode == 'CM'}">Completed</c:when>
								</c:choose></td>

							<td>${task.planForNextDay}</td>

							<c:choose>
								<c:when test="${fn:length(task.otherWork) > 0}">
									<td>${task.otherWork}</td>
								</c:when>
								<c:otherwise>
									<td style="text-align: center">-</td>
								</c:otherwise>
							</c:choose>


							<c:if test="${day == 'today'}">
								<td><a
									href="${pageContext.request.contextPath}/tasks/edit/${task.project_id}/${task.id }"><i
										class="icon-pencil"></i></a> <a
									href="${pageContext.request.contextPath}/tasks/delete/${task.id }"
									class="confirm"><i class="icon-minus-sign"></i></a></td>
							</c:if>


						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
		<div class="col_12">
			${noTaskMsg }.&nbsp;
			<c:if test="${day == 'today'}">
				<a href="${pageContext.request.contextPath}">Create A Task</a>
			</c:if>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript"> 
      $(document).ready( function() {
        $('#deleteNotify').delay(3000).fadeOut();
        $('.confirm').click(function(){
            var answer = confirm("Are you sure you want to delete this task?");
            if (answer){
                return true;
            } else {
                return false;
            }
        });
      });
</script>


<script>
/*  jQuery ready function. Specify a function to execute when the DOM is fully loaded.  
 *  http://basicuse.net/articles/pl/scripting_languages/javascript/jquery_ui_widgets_datepicker_calendar
 */
$(document).ready(
 
  /* This is the function that will get executed after the DOM is fully loaded */
  function () {
	$( "#datepicker" ).datepicker({
	  changeMonth: true,//this option for allowing user to select month
	  changeYear: true, //this option for allowing user to select from year range
	  maxDate: '-1d'
	});
	$("#datepicker").attr('required', '');
  }

);
</script>

<!-- Footer -->
<jsp:include page="footer.jsp" />