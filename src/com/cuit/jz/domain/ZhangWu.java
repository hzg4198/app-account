package com.cuit.jz.domain;


public class ZhangWu {
	private int zwid;
	private String flname;
	private double money;
	private String zhanghu;
	private String createtime;
	private String description;
	private String username;

	public int getZwid() {
		return zwid;
	}
	public void setZwid(int zwid) {
		this.zwid = zwid;
	}
	public String getFlname() {
		return flname;
	}
	public void setFlname(String flname) {
		this.flname = flname;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getZhanghu() {
		return zhanghu;
	}
	public void setZhanghu(String zhanghu) {
		this.zhanghu = zhanghu;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ZhangWu{" +
				"zwid=" + zwid +
				", flname='" + flname + '\'' +
				", money=" + money +
				", zhanghu='" + zhanghu + '\'' +
				", createtime='" + createtime + '\'' +
				", description='" + description + '\'' +
				", username='" + username + '\'' +
				'}';
	}

}
