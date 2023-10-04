package model;

import java.sql.Timestamp;

public class ItemBean {

	private static final long serialVersionUID = 1L;

	private int itemNo;
	private String itemName;
	private int itemPrice;
	private int stockCount;
	private String itemImage;
	private int specialItem;
	private Timestamp updateTime;
	private int buyAmount;
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
	 * @return itemPrice
	 */
	public int getItemPrice() {
		return itemPrice;
	}
	/**
	 * @param itemPrice セットする itemPrice
	 */
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * @return stockCount
	 */
	public int getStockCount() {
		return stockCount;
	}
	/**
	 * @param stockCount セットする stockCount
	 */
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	/**
	 * @return itemImage
	 */
	public String getItemImage() {
		return itemImage;
	}
	/**
	 * @param itemImage セットする itemImage
	 */
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param specialItem セットする specialItem
	 */
	public void setUpdateTime(Timestamp timestamp) {
		this.updateTime = timestamp;
	}

	public int getBuyAmount() {
		return this.buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	/**
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
