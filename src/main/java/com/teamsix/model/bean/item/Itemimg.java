package com.teamsix.model.bean.item;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

	@Entity
	@Table(name = "Itemimg")
	public class Itemimg implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
	
	    @Column(name = "imagename")
	    private String imagename;
	    
	    @JsonIgnore
	    @Column(name="image")
	    private byte[] image;

	    
	    //多的那一方都會放參照物 join其pk
	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "itemid")
	    private ItemDTO item;
	
	    
		public byte[] getImage() {
			return image;
		}
		
		public void setImage(byte[] image) {
			this.image = image;
		}
    
    
		public Itemimg() {
		}
    
    public void setItem(ItemDTO item) {
        this.item = item;
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

	public ItemDTO getItem() {
		return item;
	}
  
	//---------------------

//	public int getItemid() {
//		return itemid;
//	}
//
//	public void setItemid(int itemid) {
//		this.itemid = itemid;
//	}
//	
//	
    
}