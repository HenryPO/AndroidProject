package com.mobile.library.http.bean;

import org.apache.http.message.BasicNameValuePair;

import com.mobile.library.http.enums.HttpType;

/**
 * 
 * Description 三个参数的键值对<br>
 * CreateDate 2014-5-13 <br>
 * 
 * @author qujl <br>
 */
public class HttpPair extends BasicNameValuePair {

	private HttpType type = HttpType.NORMAL;
	private String name;
	private String value;

	public HttpPair(String name, String value) {
		super(name, value);
		this.type = HttpType.NORMAL;
		this.name = name;
		this.value = value;
	}

	public HttpPair(HttpType httpType, String name, String value) {
		super(name, value);
		this.type = httpType;
		this.name = name;
		this.value = value;
	}

	public HttpType getType() {
		return type;
	}

	public void setType(HttpType httpType) {
		this.type = httpType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
