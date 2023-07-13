package com.teamsix.model.bean.item;

import java.io.Serializable;

public class ItemimgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String imagename;
    
	public ItemimgDTO() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

    
}
