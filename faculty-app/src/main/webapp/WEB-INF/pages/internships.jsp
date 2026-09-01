<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InternHub - Internships</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="d-flex dashboard-container">
        
        <jsp:include page="../components/sidebar.jsp">
            <jsp:param name="activePage" value="internships" />
        </jsp:include>

        <main class="main-content">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="fw-bold m-0 text-dark">Internships</h2>
            </div>

            <div class="table-card">
                <div class="table-responsive">
                    <table class="table table-custom align-middle">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Company</th>
                                <th>Start date</th>
                                <th>End date</th>
                                <th>Description</th>
                                <th class="text-center" style="width: 100px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="internship" items="${internships}">
                                <c:set var="techNamesList" value="" />
                                <c:forEach var="tech" items="${internship.technologies}" varStatus="tStatus">
                                    <c:set var="techNamesList" value="${techNamesList}${tech.name}${not tStatus.last ? ',' : ''}" />
                                </c:forEach>

                                <tr>
                                    <td class="fw-semibold"><c:out value="${internship.name}" /></td>
                                    <td><c:out value="${internship.company != null ? internship.company.name : '-'}" /></td>
                                    <td class="text-nowrap"><c:out value="${internship.startDate}" /></td>
                                    <td class="text-nowrap"><c:out value="${internship.endDate}" /></td>
                                    <td class="text-truncate" style="max-width: 250px;"><c:out value="${internship.description}" /></td>
                                    <td class="text-center">
                                        <button type="button" 
                                                class="btn btn-link p-0 action-icon view-internship-btn border-0 bg-transparent" 
                                                data-bs-toggle="modal" 
                                                data-bs-target="#internshipDetailsModal"
                                                data-name="${internship.name}"
                                                data-company="${internship.company != null ? internship.company.name : '-'}"
                                                data-start="${internship.startDate}"
                                                data-end="${internship.endDate}"
                                                data-description="${internship.description}"
                                                data-terms="${internship.termsAndConditions}"
                                                data-technologies="${techNamesList}"
                                                title="View Details">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty internships}">
                                <tr>
                                    <td colspan="6" class="text-center text-muted py-4">
                                        No internships found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </main>
    </div>

    <jsp:include page="../components/internship-modal.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>