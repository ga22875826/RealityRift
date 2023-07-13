package com.teamsix.model.bean.item;

import java.math.BigDecimal;
import java.util.List;

public class Item {

	private int itemid;
	private String itemname;
	private String itemstatus;
	private String itemdetail;
	private String added;
	private List<Integer> categoryIdPath;
	private String categoryPath;
	private BigDecimal price;
	private int salescount;
	private int stock;
	
	public Item() {}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}

	public String getItemdetail() {
		return itemdetail;
	}

	public void setItemdetail(String itemdetail) {
		this.itemdetail = itemdetail;
	}

	public String getAdded() {
		return added;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public List<Integer> getCategoryIdPath() {
		return categoryIdPath;
	}

	public void setCategoryIdPath(List<Integer> categoryIdPath) {
		this.categoryIdPath = categoryIdPath;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSalescount() {
		return salescount;
	}

	public void setSalescount(int salescount) {
		this.salescount = salescount;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
