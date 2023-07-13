package com.teamsix.model.bean.item;

import java.util.List;

public class CartItem {

	private List<ItemimgDTO> images;
	private int quantity;
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public CartItem() {
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<ItemimgDTO> getImages() {
		return images;
	}

	public void setImages(List<ItemimgDTO> images) {
		this.images = images;
	}

}
