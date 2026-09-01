<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub - Companies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        
        <jsp:include page="../components/sidebar.jsp">
            <jsp:param name="activePage" value="companies" />
        </jsp:include>

        <main class="main-content">
            
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="fw-bold m-0 text-dark">Companies</h2>
                <button type="button" 
                        id="btnCreateCompany"
                        class="btn btn-navy btn-custom px-4 d-inline-flex align-items-center gap-2 fw-semibold"
                        data-bs-toggle="modal" 
                        data-bs-target="#companyModal">
                    Add Company
                </button>
            </div>


            <div class="table-card">
                <div class="table-responsive">
                    <table class="table table-custom align-middle">
                        <thead>
                            <tr>
                                <th>Company</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Status</th>
                                <th class="text-center" style="width: 100px;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="company" items="${companies}">
                                <tr>
                                    <td><c:out value="${company.name}" /></td>
                                    <td><c:out value="${company.username}" /></td>
                                    <td><c:out value="${company.email}" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${company.isActive}">
                                                <span class="badge bg-success">active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <button type="button" 
                                                class="btn btn-link p-0 action-icon edit-company-btn border-0 bg-transparent" 
                                                data-bs-toggle="modal" 
                                                data-bs-target="#companyModal"
                                                data-id="${company.companyId}"
                                                data-name="${company.name}"
                                                data-username="${company.username}"
                                                data-email="${company.email}"
                                                data-active="${company.isActive}"
                                                title="Edit Status">
                                            <i class="bi bi-pencil"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty companies}">
                                <tr>
                                    <td colspan="5" class="text-center text-muted py-4">
                                        No companies found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </main>
    </div>

    <jsp:include page="../components/company-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const btnCreate = document.getElementById('btnCreateCompany');
            const editButtons = document.querySelectorAll('.edit-company-btn');
            
            const modalTitle = document.getElementById('companyModalLabel');
            const modalAction = document.getElementById('modalAction');
            const modalSubmitBtn = document.getElementById('modalSubmitBtn');
            
            const fieldId = document.getElementById('modalCompanyId');
            const fieldName = document.getElementById('modalCompanyName');
            const fieldUsername = document.getElementById('modalUsername');
            const fieldEmail = document.getElementById('modalEmail');
            const fieldPassword = document.getElementById('modalPassword');
            
            const passwordGroup = document.getElementById('passwordGroup');
            const statusGroup = document.getElementById('statusGroup');
            const fieldIsActive = document.getElementById('modalIsActive');

            // NATIVE / CREATE MODE
            btnCreate.addEventListener('click', function () {
                modalTitle.textContent = 'Create New Company';
                modalAction.value = 'create-company';
                modalSubmitBtn.textContent = 'Create';
                
                fieldId.value = '';
                fieldName.value = '';
                fieldUsername.value = '';
                fieldEmail.value = '';
                fieldPassword.value = '';

                fieldName.readOnly = false;
                fieldUsername.readOnly = false;
                fieldEmail.readOnly = false;

                passwordGroup.style.display = 'block';
                fieldPassword.required = true;
                statusGroup.style.display = 'none';
            });

            // EDIT STATUS MODE
            editButtons.forEach(button => {
                button.addEventListener('click', function () {
                    modalTitle.textContent = 'Edit Company Status';
                    modalAction.value = 'update-status';
                    modalSubmitBtn.textContent = 'Save Changes';
                    
                    fieldId.value = this.getAttribute('data-id');
                    fieldName.value = this.getAttribute('data-name');
                    fieldUsername.value = this.getAttribute('data-username');
                    fieldEmail.value = this.getAttribute('data-email');
                    
                    fieldName.readOnly = true;
                    fieldUsername.readOnly = true;
                    fieldEmail.readOnly = true;

                    passwordGroup.style.display = 'none';
                    fieldPassword.required = false;
                    statusGroup.style.display = 'block';
                    
                    const isActive = this.getAttribute('data-active');
                    fieldIsActive.value = isActive;
                });
            });
        });
    </script>
</body>
</html>



