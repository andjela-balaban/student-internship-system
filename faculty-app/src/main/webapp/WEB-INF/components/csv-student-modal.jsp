<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="csvStudentModal" tabindex="-1" aria-labelledby="csvStudentModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content rounded-4 border-0 shadow">
            
            <form action="${pageContext.request.contextPath}/Controller" method="POST" enctype="multipart/form-data" id="csvUploadForm">
                <input type="hidden" name="action" value="import-students" />
                <input type="hidden" name="facultyId" value="1" />

                <div class="modal-header border-bottom-0 pb-0 pt-4 px-4">
                    <h5 class="modal-title fw-bold text-dark" id="csvStudentModalLabel">Import Students CSV</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body px-4 py-3">
                    <div class="upload-dropzone d-flex flex-column align-items-center justify-content-center rounded-4" id="csvDropzone">
                        
                        <div id="uploadInitialState" class="d-flex flex-column align-items-center justify-content-center">
                            <i class="bi bi-file-earmark-arrow-up text-muted mb-2" style="font-size: 42px;"></i>
                            <span class="text-secondary fw-medium fs-6">Drag file in here</span>
                            <span class="text-muted medium my-1">or</span>
                            
                            <label for="csvFileInput" class="btn btn-navy px-4 py-2 mt-1 rounded-3 fw-semibold" style="cursor: pointer;">
                                Choose file
                            </label>
                        </div>
                        
                        <input type="file" id="csvFileInput" name="file" accept=".csv" class="d-none" required />
                        
                        <div id="fileNameDisplay" class="file-preview-card d-none align-items-center justify-content-between w-100">
                            <div class="d-flex align-items-center text-truncate me-2">
                                <i class="bi bi-file-earmark-excel-fill text-success fs-4 me-3"></i>
                                <span id="selectedFileName" class="fw-semibold text-dark text-truncate"></span>
                            </div>
                            <button type="button" id="btnRemoveFile" class="btn-remove-file" title="Remove file">
                                <i class="bi bi-x-lg"></i>
                            </button>
                        </div>

                    </div>
                </div>

                <div class="modal-footer border-top-0 pt-0 pb-4 px-4 d-flex justify-content-end gap-2">
                    <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-modal btn-modal-primary" id="btnUploadSubmit" disabled>Upload</button>
                </div>
            </form>

        </div>
    </div>
</div>