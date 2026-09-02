<%@ page language="java" contentType="application/pdf" pageEncoding="UTF-8"%>
<%@ page import="org.unibl.etf.company_app.model.UserBean" %>
<%@ page import="org.unibl.etf.company_app.service.StudentService" %>
<%@ page import="java.io.OutputStream" %>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />
<%
    if (userBean == null || !userBean.isLoggedIn()) {
        response.sendRedirect("login.jsp");
        return;
    }

    String studentIdStr = request.getParameter("studentId");
    if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
        try {
            Integer studentId = Integer.parseInt(studentIdStr);
            StudentService studentService = new StudentService();
            byte[] pdfBytes = studentService.getCvPdfBytes(studentId, userBean.getJwtToken());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"CV_Student_" + studentId + ".pdf\"");
            response.setContentLength(pdfBytes.length);

            out.clear();
            out = pageContext.pushBody();
            OutputStream os = response.getOutputStream();
            os.write(pdfBytes);
            os.flush();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV PDF not found or error generating PDF.");
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Student ID is required.");
    }
%>
