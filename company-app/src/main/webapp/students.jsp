<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.unibl.etf.company_app.model.UserBean" %>
<%@ page import="org.unibl.etf.company_app.model.dto.*" %>
<%@ page import="org.unibl.etf.company_app.service.*" %>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />

<%
    request.setCharacterEncoding("UTF-8");

    if (userBean == null || !userBean.isLoggedIn()) {
        session.setAttribute("notification", "You must be logged in as a company.");
        response.sendRedirect("login.jsp");
        return;
    }

    InternshipService internshipService = new InternshipService();
    ApplicationService applicationService = new ApplicationService();

    List<InternshipResponseDTO> allInternships = internshipService.getAll(userBean.getJwtToken());
    List<InternshipResponseDTO> myInternships = new ArrayList<>();
    if (allInternships != null) {
        for (InternshipResponseDTO item : allInternships) {
            if (item.getCompany() != null) {
                boolean matchId = userBean.getUserId() != null && userBean.getUserId().equals(item.getCompany().getCompanyId());
                boolean matchUsername = userBean.getUsername() != null && userBean.getUsername().equalsIgnoreCase(item.getCompany().getUsername());
                if (matchId || matchUsername) {
                    myInternships.add(item);
                }
            }
        }
    }

    List<ApplicationResponseDTO> companyApplications = new ArrayList<>();
    for (InternshipResponseDTO internship : myInternships) {
        List<ApplicationResponseDTO> apps = applicationService.getAllByInternshipId(internship.getInternshipId(), userBean.getJwtToken());
        if (apps != null && !apps.isEmpty()) {
            companyApplications.addAll(apps);
        }
    }

    String notification = (String) session.getAttribute("notification");
    if (notification != null) {
        session.removeAttribute("notification");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub Company - Students &amp; Applications</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        <jsp:include page="WEB-INF/components/sidebar.jsp">
            <jsp:param name="activePage" value="students" />
        </jsp:include>

        <main class="main-content">
            <% if (notification != null && !notification.trim().isEmpty()) { %>
                <div class="alert alert-info alert-dismissible fade show mb-4" role="alert" style="border-radius: 10px;">
                    <i class="bi bi-info-circle me-2"></i><%= notification %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
                <div>
                    <h2 class="fw-bold text-dark mb-1">Student Applicants</h2>
                    <p class="text-secondary mb-0 small">Review student applications, manage statuses, and grade work journals</p>
                </div>
            </div>

            <div class="card border-0 shadow-sm p-3 mb-4" style="border-radius: 12px;">
                <div class="row align-items-center g-3">
                    <div class="col-12 col-md-6">
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0 text-secondary">
                                <i class="bi bi-search"></i>
                            </span>
                            <input type="text" id="searchInput" class="form-control border-start-0 ps-0" placeholder="Search by student name, index, or internship..." onkeyup="filterTable()">
                        </div>
                    </div>
                    <div class="col-12 col-md-6 text-md-end text-secondary small">
                        Total Applicants: <strong id="applicationCount"><%= companyApplications.size() %></strong>
                    </div>
                </div>
            </div>

            <div class="table-card">
                <div class="table-responsive">
                    <table class="table table-custom align-middle" id="studentsTable">
                        <thead>
                            <tr>
                                <th style="width: 20%;">Student Name</th>
                                <th class="text-center" style="width: 20%;">Index</th>
                                <th style="width: 30%;">Internship</th>
                                <th class="text-center" style="width: 20%;">Status</th>
                                <th class="text-center" style="width: 10%;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (companyApplications.isEmpty()) { %>
                                <tr>
                                    <td colspan="5" class="text-center py-5 text-secondary">
                                        <i class="bi bi-people display-4 d-block mb-3 text-muted"></i>
                                        No student applications found for your company's internships yet.
                                    </td>
                                </tr>
                            <% } else { 
                                for (ApplicationResponseDTO app : companyApplications) {
                                    StudentResponseDTO student = app.getStudent();
                                    InternshipResponseDTO internship = app.getInternship();
                                    String status = app.getStatus() != null ? app.getStatus() : "PENDING";
                            %>
                                <tr>
                                    <td>
                                        <div class="fw-bold text-dark">
                                            <%= student != null ? student.getFirstName() + " " + student.getLastName() : "N/A" %>
                                        </div>
                                        <div class="small text-secondary">
                                            <%= student != null && student.getEmail() != null ? student.getEmail() : "" %>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle">
                                            <%= student != null && student.getStudentIndex() != null ? student.getStudentIndex() : "-" %>
                                        </span>
                                    </td>
                                    <td>
                                        <div class="fw-semibold text-dark">
                                            <%= internship != null ? internship.getName() : "N/A" %>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <% if ("ACCEPTED".equalsIgnoreCase(status)) { %>
                                            <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle px-3 py-2 fw-semibold">
                                                ACCEPTED
                                            </span>
                                        <% } else if ("REJECTED".equalsIgnoreCase(status)) { %>
                                            <span class="badge rounded-pill bg-danger-subtle text-danger border border-danger-subtle px-3 py-2 fw-semibold">
                                                REJECTED
                                            </span>
                                        <% } else { %>
                                            <span class="badge rounded-pill bg-warning-subtle text-warning-emphasis border border-warning-subtle px-3 py-2 fw-semibold">
                                                PENDING
                                            </span>
                                        <% } %>
                                    </td>
                                    <td class="text-center">
                                        <div class="d-inline-flex gap-2 justify-content-center">
                                            <a href="student-details.jsp?internshipId=<%= internship != null ? internship.getInternshipId() : "" %>&applicationId=<%= app.getApplicationId() %>&studentId=<%= student != null ? student.getStudentId() : "" %>" 
                                               class="btn btn-sm action-icon d-inline-flex align-items-center gap-1 fw-semibold text-decoration-none" 
                                               title="View Details">
                                                <i class="bi bi-eye fs-5"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            <% 
                                } 
                            } 
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function filterTable() {
            const input = document.getElementById('searchInput').value.toLowerCase();
            const rows = document.querySelectorAll('#studentsTable tbody tr');
            let count = 0;

            rows.forEach(row => {
                const text = row.innerText.toLowerCase();
                if (text.includes(input)) {
                    row.style.display = '';
                    count++;
                } else {
                    row.style.display = 'none';
                }
            });

            document.getElementById('applicationCount').innerText = count;
        }
    </script>
</body>
</html>
