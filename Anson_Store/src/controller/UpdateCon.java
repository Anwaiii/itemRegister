package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import utill.FileUpload;

/**
 * Servlet implementation class UpdateCon
 */
public class UpdateCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCon() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");


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

		String path="C:\\Eclipse\\pleiades\\workspace\\Anson_Store\\WebContent\\files";
		FileUpload fileUpload = new FileUpload();
		ItemBean item = fileUpload.fileUp(request, path);

		StoreDao storeDao = new StoreDao();

		int itemNo = item.getItemNo();		//商品情報1
		String itemName = item.getItemName();	//商品情報2
		int num = 0;


		//	更新リンクを押したとき、itemNoしかとられなくて、itemNameはnullです。更新画面に遷移する
		if(itemName == null) {

			//item = storeDao.selectItemDao(itemNo);
			item = storeDao.selectItemDao(itemNo);

			request.setAttribute("beforeItemBean",item);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/Update.jsp");
			dispatcher.forward(request, response);

		}else {		// 更新ボタンを押したとき、更新する商品情報がすべて取られる(商品情報1-6)



			int itemPrice = item.getItemPrice();	//商品情報3
			int stockCount = item.getStockCount();	//商品情報4
			String itemImage = item.getItemImage();	//商品情報5
			int specialItem = item.getSpecialItem();	//商品情報6
			String beforetime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS").format(item.getUpdateTime());

			item.setUpdateTime(new Timestamp(System.currentTimeMillis()));

			//	商品名が重複しているかをチェックするメソッドを呼び出す
			int checkItemName = storeDao.checkItemName(item);

			//更新する前のもとのitem情報をafterBeanに代入する
			ItemBean afterBean = storeDao.selectItemDao(itemNo);

			if(itemImage != null && !itemImage.equals("")) {	//	写真が選択された場合、新しい写真をセットする
				item.setItemImage(itemImage);
			}else {		//写真が選択されなかった場合、もとの写真をセットする
				item.setItemImage(afterBean.getItemImage());
			}

			/*	新しい商品名がもとの商品名と同じであるとき（商品名が変更されていない）、もしくは新しい商品名が
				DBに存在しないとき、更新が実行される */
			if(afterBean.getItemName().equals(item.getItemName()) || checkItemName == 0) {
				String afterTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS").format(afterBean.getUpdateTime());
				if(beforetime.equals(afterTime)) {
					num=storeDao.updateDao(item);
				}else {
					num=2;	//✖更新済みエラーですです✖
				}
			}


			request.setAttribute("updateResult",num);
			ArrayList<ItemBean> itemList = new ArrayList<>();
			itemList = storeDao.allSearchDao();
			request.setAttribute("itemList", itemList);
			ServletContext app =this.getServletContext();
			RequestDispatcher dispatcher =  app.getRequestDispatcher("/List.jsp");
			dispatcher.forward(request, response);
		}




	}

}
