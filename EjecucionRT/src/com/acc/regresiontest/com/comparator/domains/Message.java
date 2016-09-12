package com.acc.regresiontest.com.comparator.domains;


public class Message {
	
	private String id;
	private String xml;
	
	
	
	public Message(String id, String xml) {
		super();
		this.id = id;
		this.xml = xml;
	}
	
	
	
	public Message() {
		super();
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mensaje [id=");
		builder.append(id);
		builder.append(", xml=");
		builder.append(xml);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
