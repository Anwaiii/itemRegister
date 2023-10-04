package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDao;

/**
 * Servlet implementation class SignUp
 */
public class SignUpCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpCon() {
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
		String passwordConfirm = request.getParameter("passwordConfirm");
		String userName = request.getParameter("userName");
		UserDao userDao = new UserDao();

		String isExistedID = "";
		int num = 0;

		//	パスワードが一致しているかを確認し、一致したらuserIDが既に存在しているを確認する
		if(!password.equals(passwordConfirm)) {
			//	パスワードが一致していない
			num=-1;
		}else {
			isExistedID = userDao.checkUserID(userID);
			if(isExistedID.equals(userID)) {
				//	userIDが既に存在している
				num=-2;
			}
		}


		if(num == 0) {
			num = userDao.newUserRegister(userID,userName,password);
		}

		if(num <= 0) {
			//	num<=0の場合はSignUp.jspに戻り、エラーメッセージが表示される
			request.setAttribute("signUpResult",num);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/SignUp.jsp");
			dispatcher.forward(request, response);
		}else {
			//	新規登録が成功、Loginページに戻る
			request.setAttribute("signUpResult",num);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
//			response.sendRedirect("Login.jsp");
		}

	}

}
