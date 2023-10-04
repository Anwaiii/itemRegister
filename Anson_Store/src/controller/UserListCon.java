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
 * Servlet implementation class UserListCon
 */
public class UserListCon extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("UserListCon:doGet()");

		ArrayList<ItemBean> itemList = new ArrayList<>();
		StoreDao storeDao = new StoreDao();

		//	全商品検索メソッドを呼び出し、結果をitemListに代入する
		itemList = storeDao.allSearchDao();

		request.setAttribute("itemList",itemList);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("UserListCon:doPost()");
		ArrayList<ItemBean> itemList = new ArrayList<>();
		StoreDao storeDao = new StoreDao();
		System.out.println("userlistcon:post");
		HttpSession sessionCheck = request.getSession(false);
		 int message = -99;
//		sessionCheck.getAttribute("userID");
//		sessionCheck.getAttribute("role");


		if(request.getAttribute("message") != null) {
			message = (Integer)request.getAttribute("message");
		}

		//	入力された商品名のキーワードとspecialItemかの結果を変数に代入する
		String itemName = request.getParameter("itemName");
		String specialItem = request.getParameter("specialItem");

		if(itemName == null) {
			itemName = "";
		}
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

		System.out.println("message:"+message);
		request.setAttribute("itemName",itemName);
		request.setAttribute("message", message);
		request.setAttribute("specialItem",specialItem);
		request.setAttribute("itemList",itemList);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/UserList.jsp");
		dispatcher.forward(request, response);
	}

}