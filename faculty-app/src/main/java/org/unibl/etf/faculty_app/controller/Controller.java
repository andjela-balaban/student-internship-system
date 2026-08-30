package org.unibl.etf.faculty_app.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.faculty_app.model.UserBean;
import org.unibl.etf.faculty_app.service.UserService;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	@Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
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
                    
                    // Preusmjeravanje na pocetnu stranicu nakon prijave
                    address = "/WEB-INF/pages/companies.jsp";
                } else {
                    session.setAttribute("notification", "Incorrect username or password!");
                    address = "/WEB-INF/pages/login.jsp";
                }
                break;

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
