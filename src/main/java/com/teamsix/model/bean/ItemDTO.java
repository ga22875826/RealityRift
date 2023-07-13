package com.teamsix.model.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "item")
public class ItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemid; 
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Itemimg> images;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryid", nullable = false)
    @JsonBackReference
    private Category category;
    private String itemname;
    private BigDecimal price;
    private int salescount;
    private int stock;
    private String itemstatus;
    private String itemdetail;
    
    private String categoryPath;
    
    @JsonProperty("categoryIdPath")
    public List<Integer> getCategoryIdPath() {
        if (this.category != null) {
            return this.category.getPath();
        }
        return Collections.emptyList();
    }

    public ItemDTO() {
    }

    public void addImage(Itemimg image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        image.setItem(this);
    }
    
    
    


    public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public List<Itemimg> getImages() {
        return images;
    }

    public void setImages(List<Itemimg> images) {
        this.images = images;
    }

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


    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

}
