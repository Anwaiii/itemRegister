package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ItemBean;
import model.UreservationBean;
import model.UserDao;

/**
 * Servlet implementation class ChargeCon
 */
public class ChargeCon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int message = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChargeCon() {
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
			HttpSession sessionCheck = request.getSession(false);
			if (sessionCheck == null) {
				response.sendRedirect("Login.jsp");
				return;
			}

			Integer balance = 0;
			ArrayList<ItemBean> shoppingList = (ArrayList<ItemBean>) sessionCheck.getAttribute("cart");

			//	チャージ金額が選ばれたとき、その金額をbalanceに代入する
			if(!request.getParameter("balance").equals("")) {
			 balance = Integer.parseInt(request.getParameter("balance"));

			}else {		//	チャージが失敗した場合
				request.setAttribute("message", -1);	//	"チャージ金額を入力してください"とのメッセージが表示される
				ServletContext app =this.getServletContext();
				RequestDispatcher dispatcher =  app.getRequestDispatcher("/Charge.jsp");
				dispatcher.forward(request, response);
			}


			//	チャージが成功した場合

				UserDao userDao = new UserDao();
				UreservationBean userBean = new UreservationBean();

				userBean = (UreservationBean)sessionCheck.getAttribute("user");
				userDao.userCharge(userBean, balance);
				userBean = userDao.login(userBean.getUserID(),userBean.getPassword());

				sessionCheck.setAttribute("user", userBean);
//				request.setAttribute("message", message);
//				request.setAttribute("cart", shoppingList);
//				ServletContext app =this.getServletContext();
//				RequestDispatcher dispatcher =  app.getRequestDispatcher("/Cart.jsp");
//				dispatcher.forward(request, response);
				response.sendRedirect("./CartCon");
			}
	}


