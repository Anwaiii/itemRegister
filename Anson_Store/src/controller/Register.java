package controller;

import java.io.IOException;

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
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
		if (sessionCheck == null) {
			response.sendRedirect("Login.jsp");
			return;
		}
		//		String path=getServletContext().getRealPath("files");
		String path="C:\\Eclipse\\pleiades\\workspace\\Anson_Store\\WebContent\\files";
		FileUpload fileUpload = new FileUpload();
		ItemBean regiBean = fileUpload.fileUp(request, path);


		StoreDao storeDao = new StoreDao();
		int num = 0;




		//String itemNo = request.getParameter("itemNo");
		//		String itemName = request.getParameter("itemName");
		//		String itemPrice = request.getParameter("itemPrice");
		//		String stockCount = request.getParameter("stockCount");
		//		String itemImage = request.getParameter("itemImage");
		//		String specialItem = request.getParameter("specialItem");

		//regiBean.setItemNo(Integer.parseInt(itemNo));
		//		regiBean.setItemName(itemName);
		//		regiBean.setItemPrice(Integer.parseInt(itemPrice));
		//		regiBean.setStockCount(Integer.parseInt(stockCount));
		//		regiBean.setItemImage(itemImage);
		//		regiBean.setSpecialItem(Integer.parseInt(specialItem));

		//	fileUpメソッドからチェックされた後、画像をアップロードできなかったとき
		if( regiBean.getItemImage() == null || regiBean.getItemImage().equals("") ) {
			num = -1;	//	✖その拡張子はサポートされていません✖
		}else {
			int checkItemName = storeDao.checkItemName(regiBean);

			if(checkItemName == 0) {  //商品名は重複なし
				num = storeDao.registerDao(regiBean);
			}}

		request.setAttribute("result",num);

		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/Register.jsp");
		dispatcher.forward(request, response);
	}

}
