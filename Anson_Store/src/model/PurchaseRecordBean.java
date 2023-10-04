package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PurchaseRecordBean {

	private static final long serialVersionUID = 1L;

	private int ID;
	private String userID;
	private int itemNo;
	private int amount;
	private int price;
	private int totalPrice;
	private Timestamp purchaseDate;
	private String imagePath;
	private String itemName;
	private int specialItem;
	/**
	 * @return iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param i セットする iD
	 */
	public void setID(int i) {
		ID = i;
	}
	/**
	 * @return userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID セットする userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return itemNo
	 */
	public int getItemNo() {
		return itemNo;
	}
	/**
	 * @param itemNo セットする itemNo
	 */
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	/**
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount セットする amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @param price セットする price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * @return totalPrice
	 */
	public int getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice セットする totalPrice
	 */
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return purchaseDate
	 */
	public String getPurchaseDate() {
		String time = new SimpleDateFormat("yyyy/MM/dd").format(purchaseDate);

		return time;
	}


	/**
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName セットする itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return specialItem
	 */
	public int getSpecialItem() {
		return specialItem;
	}
	/**
	 * @param specialItem セットする specialItem
	 */
	public void setSpecialItem(int specialItem) {
		this.specialItem = specialItem;
	}
	/**
	 * @return imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath セットする imagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * @param timestamp セットする purchaseDate
	 */
	public void setPurchaseDate(Timestamp timestamp) {
		this.purchaseDate = timestamp;
	}


}
