package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UreservationBean;
import model.UserDao;

/**
 * Servlet implementation class LoginCon
 */
public class LoginCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");



		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		UserDao userDao = new UserDao();


		HttpSession session = request.getSession();
		UreservationBean userBean = userDao.login(userID, password);

		if(userBean == null) {
			request.setAttribute("message", 0);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}else {
			int role = userBean.getRole();
			int money = userBean.getMoney();
			System.out.println(money);
			String userName = userBean.getUserName();
			session.setAttribute("user", userBean);
			session.setAttribute("userID", userID);
			session.setAttribute("role", role);
			session.setAttribute("userName", userName);
			session.setAttribute("money", money);

			if(role == 0) {
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/Register.jsp");
			dispatcher.forward(request, response);

			}else if(role == 1) {
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserListCon");
			dispatcher.forward(request, response);
			}
		}
	}

	}


