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

/**
 * Servlet implementation class ListCon
 */
public class ListCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListCon() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
			HttpSession sessionCheck = request.getSession(false);
			if (sessionCheck == null || sessionCheck.getAttribute("userID") == null
					|| (Integer)sessionCheck.getAttribute("role") != 0) {
				response.sendRedirect("Login.jsp");
				return;
			}

		ArrayList<ItemBean> itemList = new ArrayList<>();
		StoreDao storeDao = new StoreDao();

		//	全商品検索メソッドを呼び出し、結果をitemListに代入する
		itemList = storeDao.allSearchDao();

		request.setAttribute("itemList",itemList);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/List.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 request.setCharacterEncoding("UTF-8");
			HttpSession sessionCheck = request.getSession(false);
			if (sessionCheck == null || sessionCheck.getAttribute("userID") == null
					|| (Integer)sessionCheck.getAttribute("role") != 0) {
				response.sendRedirect("Login.jsp");
				return;
			}

		ArrayList<ItemBean> itemList = new ArrayList<>();
		StoreDao storeDao = new StoreDao();


		//	入力された商品名のキーワードとspecialItemかの結果を変数に代入する
		String itemName = request.getParameter("itemName");
		String specialItem = request.getParameter("specialItem");


		/* 両方も入力されていないとき全商品検索メソッドを呼び出す
		 * 		>商品名が入力されていないときはitemNameの値がnullではなくempty ("") になる
		 * 		>目玉商品か一般商品かどちらも選択されていないときはspecialItemの値がnullになる
		 * それ以外の場合に入力結果に応じて関連する商品を探すメソッドを呼び出す
		 * 結果をitemListに代入する */
		if(itemName.isEmpty() && specialItem == null) {
			itemList = storeDao.allSearchDao();
		}else {
			itemList = storeDao.selectSearchDao(itemName, specialItem);
		}

		request.setAttribute("itemName",itemName);
		request.setAttribute("specialItem",specialItem);
		request.setAttribute("itemList",itemList);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/List.jsp");
		dispatcher.forward(request, response);
	}

}
