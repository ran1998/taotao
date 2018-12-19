package com.taotao.common.pojo;

public class EasyUITreeNode {
	private long id;
	private String name;
	private String state;
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "EasyUITreeNode [id=" + id + ", name=" + name + ", state=" + state + "]";
	}
	
	
}
