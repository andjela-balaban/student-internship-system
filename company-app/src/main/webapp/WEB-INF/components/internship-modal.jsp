<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.company_app.model.dto.TechnologyResponseDTO" %>
<%
    List<TechnologyResponseDTO> availableTechnologies = (List<TechnologyResponseDTO>) request.getAttribute("availableTechnologies");
%>
<!-- Modal for creating/editing internships -->
<div class="modal fade" id="internshipModal" tabindex="-1" aria-labelledby="internshipModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg" style="border-radius: 16px;">
            <div class="modal-header border-bottom-0 pb-0 px-4 pt-4">
                <h5 class="modal-title fw-bold" id="internshipModalLabel">New Internship</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="internships.jsp" method="post" id="internshipForm">
                <input type="hidden" name="action" id="modalFormAction" value="create">
                <input type="hidden" name="internshipId" id="modalInternshipId" value="">

                <div class="modal-body p-4">
                    <!-- Internship Name -->
                    <div class="mb-3">
                        <label for="name" class="form-label form-label-sm text-secondary fw-bold">Internship name *</label>
                        <input type="text" class="form-control form-control-custom" id="modalName" name="name" maxlength="50" required placeholder="e.g. Software Engineer Intern">
                    </div>

                    <!-- Description -->
                    <div class="mb-3">
                        <label for="description" class="form-label form-label-sm text-secondary fw-bold">Description *</label>
                        <textarea class="form-control" id="modalDescription" name="description" rows="3" maxlength="500" required placeholder="Short description of tasks and role..." style="border-radius: 10px;"></textarea>
                    </div>

                    <!-- Dates -->
                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label for="startDate" class="form-label form-label-sm text-secondary fw-bold">Start date *</label>
                            <input type="date" class="form-control form-control-custom" id="modalStartDate" name="startDate" required>
                        </div>
                        <div class="col-md-6">
                            <label for="endDate" class="form-label form-label-sm text-secondary fw-bold">End date *</label>
                            <input type="date" class="form-control form-control-custom" id="modalEndDate" name="endDate" required>
                        </div>
                    </div>

                    <!-- Terms & Conditions -->
                    <div class="mb-3">
                        <label for="termsAndConditions" class="form-label form-label-sm text-secondary fw-bold">Terms &amp; Conditions</label>
                        <textarea class="form-control" id="modalTermsAndConditions" name="termsAndConditions" rows="2" maxlength="500" placeholder="e.g. Basic knowledge of Git and Java required..." style="border-radius: 10px;"></textarea>
                    </div>

                    <!-- Technologies -->
                    <div class="mb-3">
                        <label class="form-label form-label-sm text-secondary fw-bold mb-2">Used technologies</label>
                        <div class="p-3 border rounded-3 bg-light" style="max-height: 150px; overflow-y: auto;">
                            <div class="row g-2">
                                <% 
                                    if (availableTechnologies != null && !availableTechnologies.isEmpty()) {
                                        for (TechnologyResponseDTO tech : availableTechnologies) {
                                %>
                                    <div class="col-6 col-sm-4">
                                        <div class="form-check">
                                            <input class="form-check-input tech-checkbox" type="checkbox" name="technologyIds" value="<%= tech.getTechnologyId() %>" id="tech_<%= tech.getTechnologyId() %>">
                                            <label class="form-check-label small" for="tech_<%= tech.getTechnologyId() %>">
                                                <%= tech.getName() %>
                                            </label>
                                        </div>
                                    </div>
                                <% 
                                        }
                                    } else {
                                %>
                                    <div class="col-12 text-muted small">There are no available technologies in the system</div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer border-top-0 pt-0 px-4 pb-4">
                    <button type="button" class="btn btn-modal btn-modal-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-modal btn-modal-primary" id="modalSubmitBtn">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>
