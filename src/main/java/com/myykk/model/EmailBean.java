package com.myykk.model;

import java.util.Map;

public class EmailBean {
			
	private String toAddress = "";
	private String fromAddress = "";
	private String emailSubjet = "";
	private String bodyText	= "";
	private boolean htmlFormat = true;
	private String htmlTemplate;
	
	public String getHtmlTemplate() {
		return htmlTemplate;
	}
	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	private Map data;
	
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getEmailSubjet() {
		return emailSubjet;
	}
	public void setEmailSubjet(String emailSubjet) {
		this.emailSubjet = emailSubjet;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public boolean isHtmlFormat() {
		return htmlFormat;
	}
	public void setHtmlFormat(boolean htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

}
