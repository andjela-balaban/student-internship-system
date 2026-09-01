<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="logReviewModal" tabindex="-1" aria-labelledby="logReviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow">
            <form action="${pageContext.request.contextPath}/Controller" method="POST">
                <input type="hidden" name="action" value="review-log" />
                <input type="hidden" name="logId" id="reviewLogId" />
                <input type="hidden" name="studentId" id="reviewStudentId" />

                <div class="modal-header border-bottom-0 pb-0 pt-4 px-4">
                    <h5 class="modal-title fw-bold text-dark" id="logReviewModalLabel">Review Internship Log</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                
                <div class="modal-body px-4 py-3">
                    <div class="mb-3">
                        <label class="form-label text-muted small fw-semibold">Grade (5 - 10)</label>
                        <select class="form-select rounded-3" name="grade" id="reviewGrade" required>
                            <option value="">Select grade...</option>
                            <option value="5">5 (Fail)</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label text-muted small fw-semibold">Faculty Comment</label>
                        <textarea class="form-control rounded-3" name="facultyComment" id="reviewFacultyComment" rows="4" placeholder="Enter comments for student work..."></textarea>
                    </div>
                </div>
                
                <div class="modal-footer border-top-0 pt-0 pb-4 px-4 d-flex justify-content-end gap-2">
                    <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-modal btn-modal-primary">Submit Review</button>
                </div>
            </form>
        </div>
    </div>
</div>