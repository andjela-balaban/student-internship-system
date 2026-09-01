<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub - Students</title>
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
            
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="fw-bold m-0 text-dark">Students</h2>
                
                <div class="dropdown">
                    <button class="btn btn-navy btn-custom px-4 dropdown-toggle fw-semibold d-inline-flex align-items-center gap-2" 
                            type="button" 
                            id="createStudentDropdown" 
                            data-bs-toggle="dropdown" 
                            aria-expanded="false">
                        Add Students
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end custom-dropdown-menu shadow-sm border-0" aria-labelledby="createStudentDropdown">
                        <li>
                            <a class="dropdown-item d-flex align-items-center gap-2" href="#" id="btnCreateStudent" data-bs-toggle="modal" data-bs-target="#studentModal">
                                <i class="bi bi-person-plus fs-6"></i> Single Student
                            </a>
                        </li>
                        <li><hr class="dropdown-divider my-1"></li>
                        <li>
                            <a class="dropdown-item d-flex align-items-center gap-2" href="#" data-bs-toggle="modal" data-bs-target="#csvStudentModal">
                                <i class="bi bi-file-earmark-spreadsheet fs-6"></i> Import CSV
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="table-card">
                <div class="table-responsive">
                    <table class="table table-custom align-middle">
                        <thead>
                            <tr>
                                <th>Index</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th class="text-center" style="width: 120px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}">
                                <tr>
                                    <td><c:out value="${student.studentIndex}" /></td>
                                    <td><c:out value="${student.firstName} ${student.lastName}" /></td>
                                    <td><c:out value="${student.email}" /></td>
                                    <td><c:out value="${student.phoneNumber != null ? student.phoneNumber : '-'}" /></td>
                                    <td class="text-center">
                                        <div class="d-flex justify-content-center gap-4">
                                            <a href="Controller?action=student-details&id=${student.studentId}" 
                                               class="action-icon" title="View Details">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <a href="Controller?action=delete-student&id=${student.studentId}" 
                                               class="action-icon text-danger" 
                                               onclick="return confirm('Are you sure you want to delete student `${student.firstName} ${student.lastName}`?');"
                                               title="Delete Student">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty students}">
                                <tr>
                                    <td colspan="5" class="text-center text-muted py-4">
                                        No students found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </main>
    </div>

    <!-- Modali za kreiranje i CSV upload -->
    <jsp:include page="../components/add-student-modal.jsp" />
    <jsp:include page="../components/csv-student-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const dropzone = document.getElementById('csvDropzone');
            const fileInput = document.getElementById('csvFileInput');
            const uploadBtn = document.getElementById('btnUploadSubmit');
            const initialState = document.getElementById('uploadInitialState');
            const fileNameDisplay = document.getElementById('fileNameDisplay');
            const selectedFileName = document.getElementById('selectedFileName');
            const removeBtn = document.getElementById('btnRemoveFile');
            const csvModal = document.getElementById('csvStudentModal');
            const modalDialog = csvModal ? csvModal.querySelector('.modal-dialog') : null;

            if (!dropzone || !fileInput) return;

            // Drag & Drop efekti
            ['dragenter', 'dragover'].forEach(eventName => {
                dropzone.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    if (!dropzone.classList.contains('has-file')) {
                        dropzone.classList.add('drag-over');
                    }
                });
            });

            ['dragleave', 'drop'].forEach(eventName => {
                dropzone.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    dropzone.classList.remove('drag-over');
                });
            });

            dropzone.addEventListener('drop', (e) => {
                if (dropzone.classList.contains('has-file')) return;
                const files = e.dataTransfer.files;
                if (files.length > 0) {
                    fileInput.files = files;
                    handleFileSelection(files[0]);
                }
            });

            fileInput.addEventListener('change', function () {
                if (this.files.length > 0) {
                    handleFileSelection(this.files[0]);
                }
            });

            // Uklanjanje fajla
            if (removeBtn) {
                removeBtn.addEventListener('click', function (e) {
                    e.stopPropagation();
                    clearFileSelection();
                });
            }

            if (csvModal) {
                csvModal.addEventListener('hidden.bs.modal', function () {
                    clearFileSelection();
                });
            }

            function handleFileSelection(file) {
                if (file.name.endsWith('.csv')) {
                    selectedFileName.textContent = file.name;
                    
                    dropzone.classList.add('has-file');
                    initialState.classList.add('d-none');
                    fileNameDisplay.classList.remove('d-none');
                    fileNameDisplay.classList.add('d-flex');
                    
                    uploadBtn.disabled = false;

                    if (modalDialog) {
                        modalDialog.classList.remove('modal-lg');
                    }
                } else {
                    alert('Please select a valid CSV file.');
                    clearFileSelection();
                }
            }

            function clearFileSelection() {
                fileInput.value = '';
                selectedFileName.textContent = '';
                
                dropzone.classList.remove('has-file');
                initialState.classList.remove('d-none');
                fileNameDisplay.classList.add('d-none');
                fileNameDisplay.classList.remove('d-flex');
                
                uploadBtn.disabled = true;

                if (modalDialog) {
                    modalDialog.classList.add('modal-lg');
                }
            }
        });
    </script>
</body>
</html>