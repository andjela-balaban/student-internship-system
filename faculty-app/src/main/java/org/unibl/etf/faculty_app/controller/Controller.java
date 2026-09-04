package org.unibl.etf.faculty_app.controller;

import java.io.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import org.unibl.etf.faculty_app.model.*;
import org.unibl.etf.faculty_app.model.dto.*;
import org.unibl.etf.faculty_app.service.*;
import javax.servlet.annotation.MultipartConfig;

/**
 * Servlet implementation class Controller
 */
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private CompanyService companyService;
	private InternshipService internshipService;
	private StudentService studentService;
	private InternshipLogService logService;
	
	@Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
        this.companyService = new CompanyService();
        this.internshipService = new InternshipService();
        this.studentService = new StudentService();
        this.logService = new InternshipLogService();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String address = "/WEB-INF/pages/login.jsp";

        HttpSession session = request.getSession();
        
        CompanyService companyService = new CompanyService();

        if (action == null || action.trim().isEmpty()) {
            action = "login_page";
        }

        switch (action) {
            case "login_page":
                address = "/WEB-INF/pages/login.jsp";
                break;

            case "login":
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                UserBean userBean = userService.login(username, password);

                if (userBean != null) {
                    session.setAttribute("userBean", userBean);
                    session.removeAttribute("notification");
                    
                    response.sendRedirect("Controller?action=companies");
                    return;
                } else {
                    session.setAttribute("notification", "Incorrect username or password!");
                    address = "/WEB-INF/pages/login.jsp";
                }
                break;
                
            case "companies":
                UserBean loggedUser = (UserBean) session.getAttribute("userBean");

                // Provjera da li je korisnik prijavljen i posjeduje JWT token
                if (loggedUser != null && loggedUser.getJwtToken() != null) {
                    try {
                        List<CompanyResponseDTO> companies = companyService.getAll(loggedUser.getJwtToken());
                        request.setAttribute("companies", companies);
                        address = "/WEB-INF/pages/companies.jsp";
//                        System.out.println("=========================");
//                        System.out.println("JWT: " + loggedUser.getJwtToken());
//                        System.out.println("=========================");
                    } catch (Exception e) {
//                    	System.err.println(">>> GRESKA U CASE COMPANIES:");
                        e.printStackTrace();
                        session.setAttribute("notification", "Error fetching companies: " + e.getMessage());
                        address = "/WEB-INF/pages/companies.jsp";
                    }
                } else {
//                	System.out.println(">>> KORISNIK ILI TOKEN SU NULL!");
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }
                break;
                
            case "update-status":
                UserBean userForUpdate = (UserBean) session.getAttribute("userBean");

                if (userForUpdate != null && userForUpdate.getJwtToken() != null) {
                    try {
                        Integer id = Integer.parseInt(request.getParameter("companyId"));
                        Boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

                        CompanyStatusUpdateDTO statusDto = new CompanyStatusUpdateDTO();
                        statusDto.setIsActive(isActive);

                        companyService.updateStatus(id, statusDto, userForUpdate.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error updating status: " + e.getMessage());
                    }
                }
                
                response.sendRedirect("Controller?action=companies");
                return;
                
            case "create-company":
                UserBean userForCreate = (UserBean) session.getAttribute("userBean");

                if (userForCreate != null && userForCreate.getJwtToken() != null) {
                    try {
                        String companyName = request.getParameter("name");
                        String companyUsername = request.getParameter("username");
                        String companyEmail = request.getParameter("email");
                        String companyPassword = request.getParameter("password");

                        CompanyRequestDTO createCompanyDto = new CompanyRequestDTO();
                        createCompanyDto.setName(companyName);
                        createCompanyDto.setUsername(companyUsername);
                        createCompanyDto.setEmail(companyEmail);
                        createCompanyDto.setPassword(companyPassword);

                        companyService.createCompany(createCompanyDto, userForCreate.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error creating company: " + e.getMessage());
                    }
                } else {
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }

                response.sendRedirect("Controller?action=companies");
                return;

            case "internships":
                UserBean userForInternships = (UserBean) session.getAttribute("userBean");

                if (userForInternships != null && userForInternships.getJwtToken() != null) {
                    try {
                        List<InternshipResponseDTO> internships = internshipService.getAll(userForInternships.getJwtToken());
                        request.setAttribute("internships", internships);
                        address = "/WEB-INF/pages/internships.jsp";
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error fetching internships: " + e.getMessage());
                        address = "/WEB-INF/pages/internships.jsp";
                    }
                } else {
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }
                break;
                
            case "students":
                UserBean userForStudents = (UserBean) session.getAttribute("userBean");
                if (userForStudents != null && userForStudents.getJwtToken() != null) {
                    try {
                        List<StudentResponseDTO> students = studentService.getAll(userForStudents.getJwtToken());
                        request.setAttribute("students", students);
                        address = "/WEB-INF/pages/students.jsp";
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error fetching students: " + e.getMessage());
                        address = "/WEB-INF/pages/students.jsp";
                    }
                } else {
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }
                break;

            case "create-student":
                UserBean userForCreateStudent = (UserBean) session.getAttribute("userBean");
                if (userForCreateStudent != null && userForCreateStudent.getJwtToken() != null) {
                    try {
                        StudentRequestDTO dto = new StudentRequestDTO();
                        dto.setFirstName(request.getParameter("firstName"));
                        dto.setLastName(request.getParameter("lastName"));
                        dto.setStudentIndex(request.getParameter("studentIndex"));
                        dto.setEmail(request.getParameter("email"));
                        dto.setUsername(request.getParameter("username"));
                        dto.setPassword(request.getParameter("password"));
                        dto.setDateOfBirth(java.time.LocalDate.parse(request.getParameter("dateOfBirth")));
                        dto.setPhoneNumber(request.getParameter("phoneNumber"));
                        dto.setFacultyId(Integer.parseInt(request.getParameter("facultyId")));

                        studentService.createStudent(dto, userForCreateStudent.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error creating student: " + e.getMessage());
                    }
                }
                response.sendRedirect("Controller?action=students");
                return;

            case "update-student":
                UserBean userForUpdateStudent = (UserBean) session.getAttribute("userBean");
                Integer updatedStudentId = null;
                if (userForUpdateStudent != null && userForUpdateStudent.getJwtToken() != null) {
                    try {
                        updatedStudentId = Integer.parseInt(request.getParameter("studentId"));
                        StudentRequestDTO dto = new StudentRequestDTO();
                        dto.setFirstName(request.getParameter("firstName"));
                        dto.setLastName(request.getParameter("lastName"));
                        dto.setStudentIndex(request.getParameter("studentIndex"));
                        dto.setEmail(request.getParameter("email"));
                        dto.setUsername(request.getParameter("username"));
                        dto.setPassword(request.getParameter("password"));
                        dto.setDateOfBirth(java.time.LocalDate.parse(request.getParameter("dateOfBirth")));
                        dto.setPhoneNumber(request.getParameter("phoneNumber"));
                        dto.setFacultyId(Integer.parseInt(request.getParameter("facultyId")));

                        studentService.updateStudent(updatedStudentId, dto, userForUpdateStudent.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error updating student: " + e.getMessage());
                    }
                }
                if (updatedStudentId != null) {
                    response.sendRedirect("Controller?action=student-details&id=" + updatedStudentId);
                } else {
                    response.sendRedirect("Controller?action=students");
                }
                return;

            case "delete-student":
                UserBean userForDeleteStudent = (UserBean) session.getAttribute("userBean");
                if (userForDeleteStudent != null && userForDeleteStudent.getJwtToken() != null) {
                    try {
                        Integer studentId = Integer.parseInt(request.getParameter("id"));
                        studentService.deleteStudent(studentId, userForDeleteStudent.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error deleting student: " + e.getMessage());
                    }
                }
                response.sendRedirect("Controller?action=students");
                return;

            case "student-details":
                UserBean userForStudentDetails = (UserBean) session.getAttribute("userBean");
                if (userForStudentDetails != null && userForStudentDetails.getJwtToken() != null) {
                    try {
                        Integer studentId = Integer.parseInt(request.getParameter("id"));
                        StudentResponseDTO student = studentService.getById(studentId, userForStudentDetails.getJwtToken());
                        List<InternshipLogResponseDTO> logs = logService.getLogsForStudent(studentId, userForStudentDetails.getJwtToken());

                        request.setAttribute("student", student);
                        request.setAttribute("logs", logs);
                        address = "/WEB-INF/pages/student-details.jsp";
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error fetching student details: " + e.getMessage());
                        address = "/WEB-INF/pages/students.jsp";
                    }
                } else {
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }
                break;

            case "review-log":
                UserBean userForReview = (UserBean) session.getAttribute("userBean");
                String returnStudentId = request.getParameter("studentId");
                if (userForReview != null && userForReview.getJwtToken() != null) {
                    try {
                        Integer logId = Integer.parseInt(request.getParameter("logId"));
                        Integer grade = Integer.parseInt(request.getParameter("grade"));
                        String facultyComment = request.getParameter("facultyComment");

                        FacultyReviewLogDTO reviewDto = new FacultyReviewLogDTO(grade, facultyComment);
                        logService.reviewLogByFaculty(logId, reviewDto, userForReview.getJwtToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error reviewing log: " + e.getMessage());
                    }
                }
                response.sendRedirect("Controller?action=student-details&id=" + returnStudentId);
                return;
                
            case "import-students":
                UserBean userForImport = (UserBean) session.getAttribute("userBean");
                if (userForImport != null && userForImport.getJwtToken() != null) {
                    try {
                        Part filePart = request.getPart("file");
                        Integer facultyId = Integer.parseInt(request.getParameter("facultyId"));

                        if (filePart != null && filePart.getSize() > 0) {
                            String fileName = filePart.getSubmittedFileName();
                            try (InputStream inputStream = filePart.getInputStream()) {
                                studentService.importCsv(inputStream, fileName, facultyId, userForImport.getJwtToken());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("notification", "Error uploading CSV file: " + e.getMessage());
                    }
                } else {
                    response.sendRedirect("Controller?action=login_page");
                    return;
                }
                response.sendRedirect("Controller?action=students");
                return;
                
            case "logout":
                session.invalidate();
                response.sendRedirect("Controller?action=login_page");
                return;

            default:
                address = "/WEB-INF/pages/login.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

}
