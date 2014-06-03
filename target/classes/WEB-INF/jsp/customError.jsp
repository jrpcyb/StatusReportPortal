<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Error";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>
<!-- Content -->

<div class="col_12">
<p style="font-size:34px;text-align:center;line-height:1.1;color:#C33A2C;font-weight:500;margin:5%;">
Something Went Wrong
</p>
</div>
<div class="col_12">
<i class="icon-warning-sign icon-4x" style="color:#DD4D4D; font-size:9em;"></i>
</div>
<div class="col_12" style="margin:3% 0% 3% 1%;">
<a class="button blue large" href="${pageContext.request.contextPath}/">Back to&nbsp;<i class="icon-home icon-2x"></i></a>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp" />