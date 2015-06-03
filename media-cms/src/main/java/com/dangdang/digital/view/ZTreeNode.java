package com.dangdang.digital.view;

public class ZTreeNode {

	private Long id;
	private Long pId;
	private String name;
	private Boolean open = false;
	private Boolean checked = false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPId() {
		return pId;
	}
	public void setPId(Long pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
