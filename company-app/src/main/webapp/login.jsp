<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.unibl.etf.company_app.model.UserBean" %>
<%@ page import="org.unibl.etf.company_app.service.UserService" %>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />

<%
    request.setCharacterEncoding("UTF-8");

    if (userBean != null && userBean.isLoggedIn()) {
        response.sendRedirect("internships.jsp");
        return;
    }

    String errorNotification = null;
    String usernameInput = request.getParameter("username");

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String passwordInput = request.getParameter("password");

        if (usernameInput != null && !usernameInput.trim().isEmpty() &&
            passwordInput != null && !passwordInput.trim().isEmpty()) {

            try {
                UserService userService = new UserService();
                UserBean authenticatedUser = userService.login(usernameInput.trim(), passwordInput);

                if (authenticatedUser != null && authenticatedUser.isLoggedIn()) {
                    session.setAttribute("userBean", authenticatedUser);
                    session.removeAttribute("notification");
                    response.sendRedirect("internships.jsp");
                    return;
                } else {
                    errorNotification = "Incorrect username or password, or unauthorized account!";
                }
            } catch (Exception e) {
                errorNotification = e.getMessage() != null ? e.getMessage() : "Login failed. Please try again.";
            }
        } else {
            errorNotification = "Please enter both username and password.";
        }
    }

    if (errorNotification == null) {
        String sessionNotification = (String) session.getAttribute("notification");
        if (sessionNotification != null && !sessionNotification.trim().isEmpty()) {
            errorNotification = sessionNotification;
            session.removeAttribute("notification");
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub - Company Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="bg-white d-flex align-items-center min-vh-100 py-4">
    <div class="container">
        <div class="card login-card p-4 p-md-5 mx-auto">
            <div class="row align-items-center g-4 w-100 mx-0">
                
                <!-- Left Side: Logo -->
                <div class="col-12 col-md-6 text-center border-md-end pe-md-5">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.png" 
                         alt="InternHub Logo" 
                         class="img-fluid logo-img">
                </div>

                <!-- Right Side: Login Form -->
                <div class="col-12 col-md-6 ps-md-5">
                    <h3 class="fw-bold mb-4 text-center text-md-start login-title">Welcome back!</h3>

                    <form action="login.jsp" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label form-label-sm text-secondary fw-bold">Username</label>
                            <input type="text" class="form-control form-control-custom" id="username" name="username" 
                                   value="<%= usernameInput != null ? usernameInput : "" %>" required>
                        </div>
                        
                        <div class="mb-4">
                            <label for="password" class="form-label form-label-sm text-secondary fw-bold">Password</label>
                            <div class="input-group">
                                <input type="password" class="form-control form-control-custom border-end-0" id="password" name="password" required>
                                <button class="btn btn-outline-secondary border-start-0 bg-white text-secondary pe-3" type="button" id="togglePassword">
                                    <i class="bi bi-eye" id="toggleIcon"></i>
                                </button>
                            </div>
                        </div>
                        
                        <!-- Error Notification -->
                        <% if (errorNotification != null && !errorNotification.trim().isEmpty()) { %>
                            <div class="alert alert-danger py-2 small mb-3" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i><%= errorNotification %>
                            </div>
                        <% } %>

                        <button type="submit" class="btn btn-navy btn-custom w-100 fw-semibold">Login</button>
                    </form>
                </div>

            </div>
        </div>
    </div>

    <script>
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#password');
        const toggleIcon = document.querySelector('#toggleIcon');

        togglePassword.addEventListener('click', function () {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            
            toggleIcon.classList.toggle('bi-eye');
            toggleIcon.classList.toggle('bi-eye-slash');
        });
    </script>
</body>
</html>
