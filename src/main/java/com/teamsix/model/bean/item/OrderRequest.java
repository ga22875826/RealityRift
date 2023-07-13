package com.teamsix.model.bean.item;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
	private String orderStatus = "尚未付款";
	private BigDecimal totalAmount;
	private Integer memno;
	private List<CartItem> cartItems;

	public OrderRequest() {
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getMemno() {
		return memno;
	}

	public void setMemno(Integer memno) {
		this.memno = memno;
	}



}