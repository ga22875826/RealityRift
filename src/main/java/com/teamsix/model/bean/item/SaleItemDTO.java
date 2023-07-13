package com.teamsix.model.bean.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "saleItem")
public class SaleItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleId", nullable = false)
	private SaleDTO sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemid", nullable = false)
	private ItemDTO item;

	@Column(precision = 2)
	private BigDecimal discount;
	
    @Transient
    @JsonProperty("images")
    public List<Itemimg> getItemImages() {
        if (this.item != null) {
            return this.item.getImages();
        }
        return Collections.emptyList();
    }
    
    @Transient
    @JsonProperty("discountedPrice")
    public BigDecimal getDiscountedPrice() {
        if (this.item != null && this.discount != null) {
            return this.item.getPrice().multiply(this.discount);
        }
        return null;
    }
    
    @Transient
    private BigDecimal discountedPrice;

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SaleDTO getSale() {
		return sale;
	}

	public void setSale(SaleDTO sale) {
		this.sale = sale;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}