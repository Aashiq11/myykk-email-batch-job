package com.myykk.model;

import javax.mail.internet.InternetAddress;

public class BatchEmailBean {

	private String fromAddress = null;
	private String emailSubjet = null;
	private String bodyText	= null;
	private InternetAddress[] toAddress = null;
	private InternetAddress[] ccAddress = null;
	private InternetAddress[] bccAddress = null;
	private boolean htmlFormat = false;
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
	public InternetAddress[] getToAddress() {
		return toAddress;
	}
	public void setToAddress(InternetAddress[] toAddress) {
		this.toAddress = toAddress;
	}
	public InternetAddress[] getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(InternetAddress[] ccAddress) {
		this.ccAddress = ccAddress;
	}
	public InternetAddress[] getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(InternetAddress[] bccAddress) {
		this.bccAddress = bccAddress;
	}
	public boolean isHtmlFormat() {
		return htmlFormat;
	}
	public void setHtmlFormat(boolean htmlFormat) {
		this.htmlFormat = htmlFormat;
	}
	
	
}
