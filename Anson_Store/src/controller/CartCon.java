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
import model.PurchaseRecordDao;
import model.StoreDao;
import model.UreservationBean;
import model.UserDao;

/**
 * Servlet implementation class CartCon
 */
public class CartCon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int message = -99;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
			HttpSession sessionCheck = request.getSession(false);
			if (sessionCheck == null) {
				response.sendRedirect("Login.jsp");
				return;
			}
			System.out.println("CartCon.doGet()");
			StoreDao storeDao = new StoreDao();
			ArrayList<ItemBean> itemList = new ArrayList<>();
			ArrayList<ItemBean> shoppingList = (ArrayList<ItemBean>)sessionCheck.getAttribute("shoppingList");

			if(request.getParameter("message") != null) {
				message = Integer.parseInt(request.getParameter("message"));
			}

			if(request.getParameter("itemNo") != null) {
				int itemNo = Integer.parseInt(request.getParameter("itemNo"));
				for(int i = 0;i<shoppingList.size();i++) {
					if(itemNo == shoppingList.get(i).getItemNo()) {
						shoppingList.remove(i);
						break;
					}
				}
			}
//			for(int i=0;i<userCart.size();i++) {
//				ItemBean itemBean = storeDao.selectItemDao(userCart.get(i).getItemNo());
//				itemBean.setBuyAmount(userCart.get(i).getBuyAmount());
//				itemList.add(itemBean);
//			}

			sessionCheck.setAttribute("user", (UreservationBean)sessionCheck.getAttribute("user"));
			request.setAttribute("cart", shoppingList);
			request.setAttribute("message", message);
			sessionCheck.setAttribute("cart", shoppingList);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/Cart.jsp");
			dispatcher.forward(request, response);





	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
			HttpSession sessionCheck = request.getSession(false);
			if (sessionCheck == null) {
				response.sendRedirect("Login.jsp");
				return;
			}

			System.out.println("CartCon.doPost()");
			ArrayList<ItemBean> itemList = (ArrayList<ItemBean>) sessionCheck.getAttribute("cart");
			int totalAmount = 0; //	カート内のすべての商品の合計金額を表す変数


//			//	ChargeCon.javaからチャージ完了の情報を受け、カート画面に発信
//			if(request.getAttribute("message") != null && (Integer)request.getAttribute("message")==0) {
//				request.setAttribute("message", 0);
//				request.setAttribute("cart", itemList);
//				ServletContext app =this.getServletContext();
//				RequestDispatcher dispatcher =  app.getRequestDispatcher("/Cart.jsp");
//				dispatcher.forward(request, response);
//			}


			if(itemList != null) {
				UreservationBean userBean = (UreservationBean)sessionCheck.getAttribute("user");

				//	カート内のすべての商品の合計金額を計算する
				for(ItemBean item:itemList) {
					totalAmount += (item.getItemPrice() * item.getBuyAmount()) ;
				}

				//	残額が足りなかった場合
				if(userBean.getMoney() < totalAmount) {
					request.setAttribute("cart", itemList);
					request.setAttribute("message", -1);
					ServletContext app =this.getServletContext();
					RequestDispatcher dispatcher =  app.getRequestDispatcher("/Cart.jsp");
					dispatcher.forward(request, response);

				}else {
					StoreDao storeDao = new StoreDao();
					UserDao userDao = new UserDao();
					PurchaseRecordDao purchaseRecordDao = new PurchaseRecordDao();
					storeDao.reduceStockCountAndUserBalance(userBean, itemList, totalAmount);

					userBean = userDao.login(userBean.getUserID(),userBean.getPassword());
					request.setAttribute("message", 1);

					purchaseRecordDao.newPurchaseRecorcd(userBean.getUserID(), itemList);


					sessionCheck.setAttribute("user", userBean);
					sessionCheck.setAttribute("cart", null);
					sessionCheck.setAttribute("shoppingList", null);
					ServletContext app =this.getServletContext();
					RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserListCon");
					dispatcher.forward(request, response);


				}
			}else {
				request.setAttribute("message", -2);	//	カートは空です
				ServletContext app =this.getServletContext();
				RequestDispatcher dispatcher =  app.getRequestDispatcher("/Cart.jsp");
				dispatcher.forward(request, response);

			}













	}

}
