<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal fade" id="companyLogReviewModal" tabindex="-1" aria-labelledby="companyLogReviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg" style="border-radius: 16px;">
            <div class="modal-header border-bottom-0 pb-0 px-4 pt-4">
                <h5 class="modal-title fw-bold" id="companyLogReviewModalLabel">Company Feedback / Review</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="student-details.jsp" method="post" id="companyLogReviewForm">
                <input type="hidden" name="action" value="review-log">
                <input type="hidden" name="logId" id="reviewLogId" value="">
                <input type="hidden" name="internshipId" id="reviewInternshipId" value="">
                <input type="hidden" name="applicationId" id="reviewApplicationId" value="">
                <input type="hidden" name="studentId" id="reviewStudentId" value="">

                <div class="modal-body p-4">
                    <div class="mb-3">
                        <label for="companyCommentInput" class="form-label form-label-sm text-secondary fw-bold">Company Comment *</label>
                        <textarea class="form-control" id="companyCommentInput" name="companyComment" rows="4" maxlength="500" required placeholder="Enter feedback or comments on student's internship progress..." style="border-radius: 10px;"></textarea>
                    </div>
                </div>

                <div class="modal-footer border-top-0 pt-0 px-4 pb-4">
                    <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-modal btn-modal-primary">Save Comment</button>
                </div>
            </form>
        </div>
    </div>
</div>
