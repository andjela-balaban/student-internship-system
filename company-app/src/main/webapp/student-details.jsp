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

    StudentService studentService = new StudentService();
    ApplicationService applicationService = new ApplicationService();
    InternshipLogService logService = new InternshipLogService();

    String studentIdStr = request.getParameter("studentId");
    String internshipIdStr = request.getParameter("internshipId");
    String applicationIdStr = request.getParameter("applicationId");

    Integer studentId = null;
    Integer internshipId = null;
    Integer applicationId = null;

    try {
        if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
            studentId = Integer.parseInt(studentIdStr.trim());
        }
    } catch (Exception ignored) {}

    try {
        if (internshipIdStr != null && !internshipIdStr.trim().isEmpty()) {
            internshipId = Integer.parseInt(internshipIdStr.trim());
        }
    } catch (Exception ignored) {}

    try {
        if (applicationIdStr != null && !applicationIdStr.trim().isEmpty()) {
            applicationId = Integer.parseInt(applicationIdStr.trim());
        }
    } catch (Exception ignored) {}

    // Handle POST actions
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");

        if ("update-status".equalsIgnoreCase(action)) {
            String newStatus = request.getParameter("status");
            try {
                if (internshipId != null && applicationId != null && newStatus != null) {
                    applicationService.updateApplicationStatus(internshipId, applicationId, newStatus, userBean.getJwtToken());
                    session.setAttribute("notification", "Application status updated to " + newStatus + "!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", "Error updating status: " + e.getMessage());
            }
            response.sendRedirect("student-details.jsp?studentId=" + (studentId != null ? studentId : "") + "&internshipId=" + (internshipId != null ? internshipId : "") + "&applicationId=" + (applicationId != null ? applicationId : ""));
            return;

        } else if ("review-log".equalsIgnoreCase(action)) {
            try {
                String logIdParam = request.getParameter("logId");
                if (logIdParam != null && !logIdParam.trim().isEmpty()) {
                    Integer logId = Integer.parseInt(logIdParam.trim());
                    String companyComment = request.getParameter("companyComment");
                    logService.reviewLogByCompany(logId, companyComment, userBean.getJwtToken());
                    session.setAttribute("notification", "Company comment saved successfully!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", "Error saving comment: " + e.getMessage());
            }
            response.sendRedirect("student-details.jsp?studentId=" + (studentId != null ? studentId : "") + "&internshipId=" + (internshipId != null ? internshipId : "") + "&applicationId=" + (applicationId != null ? applicationId : ""));
            return;
        }
    }

    String notification = (String) session.getAttribute("notification");
    if (notification != null) {
        session.removeAttribute("notification");
    }

    StudentResponseDTO student = null;
    if (studentId != null) {
        try {
            student = studentService.getStudentById(studentId, userBean.getJwtToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ApplicationResponseDTO applicationDTO = null;
    if (internshipId != null && applicationId != null) {
        try {
            applicationDTO = applicationService.getByInternshipIdAndApplicationId(internshipId, applicationId, userBean.getJwtToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CvResponseDTO cv = null;
    if (studentId != null) {
        try {
            cv = studentService.getCvByStudentId(studentId, userBean.getJwtToken());
        } catch (Exception e) {
            cv = null;
        }
    }

    List<InternshipLogResponseDTO> logs = new ArrayList<>();
    if (studentId != null) {
        try {
            List<InternshipLogResponseDTO> allStudentLogs = logService.getAllLogsForStudent(studentId, userBean.getJwtToken());
            if (allStudentLogs != null) {
                for (InternshipLogResponseDTO logItem : allStudentLogs) {
                    if (logItem.getInternship() != null) {
                        boolean matchInternship = (internshipId != null && internshipId.equals(logItem.getInternship().getInternshipId()));
                        boolean matchCompany = (logItem.getInternship().getCompany() != null && 
                            ((userBean.getUserId() != null && userBean.getUserId().equals(logItem.getInternship().getCompany().getCompanyId())) || 
                             (userBean.getUsername() != null && userBean.getUsername().equalsIgnoreCase(logItem.getInternship().getCompany().getUsername()))));
                        
                        if (matchInternship || matchCompany) {
                            logs.add(logItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub Company - Student Details</title>
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
            <div class="mb-3">
                <a href="students.jsp" class="text-decoration-none text-muted normal fw-semibold">
                    <i class="bi bi-arrow-left"></i> Back to Applicants
                </a>
            </div>

            <% if (notification != null && !notification.trim().isEmpty()) { %>
                <div class="alert alert-info alert-dismissible fade show mb-4" role="alert" style="border-radius: 10px;">
                    <i class="bi bi-info-circle me-2"></i><%= notification %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <% if (student != null) { %>
                <div class="table-card p-4 mb-4">
                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-start gap-3">
                        <div>
                            <h3 class="fw-bold text-dark m-0"><%= student.getFirstName() %> <%= student.getLastName() %></h3>
                            <div class="d-flex align-items-center gap-2 mt-2">
                                <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle">
                                    Index: <%= student.getStudentIndex() != null ? student.getStudentIndex() : "-" %>
                                </span>

                                <% if (applicationDTO != null) { 
                                    String appStatus = applicationDTO.getStatus() != null ? applicationDTO.getStatus() : "PENDING";
                                    if ("ACCEPTED".equalsIgnoreCase(appStatus)) { %>
                                        <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle px-3 py-1 fw-semibold">
                                            ACCEPTED
                                        </span>
                                    <% } else if ("REJECTED".equalsIgnoreCase(appStatus)) { %>
                                        <span class="badge rounded-pill bg-danger-subtle text-danger border border-danger-subtle px-3 py-1 fw-semibold">
                                            REJECTED
                                        </span>
                                    <% } else { %>
                                        <span class="badge rounded-pill bg-warning-subtle text-warning-emphasis border border-warning-subtle px-3 py-1 fw-semibold">
                                            PENDING
                                        </span>
                                    <% }
                                } %>
                            </div>
                        </div>

                        <div class="d-flex flex-wrap align-items-center gap-2">
                            <% if (cv != null) { %>
                                <a href="download-cv.jsp?studentId=<%= student.getStudentId() %>" 
                                   target="_blank" 
                                   class="btn btn-sm rounded-3 px-3 fw-semibold btn-outline-teal d-inline-flex align-items-center gap-2 text-decoration-none">
                                    <i class="bi bi-file-earmark-pdf fs-5"></i>
                                    <span>Download CV (PDF)</span>
                                </a>
                            <% } %>

                            <% if (applicationDTO != null && internshipId != null && applicationId != null) { 
                                String appStatus = applicationDTO.getStatus() != null ? applicationDTO.getStatus() : "PENDING";
                                if ("PENDING".equalsIgnoreCase(appStatus)) {
                            %>
                                <form action="student-details.jsp?studentId=<%= studentId %>&internshipId=<%= internshipId %>&applicationId=<%= applicationId %>" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="update-status">
                                    <input type="hidden" name="status" value="ACCEPTED">
                                    <button type="submit" class="btn btn-sm btn-success rounded-3 px-3 fw-semibold d-inline-flex align-items-center gap-1">
                                        <i class="bi bi-check-lg fs-5"></i> Accept
                                    </button>
                                </form>
                                <form action="student-details.jsp?studentId=<%= studentId %>&internshipId=<%= internshipId %>&applicationId=<%= applicationId %>" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="update-status">
                                    <input type="hidden" name="status" value="REJECTED">
                                    <button type="submit" class="btn btn-sm btn-danger rounded-3 px-3 fw-semibold d-inline-flex align-items-center gap-1">
                                        <i class="bi bi-x-lg fs-5"></i> Reject
                                    </button>
                                </form>
                            <% 
                                } 
                            } 
                            %>
                        </div>
                    </div>

                    <hr class="my-3 text-muted opacity-25" />

                    <div class="row g-3">
                        <div class="col-md-3">
                            <span class="text-muted small d-block fw-semibold">Email</span>
                            <span class="text-dark fw-medium"><%= student.getEmail() != null ? student.getEmail() : "-" %></span>
                        </div>
                        <div class="col-md-3">
                            <span class="text-muted small d-block fw-semibold">Username</span>
                            <span class="text-dark fw-medium"><%= student.getUsername() != null ? student.getUsername() : "-" %></span>
                        </div>
                        <div class="col-md-3">
                            <span class="text-muted small d-block fw-semibold">Date of Birth</span>
                            <span class="text-dark fw-medium"><%= student.getDateOfBirth() != null ? student.getDateOfBirth() : "-" %></span>
                        </div>
                        <div class="col-md-3">
                            <span class="text-muted small d-block fw-semibold">Phone</span>
                            <span class="text-dark fw-medium"><%= student.getPhoneNumber() != null ? student.getPhoneNumber() : "-" %></span>
                        </div>
                    </div>
                </div>

                <%-- <!-- Student CV Details Section -->
                <% 
                    boolean hasCvData = cv != null && (
                        (cv.getInterests() != null && !cv.getInterests().trim().isEmpty()) || 
                        (cv.getAddress() != null && !cv.getAddress().trim().isEmpty()) || 
                        (cv.getTechnologies() != null && !cv.getTechnologies().isEmpty())
                    );
                    if (hasCvData) { 
                %>
                    <div class="table-card p-4 mb-4">
                        <h4 class="fw-bold text-dark mb-3">Student Curriculum Vitae (CV)</h4>
                        <div class="row g-3">
                            <div class="col-md-6">
                                <span class="text-muted small d-block fw-semibold mb-1">Interests / Biography</span>
                                <div class="p-3 bg-light rounded-3 text-dark small" style="white-space: pre-line;"><%= cv.getInterests() != null ? cv.getInterests().trim() : "No interests specified." %></div>
                            </div>
                            <div class="col-md-6">
                                <span class="text-muted small d-block fw-semibold mb-1">Address</span>
                                <div class="p-3 bg-light rounded-3 text-dark small">
                                    <%= cv.getAddress() != null ? cv.getAddress() : "No address specified." %>
                                </div>
                            </div>
                        </div>

                        <% if (cv.getTechnologies() != null && !cv.getTechnologies().isEmpty()) { %>
                            <div class="mt-3">
                                <span class="text-muted small d-block fw-semibold mb-2">Technologies & Skills</span>
                                <div class="d-flex flex-wrap gap-2">
                                    <% for (TechnologyResponseDTO tech : cv.getTechnologies()) { %>
                                        <span class="badge" style="background-color: #eaf6f7; color: #257b84; font-weight: 500; font-size: 0.85rem; padding: 6px 12px; border-radius: 8px;">
                                            <%= tech.getName() %>
                                        </span>
                                    <% } %>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="table-card p-4 mb-4 text-center text-muted">
                        <i class="bi bi-file-earmark-person display-4 d-block mb-2 text-muted opacity-50"></i>
                        <span>Student has not created or uploaded a CV yet.</span>
                    </div>
                <% } %> --%>
                <!-- Prikaz CV kartice ili obavjestenja -->
                <% if (cv != null) { %>
                    <div class="table-card p-4 mb-4">
                        <h4 class="fw-bold text-dark mb-3">Student Curriculum Vitae (CV)</h4>
                        <div class="row g-3">
                            <div class="col-md-6">
                                <span class="text-muted small d-block fw-semibold mb-1">Interests / Biography</span>
                                <div class="p-3 bg-light rounded-3 text-dark small" style="white-space: pre-line;"><%= cv.getInterests() != null ? cv.getInterests().trim() : "No interests specified." %></div>
                            </div>
                            <div class="col-md-6">
                                <span class="text-muted small d-block fw-semibold mb-1">Address</span>
                                <div class="p-3 bg-light rounded-3 text-dark small">
                                    <%= cv.getAddress() != null ? cv.getAddress() : "No address specified." %>
                                </div>
                            </div>
                        </div>

                        <% if (cv.getTechnologies() != null && !cv.getTechnologies().isEmpty()) { %>
                            <div class="mt-3">
                                <span class="text-muted small d-block fw-semibold mb-2">Technologies &amp; Skills</span>
                                <div class="d-flex flex-wrap gap-2">
                                    <% for (TechnologyResponseDTO tech : cv.getTechnologies()) { %>
                                        <span class="badge" style="background-color: #eaf6f7; color: #257b84; font-weight: 500; font-size: 0.85rem; padding: 6px 12px; border-radius: 8px;">
                                            <%= tech.getName() %>
                                        </span>
                                    <% } %>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="alert alert-warning border-0 shadow-sm rounded-3 p-3 mb-4 d-flex align-items-center gap-2" role="alert">
                        <i class="bi bi-exclamation-triangle-fill fs-5 text-warning"></i>
                        <span>This student has not submitted or created a CV yet.</span>
                    </div>
                <% } %>

                <!-- Section Title -->
                <h4 class="fw-bold text-dark mb-3">Internship Logs &amp; Work Journal</h4>

                <!-- Internship Logs List -->
                <div class="d-flex flex-column gap-3 mb-5">
                    <% if (logs != null && !logs.isEmpty()) { 
                        int count = 1;
                        for (InternshipLogResponseDTO logItem : logs) { 
                    %>
                        <div class="table-card p-4">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <div class="d-flex align-items-center gap-3">
                                    <h5 class="fw-bold text-dark m-0">
                                        Log #<%= count++ %> &mdash; <%= logItem.getInternship() != null ? logItem.getInternship().getName() : "Internship" %>
                                    </h5>
                                    
                                    <% if (logItem.getGrade() != null) { %>
                                        <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle px-3 py-2 fw-semibold">
                                            Grade: <%= logItem.getGrade() %>
                                        </span>
                                    <% } else { %>
                                        <span class="badge rounded-pill bg-warning-subtle text-warning-emphasis border border-warning-subtle px-3 py-2 fw-semibold">
                                            Not Graded Yet
                                        </span>
                                    <% } %>
                                </div>

                                <% if (logItem.getCompanyComment() == null) { %>
                                    <button type="button" 
                                            class="btn btn-teal btn-sm rounded-3 px-3 fw-semibold btn-review-company-log"
                                            onclick="openCompanyReviewModal(<%= logItem.getLogId() %>, '<%= logItem.getCompanyComment() != null ? logItem.getCompanyComment().replace("'", "\\'").replace("\n", "\\n") : "" %>')">
                                        <i class="bi bi-chat-left-text me-1"></i> Comment / Review
                                    </button>
                                <% } %>
                            </div>

                            <!-- Student Log Content -->
                            <div class="mb-3">
                                <label class="form-label text-muted small fw-semibold mb-2">Student Log Content</label>
                                <% if (logItem.getContent() != null && !logItem.getContent().trim().isEmpty()) { 
                                    String[] entries = logItem.getContent().split("\n");
                                %>
                                    <div class="d-flex flex-column gap-2">
                                        <% for (String entry : entries) { 
                                            if (entry != null && !entry.trim().isEmpty()) { 
                                        %>
                                            <div class="p-3 bg-light rounded-3 text-dark fs-6">
                                                <%= entry.trim() %>
                                            </div>
                                        <% 
                                            } 
                                        } %>
                                    </div>
                                <% } else { %>
                                    <div class="p-3 bg-light rounded-3 text-muted fs-6">
                                        No content entered yet by student.
                                    </div>
                                <% } %>
                            </div>

                            <!-- Comments -->
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label text-muted small fw-semibold">Company Comment</label>
                                    <div class="p-3 rounded-3 text-dark small" style="background-color: #eaf6f7; border: 1px solid #b8e4e8;">
                                        <%= logItem.getCompanyComment() != null && !logItem.getCompanyComment().trim().isEmpty() ? logItem.getCompanyComment() : "No company comment added yet." %>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label text-muted small fw-semibold">Faculty Comment</label>
                                    <div class="p-3 bg-light rounded-3 text-dark small">
                                        <%= logItem.getFacultyComment() != null && !logItem.getFacultyComment().trim().isEmpty() ? logItem.getFacultyComment() : "No faculty comment added yet." %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <% 
                        } 
                    } else { 
                    %>
                        <div class="table-card p-4 text-center text-muted">
                            No internship work logs found for this student at your company.
                        </div>
                    <% } %>
                </div>

            <% } else { %>
                <div class="alert alert-warning" role="alert">
                    Student profile not found.
                </div>
            <% } %>
        </main>
    </div>

    <!-- Company Log Review Modal -->
    <jsp:include page="WEB-INF/components/company-log-review-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const companyLogReviewModal = new bootstrap.Modal(document.getElementById('companyLogReviewModal'));

        function openCompanyReviewModal(logId, currentComment) {
            document.getElementById('reviewLogId').value = logId;
            document.getElementById('reviewInternshipId').value = '<%= internshipId != null ? internshipId : "" %>';
            document.getElementById('reviewApplicationId').value = '<%= applicationId != null ? applicationId : "" %>';
            document.getElementById('reviewStudentId').value = '<%= studentId != null ? studentId : "" %>';
            document.getElementById('companyCommentInput').value = currentComment;

            companyLogReviewModal.show();
        }
    </script>
</body>
</html>