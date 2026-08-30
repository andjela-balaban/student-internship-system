<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="companyModal" tabindex="-1" aria-labelledby="companyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow">
            <form action="${pageContext.request.contextPath}/Controller" method="POST" id="companyForm">
                <input type="hidden" name="action" id="modalAction" value="create-company" />
                <input type="hidden" name="companyId" id="modalCompanyId" />

                <div class="modal-header border-bottom-0 pb-0 pt-4 px-4">
                    <h5 class="modal-title fw-bold text-dark" id="companyModalLabel">Company</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                
                <div class="modal-body px-4 py-3">
                    <div class="mb-3">
                        <label class="form-label text-muted small fw-semibold">Company Name</label>
                        <input type="text" class="form-control rounded-3" name="name" id="modalCompanyName" required />
                    </div>
                    <div class="mb-3">
                        <label class="form-label text-muted small fw-semibold">Username</label>
                        <input type="text" class="form-control rounded-3" name="username" id="modalUsername" required />
                    </div>
                    <div class="mb-3">
                        <label class="form-label text-muted small fw-semibold">Email</label>
                        <input type="email" class="form-control rounded-3" name="email" id="modalEmail" required />
                    </div>
                    
                    <div class="mb-3" id="passwordGroup">
                        <label class="form-label text-muted small fw-semibold">Password</label>
                        <input type="password" class="form-control rounded-3" name="password" id="modalPassword" />
                    </div>
                    
                    <div class="mb-3" id="statusGroup">
                        <label class="form-label text-muted small fw-semibold">Status</label>
                        <select class="form-select rounded-3" name="isActive" id="modalIsActive">
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
                        </select>
                    </div>
                </div>
                

			<div class="modal-footer border-top-0 pt-0 pb-4 px-4 d-flex justify-content-end gap-2">
    			<button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
    			<button type="submit" class="btn btn-modal btn-modal-primary" id="modalSubmitBtn">Save</button>
			</div>
            </form>
        </div>
    </div>
</div>