<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub - Student Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        
        <jsp:include page="../components/sidebar.jsp">
            <jsp:param name="activePage" value="students" />
        </jsp:include>

        <main class="main-content">
            
            <div class="mb-3">
                <a href="Controller?action=students" class="text-decoration-none text-muted normal fw-semibold">
                    <i class="bi bi-arrow-left"></i> Back to Students
                </a>
            </div>

            <!-- Student Profile Header Card -->
            <div class="table-card p-4 mb-4">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h3 class="fw-bold text-dark m-0"><c:out value="${student.firstName} ${student.lastName}" /></h3>
                        <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle mt-2">
                            Index: <c:out value="${student.studentIndex}" />
                        </span>
                    </div>
                    <button type="button" 
                            id="btnEditStudent"
                            class="btn btn-outline-primary btn-sm rounded-3 px-3 fw-semibold"
                            data-bs-toggle="modal" 
                            data-bs-target="#studentModal"
                            data-id="${student.studentId}"
                            data-firstname="${student.firstName}"
                            data-lastname="${student.lastName}"
                            data-index="${student.studentIndex}"
                            data-email="${student.email}"
                            data-username="${student.username}"
                            data-dob="${student.dateOfBirth}"
                            data-phone="${student.phoneNumber}"
                            data-facultyid="${student.faculty != null ? student.faculty.facultyId : 1}">
                        <i class="bi bi-pencil me-1"></i> Edit Profile
                    </button>
                </div>

                <hr class="my-3 text-muted opacity-25" />

                <div class="row g-3">
                    <div class="col-md-3">
                        <span class="text-muted small d-block fw-semibold">Email</span>
                        <span class="text-dark fw-medium"><c:out value="${student.email}" /></span>
                    </div>
                    <div class="col-md-3">
                        <span class="text-muted small d-block fw-semibold">Username</span>
                        <span class="text-dark fw-medium"><c:out value="${student.username}" /></span>
                    </div>
                    <div class="col-md-3">
                        <span class="text-muted small d-block fw-semibold">Date of Birth</span>
                        <span class="text-dark fw-medium"><c:out value="${student.dateOfBirth}" /></span>
                    </div>
                    <div class="col-md-3">
                        <span class="text-muted small d-block fw-semibold">Phone</span>
                        <span class="text-dark fw-medium"><c:out value="${student.phoneNumber != null ? student.phoneNumber : '-'}" /></span>
                    </div>
                </div>
            </div>

            <!-- Internship Logs Section -->
            <h4 class="fw-bold text-dark mb-3">Internship Logs &amp; Work Journal</h4>

            <div class="d-flex flex-column gap-3">
                <c:forEach var="log" items="${logs}" varStatus="status">
                    <div class="table-card p-4">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="fw-bold text-dark m-0">
                                Log #${status.count} &mdash; <c:out value="${log.internship != null ? log.internship.name : 'Internship'}" />
                            </h5>
                            <div>
                                <c:choose>
                                    <c:when test="${log.grade != null}">
                                        <span class="badge bg-success fs-6">Grade: <c:out value="${log.grade}" /></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-warning text-dark fs-6">Not Graded</span>
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" 
                                        class="btn btn-navy btn-sm rounded-3 ms-2 px-3 btn-review-log"
                                        data-bs-toggle="modal" 
                                        data-bs-target="#logReviewModal"
                                        data-logid="${log.logId}"
                                        data-grade="${log.grade != null ? log.grade : ''}"
                                        data-comment="${log.facultyComment != null ? log.facultyComment : ''}">
                                    Review / Grade
                                </button>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label text-muted small fw-semibold">Student Log Content</label>
                            <div class="p-3 bg-light rounded-3 text-dark fs-6">
                                <c:out value="${log.content != null ? log.content : 'No content entered yet.'}" />
                            </div>
                        </div>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label text-muted small fw-semibold">Company Comment</label>
                                <div class="p-3 bg-light rounded-3 text-dark small">
                                    <c:out value="${log.companyComment != null ? log.companyComment : '-'}" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label text-muted small fw-semibold">Faculty Comment</label>
                                <div class="p-3 bg-light rounded-3 text-dark small">
                                    <c:out value="${log.facultyComment != null ? log.facultyComment : '-'}" />
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty logs}">
                    <div class="table-card p-4 text-center text-muted">
                        No internship logs found for this student.
                    </div>
                </c:if>
            </div>

        </main>
    </div>

    <!-- Modali za Edit i Review -->
    <jsp:include page="../components/add-student-modal.jsp" />
    <jsp:include page="../components/log-review-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Setup Edit Student Modal
            const btnEdit = document.getElementById('btnEditStudent');
            if (btnEdit) {
                btnEdit.addEventListener('click', function () {
                    document.getElementById('studentModalLabel').textContent = 'Edit Student Profile';
                    document.getElementById('modalStudentAction').value = 'update-student';
                    document.getElementById('modalStudentSubmitBtn').textContent = 'Save Changes';
                    
                    document.getElementById('modalStudentId').value = this.getAttribute('data-id');
                    document.getElementById('modalFirstName').value = this.getAttribute('data-firstname');
                    document.getElementById('modalLastName').value = this.getAttribute('data-lastname');
                    document.getElementById('modalStudentIndex').value = this.getAttribute('data-index');
                    document.getElementById('modalEmail').value = this.getAttribute('data-email');
                    document.getElementById('modalUsername').value = this.getAttribute('data-username');
                    document.getElementById('modalDateOfBirth').value = this.getAttribute('data-dob');
                    document.getElementById('modalPhoneNumber').value = this.getAttribute('data-phone');
                    document.getElementById('modalFacultyId').value = this.getAttribute('data-facultyid');

                    const passwordGroup = document.getElementById('studentPasswordGroup');
                    passwordGroup.style.display = 'none';
                    document.getElementById('modalPassword').required = false;
                });
            }

            // Setup Review Log Modal
            const reviewButtons = document.querySelectorAll('.btn-review-log');
            reviewButtons.forEach(button => {
                button.addEventListener('click', function () {
                    document.getElementById('reviewLogId').value = this.getAttribute('data-logid');
                    document.getElementById('reviewStudentId').value = '${student.studentId}';
                    document.getElementById('reviewGrade').value = this.getAttribute('data-grade');
                    document.getElementById('reviewFacultyComment').value = this.getAttribute('data-comment');
                });
            });
        });
    </script>
</body>
</html>