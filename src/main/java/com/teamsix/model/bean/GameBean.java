package com.teamsix.model.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "GAMEDATA")
public class GameBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gameid")
	private int gameid;
	
	@Column(name = "studio")
	private String studio;
	
	@Column(name = "gname")
	private String gname;
	
	@Column(name = "level")
	private String level;
	
	@Column(name = "gamestatus")
	private String gamestatus;
	
	@Column(name = "player")
	private String player;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "priceper")
	private String priceper;
	
	@Column(name ="textdesc")
	private String textdesc;
	
	@Column(name = "gameimg")
	private String gameimg;
	
	@JsonManagedReference //由這邊做JSON序列化
	@OneToMany(mappedBy = "gameBean",cascade = CascadeType.ALL,orphanRemoval = true /*fetch = FetchType.LAZY 預設lazy，可不寫，使用到圖片才去抓*/ )
	private List<Booking> booking = new ArrayList<>();
	
	public GameBean() {	}

	// ========================================================
	public int getGameid() { return gameid; }
	public String getStudio() { return studio; }
	public String getGname() { return gname; }
	public String getLevel() { return level; }
	public String getGamestatus() { return gamestatus; }
	public String getPlayer() { return player; }
	public String getTime() { return time; }
	public String getAddress() { return address; }
	public String getPhone() { return phone; }
	public String getPriceper() { return priceper; }
	public String getTextdesc() { return textdesc; }
	public String getGameimg() { return gameimg; }

	public void setGameid(int gameid) { this.gameid = gameid; }
	public void setStudio(String studio) { this.studio = studio; }
	public void setGname(String gname) { this.gname = gname; }
	public void setLevel(String level) { this.level = level; }
	public void setGamestatus(String gamestatus) { this.gamestatus = gamestatus; }
	public void setPlayer(String player) { this.player = player; }
	public void setTime(String time) { this.time = time; }
	public void setAddress(String address) { this.address = address; }
	public void setPhone(String phone) { this.phone = phone; }
	public void setPriceper(String priceper) { this.priceper = priceper; }
	public void setTextdesc(String textdesc) { this.textdesc = textdesc; }
	public void setGameimg(String fileName) { this.gameimg = fileName; }

	
}
