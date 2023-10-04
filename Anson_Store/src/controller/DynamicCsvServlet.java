package controller;

import java.io.File;
import java.io.FileWriter;
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
 * Servlet implementation class DynamicCsvServlet
 */
public class DynamicCsvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DynamicCsvServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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



		File file = new File("C:\\Eclipse\\pleiades\\workspace\\Anson_Store\\WebContent\\files\\item.csv");

		FileWriter fWriter = new FileWriter(file);
		fWriter.write("商品名,商品価格,在庫数,,目玉/一般\n");

		for(int i=0;i<itemList.size();i++) {
			fWriter.write(itemList.get(i).getItemName()+","+itemList.get(i).getItemPrice()+","
					+ itemList.get(i).getStockCount()+",,"+itemList.get(i).getSpecialItem()+"\n");


		}

		fWriter.close();
		request.setAttribute("csv", 1);
		request.setAttribute("itemList", itemList);

		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/List.jsp");
		dispatcher.forward(request, response);
	}

}
