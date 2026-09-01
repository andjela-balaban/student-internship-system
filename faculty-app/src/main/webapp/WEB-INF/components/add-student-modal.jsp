<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="studentModal" tabindex="-1" aria-labelledby="studentModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content rounded-4 border-0 shadow">
            <form action="${pageContext.request.contextPath}/Controller" method="POST" id="studentForm">
                <input type="hidden" name="action" id="modalStudentAction" value="create-student" />
                <input type="hidden" name="studentId" id="modalStudentId" />
                <input type="hidden" name="facultyId" id="modalFacultyId" value="1" />

                <div class="modal-header border-bottom-0 pb-0 pt-4 px-4">
                    <h5 class="modal-title fw-bold text-dark" id="studentModalLabel">Create New Student</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                
                <div class="modal-body px-4 py-3">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">First Name</label>
                            <input type="text" class="form-control rounded-3" name="firstName" id="modalFirstName" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Last Name</label>
                            <input type="text" class="form-control rounded-3" name="lastName" id="modalLastName" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Student Index</label>
                            <input type="text" class="form-control rounded-3" name="studentIndex" id="modalStudentIndex" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Email</label>
                            <input type="email" class="form-control rounded-3" name="email" id="modalEmail" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Username</label>
                            <input type="text" class="form-control rounded-3" name="username" id="modalUsername" required />
                        </div>
                        <div class="col-md-6" id="studentPasswordGroup">
                            <label class="form-label text-muted small fw-semibold">Password</label>
                            <input type="password" class="form-control rounded-3" name="password" id="modalPassword" />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Date of Birth</label>
                            <input type="date" class="form-control rounded-3" name="dateOfBirth" id="modalDateOfBirth" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-semibold">Phone Number</label>
                            <input type="text" class="form-control rounded-3" name="phoneNumber" id="modalPhoneNumber" />
                        </div>
                    </div>
                </div>
                
                <div class="modal-footer border-top-0 pt-0 pb-4 px-4 d-flex justify-content-end gap-2">
                    <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-modal btn-modal-primary" id="modalStudentSubmitBtn">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>