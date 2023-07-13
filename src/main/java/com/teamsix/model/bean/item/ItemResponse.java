package com.teamsix.model.bean.item;

public class ItemResponse {
	private boolean success;
	private ItemDTO item;
	private String message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ItemDTO getItem() {
		return item;
	}
	public void setItem(ItemDTO deletedItem) {
		this.item = deletedItem;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
