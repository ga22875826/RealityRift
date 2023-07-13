package com.teamsix.model.bean.item;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "sale")
public class SaleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int saleId;
	private String saleName; // 特價名稱

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date saleStart; // 特價開始時間

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date saleEnd;
	private String saleStatus;

	public boolean shouldBeStarted() {
	    return new Date().after(this.saleStart) && this.saleStatus.equals("準備中");
	}

	public boolean shouldBeEnded() {
	    return new Date().after(this.saleEnd) && this.saleStatus.equals("進行中");
	}

	public void start() {
		saleStatus = "進行中";
	}

	public void end() {
		saleStatus = "已結束";
	}

	public void startSaleNow() {
	    Date now = new Date();
	    if (now.after(saleEnd)) {
	        throw new IllegalArgumentException("Sales has already ended. Unable to start.");
	    }
	    saleStart = now; // 更新特賣開始時間為現在時間
	    saleStatus = "進行中"; // 更新特賣狀態為進行中
	}

	public void endSaleNow() {
	    Date now = new Date();
	    if (now.before(saleStart)) {
	        throw new IllegalArgumentException("Sales has not started yet. Unable to end.");
	    }
	    saleEnd = now; // 更新特賣結束時間為現在時間
	    saleStatus = "已結束"; // 更新特賣狀態為特賣結束
	}

	public void pauseSale() {
		saleStatus = "已暫停";
	}

	public void resumeSale() {
		saleStatus = "進行中";
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public Date getSaleStart() {
		return saleStart;
	}

	public void setSaleStart(Date saleStart) {
		this.saleStart = saleStart;
	}

	public Date getSaleEnd() {
		return saleEnd;
	}

	public void setSaleEnd(Date saleEnd) {
		this.saleEnd = saleEnd;
	}

}
