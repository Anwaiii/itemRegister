package model;

import java.util.ArrayList;

public class UreservationBean {

	private static final long serialVersionUID = 1L;

	private String userID;
	private String userName;
	private String password;
	private int money;
	private int role;
	private ArrayList<Item> cart = new ArrayList<>();

	/**
	 * @return userID
	 */

	public String getUserID() {
		return userID;
	}


	 public void setUserID(String userID) {
		this.userID = userID;
	 }


	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return money
	 */
	public int getMoney() {
		return money;
	}


	/**
	 * @param money セットする money
	 */
	public void setMoney(int money) {
		this.money = money;
	}


	public int getRole() {
		return role;
	}
	/**
	 * @param password セットする password
	 */
	public void setRole(String role) {
		this.role = Integer.parseInt(role);
	}

	public ArrayList<Item> getCart() {
		ArrayList<Item> cartList = new ArrayList<>(cart);
		return cartList;
	}

	public void addItem(int itemNo,int buyAmount) {
		Item item=new Item(itemNo, buyAmount);
		cart.add(item);
	}

	public class Item{
		private static final long serialVersionUID = 1L;

		private int itemNo;
		private String itemName;
		private int itemPrice;
		private int specialItem;
		private int buyAmount;

		public Item(int itemNo, int buyAmount) {
			this.itemNo = itemNo;
			this.buyAmount = buyAmount;
		}
		/**
		 * @return itemNo
		 */
		public int getItemNo() {
			return itemNo;
		}

		/**
		 * @return itemName

		/**
		 * @return buyAmount
		 */
		public int getBuyAmount() {
			return buyAmount;
		}
		/**
		 * @param buyAmount セットする buyAmount
		 */
		public void setBuyAmount(int buyAmount) {
			this.buyAmount = buyAmount;
		}





	}
}
