package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
 * Servlet implementation class CsvCon
 */
public class CsvCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CsvCon() {
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
			if (sessionCheck == null || sessionCheck.getAttribute("userID") == null
					|| (Integer)sessionCheck.getAttribute("role") != 0) {
				response.sendRedirect("Login.jsp");
				return;
			}

		//		String path=getServletContext().getRealPath("files");
		String path="C:\\Eclipse\\pleiades\\workspace\\Anson_Store\\WebContent\\files";
		FileUpload fileUpload = new FileUpload();
		String result = fileUpload.csvFileUp(request, path);


		StoreDao storeDao = new StoreDao();
		ArrayList<ItemBean> itemList = new ArrayList<>();
		ArrayList<String> registeredItem = new ArrayList<>();
		ArrayList<String> nonRegisteredItem = new ArrayList<>();
		int failRegisteredItem = 0;
		int num = 0;
		String itemName="";
		int itemPrice = 0;
		int stockCount = 0;
		String itemImage = "noPhoto.jpg";
		int specialItem =0;

		if(result.equals("")) {
			num = -2;
		}else {

			File fileName = new File(path+"/"+result);

			Scanner scanner = new Scanner(fileName);
			System.out.println("ファイルを読み込みました。");
			if(scanner.hasNext())scanner.next();

//			String[] data = new String[5];
//			data = bReader.readLine().split(",");

			try {

				while(scanner.hasNext()) {
					String[] data = new String[5];
					data=scanner.next().split(",");

					itemName = data[0];

					//	先にparseしようとして、データが不正の場合にcatch文によってエラーメッセージが表示される
					itemPrice = Integer.parseInt(data[1]);
					stockCount = Integer.parseInt(data[2]);
					specialItem = Integer.parseInt(data[4]);

					if(itemName.equals("")) {
						num = -3;	//	✖登録に失敗しました（データが不正です）✖
						break;
					}else if(itemName.length() > 100) {
						num = -4;	//	✖登録に失敗しました（商品名は100文字までです）✖
						break;
					}else if(itemPrice < 0 || itemPrice > 100000) {
						num = -5;	//	✖登録に失敗しました（商品価格は0-100000円までです）✖
						break;
					}else if(stockCount < 0 || stockCount > 1000) {
						num = -6;	//	✖登録に失敗しました（在庫数は0-1000までです）✖
						break;
					}else if(specialItem < 0 || specialItem > 1) {
						num = -7;	//	✖登録に失敗しました（目玉商品の値が不正です）✖
						break;
					}else {
						ItemBean itemBean = new ItemBean();

						itemBean.setItemName(itemName);
						itemBean.setItemPrice(itemPrice);
						itemBean.setStockCount(stockCount);
						itemBean.setItemImage(itemImage);
						itemBean.setSpecialItem(specialItem);
						itemList.add(itemBean);
					}
				}

			}catch(NumberFormatException e) {
				num = -3;	//	✖登録に失敗しました（データが不正です）✖
			}finally {
				scanner.close();
			}
			fileName.deleteOnExit();
		}

			if(num != 0) {	//	変化あり、数字によってRegister.jspにエラーメッセージが表示される
				request.setAttribute("result",num);
			}else {		//	変化なし、Register.jspのelse文がトリガーされる
				request.setAttribute("result",99);
			}


			if(num == 0) {	//	変化なし、csvファイルとその内容が無事である場合に、商品登録を行う

				for(int i = 0;i<itemList.size();i++) {
					int checkItemName = storeDao.checkItemName(itemList.get(i));

					if(checkItemName == 0) {  //商品名は重複なし
						int registerResult = storeDao.registerDao(itemList.get(i));

						//商品登録ができるたびに、登録できた商品を成功リストに追加し、numに1を足す。
						if(registerResult == 1) {
						num++;
						registeredItem.add(itemList.get(i).getItemName());
						}

					}else {	//商品登録が失敗するたびに、登録できなかった商品を失敗リストに追加し、failRegisteredItemに1を足す。
						failRegisteredItem++;
						nonRegisteredItem.add(itemList.get(i).getItemName());

					}



				}

			}



		request.setAttribute("succussedNumber",num);
		request.setAttribute("successedItem",registeredItem);
		request.setAttribute("failedNumber",failRegisteredItem);
		request.setAttribute("failedItem",nonRegisteredItem);
		ServletContext app =this.getServletContext();
		RequestDispatcher dispatcher =  app.getRequestDispatcher("/Register.jsp");
		dispatcher.forward(request, response);
	}

}
