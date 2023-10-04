package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	Connection conn = null;
	PreparedStatement stmt = null;

	//	新規ユーザー登録のメソッド
	public int newUserRegister(String userID,String userName,String password) {
		int num = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql = "insert into user_table (USER_ID,USER_NAME,PASSWORD)"
					+ " VALUES(?,?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, userID);
			stmt.setString(2, userName);
			stmt.setString(3, password);


			//	item_tableが更新できたとき、numは1になる。そして、stock_tableの更新が行われる
			num = stmt.executeUpdate();

			sql = "insert into role_table (USER_ID,ROLE) VALUES(?,1)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userID);
			stmt.executeUpdate();


			stmt.close();

			conn.commit();

		}catch(SQLException | ClassNotFoundException e) {
			num=0;	//user_tableの更新エラーはもちろん、role_tableの更新が失敗したとき、numの値をリセットする
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


	//	新規登録の時にuserIDが既に存在しているかをチェックするメソッド
	public String checkUserID(String userID) {

		ResultSet rs=null;
		String id="";
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql ="select user_id from user_table where user_id=? ";


			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userID);

			rs=stmt.executeQuery();

			//	ItemBeanクラスのobjectを作り、返された結果をこのobjectのフィールドに代入する
			while(rs.next()) {
				id = rs.getString("user_id");
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
		return id;
	}

	public UreservationBean login(String userID,String password) {

		UreservationBean userBean = new UreservationBean();
		ResultSet rs=null;
		String userID_result = "";
		String userPassword_result = "";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);

			String sql ="select * from role_table r join user_table u on r.user_id=u.user_id where u.user_id =? ";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userID);

			rs=stmt.executeQuery();

			//
			while(rs.next()) {
				userID_result = rs.getString("user_id");
				userPassword_result = rs.getString("password");
				if(userID_result.equals(userID) && userPassword_result.equals(password)) {
					userBean.setUserID(rs.getString("user_id"));
					userBean.setUserName(rs.getString("user_name"));
					userBean.setPassword(rs.getString("password"));
					userBean.setMoney(rs.getInt("money"));
					userBean.setRole(rs.getString("role"));
					return userBean;
				}
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
		return null;
	}

	public void userCharge(UreservationBean user,int balance) {

		ItemBean itemBean = new ItemBean();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","WOng","159951");
			conn.setAutoCommit(false);




			String sql ="update user_table set money = money + ? where user_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, balance);
			stmt.setString(2, user.getUserID());
			stmt.executeUpdate();






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
	}
}

