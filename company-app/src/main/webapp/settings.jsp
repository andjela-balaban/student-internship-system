<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.unibl.etf.company_app.model.UserBean" %>
<%@ page import="org.unibl.etf.company_app.model.dto.ChangePasswordRequestDTO" %>
<%@ page import="org.unibl.etf.company_app.service.UserService" %>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />

<%
    request.setCharacterEncoding("UTF-8");

    // Session check
    if (userBean == null || !userBean.isLoggedIn()) {
        session.setAttribute("notification", "You must be logged in as a company.");
        response.sendRedirect("login.jsp");
        return;
    }

    String notification = null;
    String notificationType = "danger";

    // Handle POST action for changing password (Model 1)
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");

        if (oldPassword == null || oldPassword.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            newPasswordConfirm == null || newPasswordConfirm.trim().isEmpty()) {
            
            session.setAttribute("notification", "All fields are required.");
            session.setAttribute("notificationType", "danger");
        } else if (!newPassword.equals(newPasswordConfirm)) {
            session.setAttribute("notification", "New password and confirm password do not match.");
            session.setAttribute("notificationType", "danger");
        } else if (newPassword.length() < 6) {
            session.setAttribute("notification", "New password must be at least 6 characters long.");
            session.setAttribute("notificationType", "danger");
        } else {
            try {
                UserService userService = new UserService();
                ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO(oldPassword, newPassword, newPasswordConfirm);
                boolean success = userService.changePassword(userBean.getUserId(), dto, userBean.getJwtToken());

                if (success) {
                    session.setAttribute("notification", "Password successfully changed!");
                    session.setAttribute("notificationType", "success");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", e.getMessage() != null ? e.getMessage() : "Error changing password.");
                session.setAttribute("notificationType", "danger");
            }
        }
        response.sendRedirect("settings.jsp");
        return;
    }

    // Read session notification
    notification = (String) session.getAttribute("notification");
    String sessionType = (String) session.getAttribute("notificationType");
    if (sessionType != null) {
        notificationType = sessionType;
        session.removeAttribute("notificationType");
    }
    if (notification != null) {
        session.removeAttribute("notification");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub Company - Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        <jsp:include page="WEB-INF/components/sidebar.jsp">
            <jsp:param name="activePage" value="settings" />
        </jsp:include>

        <main class="main-content">
            <% if (notification != null && !notification.trim().isEmpty()) { %>
                <div class="alert alert-<%= notificationType %> alert-dismissible fade show mb-4" role="alert" style="border-radius: 10px;">
                    <i class="bi bi-<%= "success".equals(notificationType) ? "check-circle" : "exclamation-triangle" %> me-2"></i><%= notification %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <div class="mb-4">
                <h2 class="fw-bold text-dark mb-1">Account Settings</h2>
                <p class="text-secondary mb-0 small">Manage your company account security and credentials</p>
            </div>

            <div class="row">
                <div class="col-12 col-lg-8 col-xl-6">
                    <div class="card border-0 shadow-sm p-4 p-md-5" style="border-radius: 16px;">
                        <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                            <div class="d-flex align-items-center justify-content-center rounded-circle" style="width: 48px; height: 48px; background-color: #eaf6f7; color: #37A5B0;">
                                <i class="bi bi-shield-lock fs-4"></i>
                            </div>
                            <div>
                                <h5 class="fw-bold text-dark mb-0">Change Password</h5>
                                <small class="text-secondary">Update your access password below</small>
                            </div>
                        </div>

                        <form action="settings.jsp" method="post" id="settingsForm">
                            <!-- Current Password -->
                            <div class="mb-3">
                                <label for="oldPassword" class="form-label form-label-sm text-secondary fw-bold">Current Password *</label>
                                <div class="input-group">
                                    <input type="password" class="form-control form-control-custom border-end-0" id="oldPassword" name="oldPassword" required>
                                    <button class="btn btn-outline-secondary border-start-0 bg-white text-secondary pe-3" type="button" onclick="togglePass('oldPassword', 'toggleIconOld')">
                                        <i class="bi bi-eye" id="toggleIconOld"></i>
                                    </button>
                                </div>
                            </div>

                            <!-- New Password -->
                            <div class="mb-3">
                                <label for="newPassword" class="form-label form-label-sm text-secondary fw-bold">New Password *</label>
                                <div class="input-group">
                                    <input type="password" class="form-control form-control-custom border-end-0" id="newPassword" name="newPassword" minlength="6" required>
                                    <button class="btn btn-outline-secondary border-start-0 bg-white text-secondary pe-3" type="button" onclick="togglePass('newPassword', 'toggleIconNew')">
                                        <i class="bi bi-eye" id="toggleIconNew"></i>
                                    </button>
                                </div>
                                <div class="form-text small">Minimum 6 characters long</div>
                            </div>

                            <!-- Confirm New Password -->
                            <div class="mb-4">
                                <label for="newPasswordConfirm" class="form-label form-label-sm text-secondary fw-bold">Confirm New Password *</label>
                                <div class="input-group">
                                    <input type="password" class="form-control form-control-custom border-end-0" id="newPasswordConfirm" name="newPasswordConfirm" minlength="6" required>
                                    <button class="btn btn-outline-secondary border-start-0 bg-white text-secondary pe-3" type="button" onclick="togglePass('newPasswordConfirm', 'toggleIconConfirm')">
                                        <i class="bi bi-eye" id="toggleIconConfirm"></i>
                                    </button>
                                </div>
                            </div>

                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-teal btn-custom fw-semibold px-4">
                                    Update Password
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function togglePass(inputId, iconId) {
            const input = document.getElementById(inputId);
            const icon = document.getElementById(iconId);
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        }
    </script>
</body>
</html>
