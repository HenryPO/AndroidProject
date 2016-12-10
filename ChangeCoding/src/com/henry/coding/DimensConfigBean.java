package com.henry.coding;

public class DimensConfigBean {
	private String dpiName;
	private int width;
	private int height;
	private float weight;
	private boolean isShowDetailName;

	public String getDpiName() {
		return dpiName;
	}

	public void setDpiName(String dpiName) {
		this.dpiName = dpiName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public boolean isShowDetailName() {
		return isShowDetailName;
	}

	public void setShowDetailName(boolean isShowDetailName) {
		this.isShowDetailName = isShowDetailName;
	}

	public DimensConfigBean(String dpiName, int width, int height, float weight, boolean isShowDetailName) {
		super();
		this.dpiName = dpiName;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.isShowDetailName = isShowDetailName;
	}

	@Override
	protected DimensConfigBean clone()  {
		try {
			return (DimensConfigBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
