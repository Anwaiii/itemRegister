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
import model.StoreDao;
import model.UreservationBean;

/**
 * Servlet implementation class BuyCon
 */
public class BuyCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuyCon() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int itemNo = Integer.parseInt(request.getParameter("itemNo"));
		StoreDao storeDao = new StoreDao();
		ItemBean item = storeDao.selectItemDao(itemNo);

		request.setAttribute("Item",item);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserBuy.jsp");
		dispatcher.forward(request, response);
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

		int itemNo = Integer.parseInt(request.getParameter("itemNo"));
		int stockCount = Integer.parseInt(request.getParameter("stockCount"));
		int buyAmount = Integer.parseInt(request.getParameter("buyAmount"));
		int num = 0;
		int result = 0;
		UreservationBean userBean = (UreservationBean) sessionCheck.getAttribute("user");

		if(userBean.getRole() == 0) {
			num = -3;	//	管理者アカウントは注文できないです
		}

		if(buyAmount > stockCount) {
			num = -1;	//	✖在庫不足です。注文数を入れ直してください。✖
		}else if(buyAmount <= 0) {
			num = -2; //	✖注文数に0以下は入力できません。入れ直してください。✖
		}

		//	注文失敗。doGet()を呼び出し、もとの商品画面にもどる
		if(num !=0 ) {
			request.setAttribute("message",num);
			doGet(request, response);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserBuy.jsp");
			dispatcher.forward(request, response);
		}

		//	注文数が問題なかった場合
		StoreDao storeDao = new StoreDao();
		ItemBean itemBean = storeDao.selectItemDao(itemNo);
		itemBean.setBuyAmount(buyAmount);
		ArrayList<ItemBean> shoppingList = (ArrayList<ItemBean>) sessionCheck.getAttribute("shoppingList");

		// shoppingListが新しく作られた場合、1つ目の商品を追加する
		if(shoppingList == null) {
			System.out.println("nulldesususu");
			shoppingList = new ArrayList<>();
			shoppingList.add(itemBean);
		}else {

			//	2つ目以降の商品をチェックし、同じ商品がもうカートにあれば、注文数を更新する
			for(int i=0;i<shoppingList.size();i++) {
				if(itemBean.getItemNo() == shoppingList.get(i).getItemNo()) {
					shoppingList.get(i).setBuyAmount(buyAmount);
					result = 1;
					break;
				}
			}

			//	カートに同じ商品がないと、カートに追加する
			if(result == 0) {
				shoppingList.add(itemBean);
			}
		}
		sessionCheck.setAttribute("shoppingList", shoppingList);
		request.setAttribute("message",num);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserListCon");
		dispatcher.forward(request, response);
	}

}
