<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String activePage = request.getParameter("activePage");
    if (activePage == null) activePage = "";
%>
<aside class="sidebar">
    <div>
        <!-- Logo -->
        <div class="text-center mb-4">
            <img src="${pageContext.request.contextPath}/assets/img/logo2.png" 
                 alt="InternHub Logo" 
                 class="img-fluid sidebar-logo">
        </div>

        <!-- Navigacija za kompanije -->
        <nav class="nav flex-column">
            <a class="nav-link-custom <%= "internships".equals(activePage) ? "active" : "" %>" 
               href="${pageContext.request.contextPath}/internships.jsp">
                <i class="bi bi-briefcase fs-5"></i>
                <span>Internships</span>
            </a>
            
            <a class="nav-link-custom <%= "students".equals(activePage) ? "active" : "" %>" 
               href="${pageContext.request.contextPath}/students.jsp">
                <i class="bi bi-people fs-5"></i>
                <span>Students</span>
            </a>
            
            <a class="nav-link-custom <%= "settings".equals(activePage) ? "active" : "" %>" 
               href="${pageContext.request.contextPath}/settings.jsp">
                <i class="bi bi-gear fs-5"></i>
                <span>Settings</span>
            </a>
        </nav>
    </div>

    <!-- Log out dugme -->
    <div>
        <form action="${pageContext.request.contextPath}/logout.jsp" method="post">
            <button type="submit" class="btn btn-teal btn-custom w-100 d-flex align-items-center justify-content-center gap-2 fw-semibold">
                <i class="bi bi-box-arrow-right fs-5"></i>
                <span>Log out</span>
            </button>
        </form>
    </div>
</aside>
