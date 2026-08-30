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

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private CompanyService companyService;
	
	@Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
        this.companyService = new CompanyService();
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
