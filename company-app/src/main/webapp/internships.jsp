<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.unibl.etf.company_app.model.UserBean" %>
<%@ page import="org.unibl.etf.company_app.model.dto.*" %>
<%@ page import="org.unibl.etf.company_app.service.*" %>
<jsp:useBean id="userBean" class="org.unibl.etf.company_app.model.UserBean" scope="session" />

<%
    request.setCharacterEncoding("UTF-8");

    // Session check
    if (userBean == null || !userBean.isLoggedIn()) {
        session.setAttribute("notification", "You must be logged in as a company.");
        response.sendRedirect("login.jsp");
        return;
    }

    InternshipService internshipService = new InternshipService();
    TechnologyService technologyService = new TechnologyService();
    String notification = null;

    // Handle POST actions (Model 1 CRUD)
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {
            try {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                String terms = request.getParameter("termsAndConditions");
                String[] techIdsArr = request.getParameterValues("technologyIds");

                List<Integer> techIds = new ArrayList<>();
                if (techIdsArr != null) {
                    for (String tId : techIdsArr) {
                        techIds.add(Integer.parseInt(tId));
                    }
                }

                InternshipRequestDTO dto = new InternshipRequestDTO();
                dto.setName(name);
                dto.setDescription(description);
                dto.setStartDate(LocalDate.parse(startDateStr));
                dto.setEndDate(LocalDate.parse(endDateStr));
                dto.setTermsAndConditions(terms);
                dto.setCompanyId(userBean.getUserId());
                dto.setTechnologyIds(techIds);

                internshipService.createInternship(dto, userBean.getJwtToken());
                session.setAttribute("notification", "Internship successfully created!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", "Error creating internship: " + e.getMessage());
            }
            response.sendRedirect("internships.jsp");
            return;

        } else if ("update".equalsIgnoreCase(action)) {
            try {
                Integer internshipId = Integer.parseInt(request.getParameter("internshipId"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                String terms = request.getParameter("termsAndConditions");
                String[] techIdsArr = request.getParameterValues("technologyIds");

                List<Integer> techIds = new ArrayList<>();
                if (techIdsArr != null) {
                    for (String tId : techIdsArr) {
                        techIds.add(Integer.parseInt(tId));
                    }
                }

                InternshipRequestDTO dto = new InternshipRequestDTO();
                dto.setName(name);
                dto.setDescription(description);
                dto.setStartDate(LocalDate.parse(startDateStr));
                dto.setEndDate(LocalDate.parse(endDateStr));
                dto.setTermsAndConditions(terms);
                dto.setCompanyId(userBean.getUserId());
                dto.setTechnologyIds(techIds);

                internshipService.updateInternship(internshipId, dto, userBean.getJwtToken());
                session.setAttribute("notification", "Internship successfully updated!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", "Error updating internship: " + e.getMessage());
            }
            response.sendRedirect("internships.jsp");
            return;

        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                Integer internshipId = Integer.parseInt(request.getParameter("internshipId"));
                boolean deleted = internshipService.deleteInternship(internshipId, userBean.getJwtToken());
                if (deleted) {
                    session.setAttribute("notification", "Internship successfully deleted.");
                } else {
                    session.setAttribute("notification", "Error deleting internship.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notification", "Error deleting internship: " + e.getMessage());
            }
            response.sendRedirect("internships.jsp");
            return;
        }
    }

    // Read session notification
    notification = (String) session.getAttribute("notification");
    if (notification != null) {
        session.removeAttribute("notification");
    }

    // GET data retrieval
    List<InternshipResponseDTO> allInternships = internshipService.getAll(userBean.getJwtToken());
    List<InternshipResponseDTO> myInternships = new ArrayList<>();

    if (allInternships != null) {
        for (InternshipResponseDTO item : allInternships) {
            if (item.getCompany() != null && userBean.getUserId().equals(item.getCompany().getCompanyId())) {
                myInternships.add(item);
            }
        }
    }

    List<TechnologyResponseDTO> availableTechnologies = technologyService.getAll(userBean.getJwtToken());
    request.setAttribute("availableTechnologies", availableTechnologies);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub Company - Internships</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        <!-- Sidebar -->
        <jsp:include page="WEB-INF/components/sidebar.jsp">
            <jsp:param name="activePage" value="internships" />
        </jsp:include>

        <main class="main-content">
            <% if (notification != null && !notification.trim().isEmpty()) { %>
                <div class="alert alert-info alert-dismissible fade show mb-4" role="alert" style="border-radius: 10px;">
                    <i class="bi bi-info-circle me-2"></i><%= notification %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
                <div>
                    <h2 class="fw-bold text-dark mb-1">My Internships</h2>
                    <p class="text-secondary mb-0 small">Manage practices published by your company</p>
                </div>
                <div>
                    <button type="button" class="btn btn-teal btn-custom fw-semibold d-inline-flex align-items-center gap-2" onclick="openCreateModal()">
                        <span>Add Internship</span>
                    </button>
                </div>
            </div>

            <div class="card border-0 shadow-sm p-3 mb-4" style="border-radius: 12px;">
                <div class="row align-items-center g-3">
                    <div class="col-12 col-md-6">
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0 text-secondary">
                                <i class="bi bi-search"></i>
                            </span>
                            <input type="text" id="searchInput" class="form-control border-start-0 ps-0" placeholder="Search by name or technologies..." onkeyup="filterTable()">
                        </div>
                    </div>
                    <div class="col-12 col-md-6 text-md-end text-secondary small">
                        Shown: <strong id="internshipCount"><%= myInternships.size() %></strong>
                    </div>
                </div>
            </div>

            <div class="table-card">
                <div class="table-responsive">
                    <table class="table table-custom align-middle" id="internshipsTable">
                        <thead>
                            <tr>
                                <th>Internship Name</th>
                                <th>Period</th>
                                <th>Technologies</th>
                                <th class="text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (myInternships.isEmpty()) { %>
                                <tr>
                                    <td colspan="4" class="text-center py-5 text-secondary">
                                        <i class="bi bi-briefcase display-4 d-block mb-3 text-muted"></i>
                                        You have not created any internship. Click on <strong>"Add Internship"</strong> to create one.
                                    </td>
                                </tr>
                            <% } else { 
                                for (InternshipResponseDTO item : myInternships) { 
                                    StringBuilder techNames = new StringBuilder();
                                    List<Integer> techIdList = new ArrayList<>();
                                    if (item.getTechnologies() != null) {
                                        for (TechnologyResponseDTO tech : item.getTechnologies()) {
                                            if (techNames.length() > 0) techNames.append(", ");
                                            techNames.append(tech.getName());
                                            techIdList.add(tech.getTechnologyId());
                                        }
                                    }
                            %>
                                <tr>
                                    <td>
                                        <div class="fw-bold text-dark"><%= item.getName() %></div>
                                        <div class="small text-secondary text-truncate" style="max-width: 280px;">
                                            <%= item.getDescription() != null ? item.getDescription() : "" %>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="small text-dark fw-semibold">
                                            <%= item.getStartDate() != null ? item.getStartDate() : "-" %> &mdash; <%= item.getEndDate() != null ? item.getEndDate() : "-" %>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="d-flex flex-wrap gap-1">
                                            <% 
                                                if (item.getTechnologies() != null && !item.getTechnologies().isEmpty()) {
                                                    for (TechnologyResponseDTO tech : item.getTechnologies()) { 
                                            %>
                                                <span class="badge" style="background-color: #eaf6f7; color: #257b84; font-weight: 500;">
                                                    <%= tech.getName() %>
                                                </span>
                                            <% 
                                                    }
                                                } else { 
                                            %>
                                                <span class="text-muted small">&mdash;</span>
                                            <% } %>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <div class="d-inline-flex gap-2 justify-content-center">
                                            <!-- View Details -->
                                            <button type="button" class="btn btn-sm action-icon" 
                                                    title="Details"
                                                    onclick="viewDetails('<%= item.getName().replace("'", "\\'") %>', 
                                                                        '<%= item.getCompany() != null ? item.getCompany().getName().replace("'", "\\'") : "" %>', 
                                                                        '<%= item.getStartDate() %>', 
                                                                        '<%= item.getEndDate() %>', 
                                                                        '<%= item.getDescription() != null ? item.getDescription().replace("'", "\\'").replace("\n", "\\n") : "" %>', 
                                                                        '<%= item.getTermsAndConditions() != null ? item.getTermsAndConditions().replace("'", "\\'").replace("\n", "\\n") : "" %>', 
                                                                        '<%= techNames.toString().replace("'", "\\'") %>')">
                                                <i class="bi bi-eye fs-5"></i>
                                            </button>

                                            <!-- Edit -->
                                            <button type="button" class="btn btn-sm action-icon" 
                                                    title="Edit"
                                                    onclick="openEditModal(<%= item.getInternshipId() %>, 
                                                                           '<%= item.getName().replace("'", "\\'") %>', 
                                                                           '<%= item.getDescription() != null ? item.getDescription().replace("'", "\\'").replace("\n", "\\n") : "" %>', 
                                                                           '<%= item.getStartDate() %>', 
                                                                           '<%= item.getEndDate() %>', 
                                                                           '<%= item.getTermsAndConditions() != null ? item.getTermsAndConditions().replace("'", "\\'").replace("\n", "\\n") : "" %>', 
                                                                           [<%= techIdList.toString().replaceAll("[\\[\\]]", "") %>])">
                                                <i class="bi bi-pencil fs-5"></i>
                                            </button>

                                            <!-- Delete -->
                                            <form action="internships.jsp" method="post" class="d-inline" onsubmit="return confirm('Are you sure you want to delete this internship?');">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="internshipId" value="<%= item.getInternshipId() %>">
                                                <button type="submit" class="btn btn-sm action-icon-danger" title="Delete">
                                                    <i class="bi bi-trash fs-5"></i>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            <% 
                                } 
                            } 
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <!-- Modals -->
    <jsp:include page="WEB-INF/components/internship-modal.jsp" />
    <jsp:include page="WEB-INF/components/internship-details-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        const internshipModal = new bootstrap.Modal(document.getElementById('internshipModal'));
        const internshipDetailsModal = new bootstrap.Modal(document.getElementById('internshipDetailsModal'));

        function openCreateModal() {
            document.getElementById('internshipModalLabel').innerText = 'New Internship';
            document.getElementById('modalFormAction').value = 'create';
            document.getElementById('modalInternshipId').value = '';
            document.getElementById('internshipForm').reset();
            
            document.querySelectorAll('.tech-checkbox').forEach(cb => cb.checked = false);
            
            internshipModal.show();
        }

        function openEditModal(id, name, description, startDate, endDate, terms, techIds) {
            document.getElementById('internshipModalLabel').innerText = 'Edit Internship';
            document.getElementById('modalFormAction').value = 'update';
            document.getElementById('modalInternshipId').value = id;
            
            document.getElementById('modalName').value = name;
            document.getElementById('modalDescription').value = description;
            document.getElementById('modalStartDate').value = startDate;
            document.getElementById('modalEndDate').value = endDate;
            document.getElementById('modalTermsAndConditions').value = terms;

            document.querySelectorAll('.tech-checkbox').forEach(cb => {
                cb.checked = techIds.includes(parseInt(cb.value));
            });

            internshipModal.show();
        }

        function viewDetails(name, company, startDate, endDate, description, terms, techNamesStr) {
            document.getElementById('detailsTitle').innerText = name;
            document.getElementById('detailsCompany').innerText = company || 'My Company';
            document.getElementById('detailsPeriod').innerText = (startDate || '-') + '  -  ' + (endDate || '-');
            document.getElementById('detailsDescription').innerText = description || 'No description provided.';
            document.getElementById('detailsTerms').innerText = terms || 'No special conditions provided.';

            const techContainer = document.getElementById('detailsTechnologies');
            techContainer.innerHTML = '';

            if (techNamesStr && techNamesStr.trim().length > 0) {
                const arr = techNamesStr.split(', ');
                arr.forEach(tech => {
                    const span = document.createElement('span');
                    span.className = 'badge';
                    span.style.backgroundColor = '#eaf6f7';
                    span.style.color = '#257b84';
                    span.style.fontSize = '0.85rem';
                    span.style.fontWeight = '500';
                    span.style.padding = '6px 12px';
                    span.style.borderRadius = '8px';
                    span.innerText = tech;
                    techContainer.appendChild(span);
                });
            } else {
                techContainer.innerHTML = '<span class="text-muted small">No technologies listed</span>';
            }

            internshipDetailsModal.show();
        }

        function filterTable() {
            const input = document.getElementById('searchInput').value.toLowerCase();
            const rows = document.querySelectorAll('#internshipsTable tbody tr');
            let count = 0;

            rows.forEach(row => {
                const text = row.innerText.toLowerCase();
                if (text.includes(input)) {
                    row.style.display = '';
                    count++;
                } else {
                    row.style.display = 'none';
                }
            });

            document.getElementById('internshipCount').innerText = count;
        }
    </script>
</body>
</html>
