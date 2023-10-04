package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class StoreDao {

	Connection conn = null;
	PreparedStatement stmt = null;

	//	商品情報をDBへ登録する
	public int registerDao(ItemBean regiBean) {

		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql = "insert into item_table (ITEM_NO,ITEM_NAME,ITEM_PRICE,ITEM_IMAGE_PATH,SPECIAL_ITEM)"
					+ " VALUES(ITEM_SEQUENCE.nextval,?,?,?,?)";

			stmt = conn.prepareStatement(sql);

			//stmt.setInt(1, regiBean.getItemNo());
			stmt.setString(1, regiBean.getItemName());
			stmt.setInt(2, regiBean.getItemPrice());
			stmt.setString(3, regiBean.getItemImage());
			stmt.setInt(4, regiBean.getSpecialItem());

			//	item_tableが更新できたとき、numは1になる。そして、stock_tableの更新が行われる
			num = stmt.executeUpdate();

			sql = "insert into stock_table (ID,ITEM_NO,COUNT) VALUES(ITEM_SEQUENCE.currval,"
					+ "ITEM_SEQUENCE.currval,?)";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, regiBean.getStockCount());
			stmt.executeUpdate();


			stmt.close();

			conn.commit();

		}catch(SQLException | ClassNotFoundException e) {
			num=0;	//item_tableの更新エラーはもちろん、stock_tableの更新が失敗したとき、numの値をリセットする
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return num;
	}


	//	DBの商品情報を更新する
	public int updateDao(ItemBean updateBean){
		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql = "update item_table set item_name=?,item_price=?,item_image_path=?,special_item =?, "
					+ "update_time = current_timestamp where item_no = ? ";

			stmt = conn.prepareStatement(sql);

			//stmt.setInt(1, regiBean.getItemNo());
			stmt.setString(1, updateBean.getItemName());
			stmt.setInt(2, updateBean.getItemPrice());
			stmt.setString(3, updateBean.getItemImage());
			stmt.setInt(4, updateBean.getSpecialItem());
			stmt.setInt(5, updateBean.getItemNo());

			//	item_tableが更新できたとき、numは1になる。そして、stock_tableの更新が行われる
			num = stmt.executeUpdate();

			sql = "update (select i.item_no, s.count,s.update_time "
					+ "from item_table i join stock_table s on i.item_no = s.item_no) "
					+ "set COUNT = ? ,update_time = current_timestamp where item_no=?";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, updateBean.getStockCount());
			stmt.setInt(2, updateBean.getItemNo());
			stmt.executeUpdate();


			stmt.close();

			conn.commit();

		}catch(SQLException | ClassNotFoundException e) {
			num=0;	//item_tableの更新エラーはもちろん、stock_tableの更新が失敗したとき、numの値をリセットする
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return num;
	}


	//	DBの商品情報を削除する
	public int deleteDao(int itemNo){
		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql = "delete from stock_table where item_no = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, itemNo);
			stmt.executeUpdate();

			sql = "delete from item_table where item_no = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, itemNo);
			 // stock_tableとitem_table両方を削除できたら、numに完了した件数を返します。
			num = stmt.executeUpdate();

			stmt.close();

			conn.commit();

		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return num;
	}


	public int checkItemName(ItemBean regiBean) {

		ResultSet rs=null;
		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);
			String sql = "select COUNT(*) from item_table where item_name=?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, regiBean.getItemName());

			rs=stmt.executeQuery();
			rs.next();
			num=rs.getInt(1);
			rs.close();

		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}

	//すべての商品をリスト型で返す
	public ArrayList<ItemBean> allSearchDao() {

		ArrayList<ItemBean> itemList = new ArrayList<>();
		ResultSet rs=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);
			String sql = "select * from item_table i join stock_table s on i.item_no=s.item_no";

			stmt = conn.prepareStatement(sql);

			rs=stmt.executeQuery();

			while(rs.next()) {
				ItemBean itemBean = new ItemBean();
				itemBean.setItemNo(rs.getInt("item_no"));
				itemBean.setItemName(rs.getString("item_name"));
				itemBean.setItemPrice(rs.getInt("item_price"));
				itemBean.setStockCount(rs.getInt("count"));
				itemBean.setItemImage(rs.getString("item_image_path"));
				itemBean.setSpecialItem(rs.getInt("special_item"));
				itemList.add(itemBean);
			}

			rs.close();

		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemList;
	}


	//検索条件に合っているすべての商品をリスト型で返す
	public ArrayList<ItemBean> selectSearchDao(String itemName,String specialItem) {

		ArrayList<ItemBean> itemList = new ArrayList<>();
		ResultSet rs=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			//	item_tableのitem_name、item_priceとstock_tableの在庫数をselectする
			String sql ="select * from item_table i join stock_table s "
					+ "on i.item_no=s.item_no "	 // <<<空白入れることで、次のwhereとの間隔を作る
					+ "where i.item_name like ? ";// <<<空白入れることで、specialItem != nullの場合に次のandとの間隔を作る

			//   目玉商品と一般商品がどちらか選択されたとき、新しいバインド変数のsql文を追加する
			if(specialItem != null) {
				sql += "and special_item = ?";
			}

			stmt = conn.prepareStatement(sql);

			//	itemNameの値を1つ目のバインド変数に代入する
			stmt.setString(1, "%"+itemName+"%");

			//   目玉商品と一般商品がどちらか選択されたとき、specialItemの値を2つ目のバインド変数に代入する
			if(specialItem != null) {
				stmt.setString(2, specialItem);
			}

			rs=stmt.executeQuery();

			//	ItemBeanクラスのobjectを作り、返された結果をこのobjectのフィールドに代入する
			while(rs.next()) {
				ItemBean itemBean = new ItemBean();
				itemBean.setItemNo(rs.getInt("item_no"));
				itemBean.setItemName(rs.getString("item_name"));
				itemBean.setItemPrice(rs.getInt("item_price"));
				itemBean.setStockCount(rs.getInt("count"));
				itemBean.setItemImage(rs.getString("item_image_path"));
				itemBean.setSpecialItem(rs.getInt("special_item"));
				itemList.add(itemBean);
			}
			rs.close();
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemList;
	}

	//	itemNoで探した商品の情報を返す
	public ItemBean selectItemDao(int itemNo){

		ItemBean itemBean = new ItemBean();
		ResultSet rs=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);


			String sql ="select * from item_table i join stock_table s on i.item_no = s.item_no where i.item_no = ? ";


			stmt = conn.prepareStatement(sql);

			//	itemNoの値を1つ目のバインド変数に代入する
			stmt.setInt(1, itemNo);

			rs=stmt.executeQuery();

			//	ItemBeanクラスのobjectを作り、返された結果をこのobjectのフィールドに代入する
			while(rs.next()) {
				itemBean.setItemNo(rs.getInt("item_no"));
				itemBean.setItemName(rs.getString("item_name"));
				itemBean.setItemPrice(rs.getInt("item_price"));
				itemBean.setStockCount(rs.getInt("count"));
				itemBean.setItemImage(rs.getString("item_image_path"));
				itemBean.setSpecialItem(rs.getInt("special_item"));
				itemBean.setUpdateTime(rs.getTimestamp("update_time"));
			}

			rs.close();
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemBean;

	}


	//	注文確定したとき、在庫数量とユーザーの金を減らす
	public int reduceStockCountAndUserBalance(UreservationBean user,ArrayList<ItemBean> cart,int totalAmount){

		ItemBean itemBean = new ItemBean();
		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);


			for(int i = 0;i<cart.size();i++) {
				System.out.println( "buyAmount:"+cart.get(i).getBuyAmount());
				System.out.println( "itemNO:"+cart.get(i).getItemNo());

			String sql ="update stock_table set count = (count - ?) where item_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cart.get(i).getBuyAmount());
			stmt.setInt(2, cart.get(i).getItemNo());
			num = stmt.executeUpdate();
			}

			String sql ="update user_table set money = (money - ?) where user_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, totalAmount);
			stmt.setString(2, user.getUserID());
			num = stmt.executeUpdate();



			stmt.close();
			System.out.println("1231321");
			conn.commit();
		}catch(SQLException | ClassNotFoundException e) {
			num=0;
			e.printStackTrace();
		}finally {

			try {
				if(stmt != null) {
					stmt.close();
				}

				if(conn != null) {
					conn.rollback();
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;

	}
}

