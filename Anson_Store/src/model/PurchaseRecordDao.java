package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseRecordDao {

	Connection conn = null;
	PreparedStatement stmt = null;


	public int newPurchaseRecorcd(String userID,ArrayList<ItemBean> itemList) {
		int num = 0;


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			if(num ==0) {
				String sql = "insert into PURCHASERECORD_TABLE (ID,USER_ID,ITEM_NO,AMOUNT,PRICE,TOTALPRICE,IMAGE,"
						+ "ITEM_NAME,SPECIAL_ITEM)"
						+ " VALUES(purchaseRecord_sequence.nextval,?,?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
			}
			for(ItemBean item:itemList) {
				if(num!=0) {
					String sql = "insert into PURCHASERECORD_TABLE (ID,USER_ID,ITEM_NO,AMOUNT,PRICE,TOTALPRICE,IMAGE,"
							+ "ITEM_NAME,SPECIAL_ITEM)"
							+ " VALUES(purchaseRecord_sequence.currval,?,?,?,?,?,?,?,?)";
					stmt = conn.prepareStatement(sql);
				}
				stmt.setString(1, userID);
				stmt.setInt(2, item.getItemNo());
				stmt.setInt(3, item.getBuyAmount());
				stmt.setInt(4, item.getItemPrice());
				stmt.setInt(5, item.getItemPrice() * item.getBuyAmount());
				stmt.setString(6, item.getItemImage());
				stmt.setString(7, item.getItemName());
				stmt.setInt(8, item.getSpecialItem());
				num += stmt.executeUpdate();
			}

			stmt.close();

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


	public ArrayList<Integer> getAllOrderID(UreservationBean user) {

		ArrayList<Integer> orderID_List = new ArrayList<>();
		ResultSet rs=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);
			String sql = "select id from purchaserecord_table where user_id= ? group by id order by id desc";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, user.getUserID());
			rs=stmt.executeQuery();

			while(rs.next()) {
				orderID_List.add(rs.getInt("id"));
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
		return orderID_List;
	}



	public ArrayList<PurchaseRecordBean> getPurchasedItemByEachOderID(int orderID) {

		ArrayList<PurchaseRecordBean> recordList = new ArrayList<>();
		ResultSet rs=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);
			String sql = "select * from purchaserecord_table where id= ? order by id desc";

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, orderID);
			rs=stmt.executeQuery();

			while(rs.next()) {
				PurchaseRecordBean purchaseRecordBean = new PurchaseRecordBean();
				purchaseRecordBean.setID(rs.getInt("id"));
				purchaseRecordBean.setUserID(rs.getString("user_id"));
				purchaseRecordBean.setItemNo(rs.getInt("item_no"));
				purchaseRecordBean.setAmount(rs.getInt("amount"));
				purchaseRecordBean.setPrice(rs.getInt("price"));
				purchaseRecordBean.setTotalPrice(rs.getInt("totalprice"));
				purchaseRecordBean.setPurchaseDate(rs.getTimestamp("purchasedate"));
				purchaseRecordBean.setImagePath(rs.getString("image"));
				purchaseRecordBean.setItemName(rs.getString("item_name"));
				purchaseRecordBean.setSpecialItem(rs.getInt("special_item"));
				recordList.add(purchaseRecordBean);
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
		return recordList;
	}
}