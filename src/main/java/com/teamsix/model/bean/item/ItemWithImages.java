package com.teamsix.model.bean.item;

import java.util.List;

public class ItemWithImages {

	private ItemDTO item;

	private List<Itemimg> images;

	public ItemWithImages(ItemDTO item, List<Itemimg> images) {
		this.item = item;
		this.images = images;
	}

	public ItemWithImages() {
	};

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public List<Itemimg> getImages() {
		return images;
	}

	public void setImages(List<Itemimg> images) {
		this.images = images;
	}

}
