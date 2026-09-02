<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />
<%
    if (userBean != null && userBean.isLoggedIn()) {
        response.sendRedirect("internships.jsp");
    } else {
        response.sendRedirect("login.jsp");
    }
%>
