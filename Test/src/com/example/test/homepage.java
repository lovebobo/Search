package com.example.test;

import java.io.Serializable;

public class homepage implements Serializable{
	//ͼ����ע��
	private String  name;
	
	//ͼ��
	private int icon;


	public homepage(String name,int icon)
	{
		this.name = name;
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
