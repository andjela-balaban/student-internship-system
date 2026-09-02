<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Modal za detaljan pregled prakse -->
<div class="modal fade" id="internshipDetailsModal" tabindex="-1" aria-labelledby="internshipDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg" style="border-radius: 16px;">
            <div class="modal-header border-bottom-0 pb-0 px-4 pt-4">
                <div>
                    <h4 class="modal-title fw-bold text-dark mb-1" id="detailsTitle">Internship Name</h4>
                    <span class="badge bg-teal-subtle text-teal fw-semibold" id="detailsCompany" style="background-color: #eaf6f7; color: #257b84; font-size: 0.85rem; padding: 6px 12px; border-radius: 8px;">
                        Company
                    </span>
                </div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <div class="modal-body p-4">
                <!-- Period -->
                <div class="mb-4 p-3 rounded-3" style="background-color: #f8fafc; border: 1px solid #e2e8f0;">
                    <div class="row align-items-center text-center text-sm-start">
                        <div class="col-sm-6 mb-2 mb-sm-0">
                            <small class="text-uppercase text-secondary fw-bold" style="font-size: 0.75rem;">Period</small>
                            <div class="fw-semibold text-dark mt-1 fs-6">
                                <i class="bi bi-calendar-event me-2" style="color: #37A5B0;"></i>
                                <span id="detailsPeriod">01.09.2026 - 01.12.2026</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Description -->
                <div class="mb-4">
                    <h6 class="fw-bold text-dark mb-2">Description</h6>
                    <p class="text-secondary" id="detailsDescription" style="white-space: pre-line; line-height: 1.6;">
                        ...
                    </p>
                </div>

                <!-- Terms & Conditions -->
                <div class="mb-4">
                    <h6 class="fw-bold text-dark mb-2">Terms &amp; Conditions</h6>
                    <p class="text-secondary" id="detailsTerms" style="white-space: pre-line; line-height: 1.6;">
                        ...
                    </p>
                </div>

                <!-- Technologies -->
                <div>
                    <h6 class="fw-bold text-dark mb-2">Technologies</h6>
                    <div id="detailsTechnologies" class="d-flex flex-wrap gap-2 pt-1">
                    </div>
                </div>
            </div>

            <div class="modal-footer border-top-0 pt-0 px-4 pb-4">
                <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
