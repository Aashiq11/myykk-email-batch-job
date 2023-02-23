package com.myykk.model;

public class BatchEmailDTO {

	private String externalEmailSource;
	private int externalEmailID;
	private String from;
	private String subject;
	private String to;
	private int ccFlag;
	private int attachmentFlag;
	private int status;
	private java.util.Date sendDateTime;
	private java.util.Date sentDateTime;
	private String externalCode;
	private int bodyType;
	private int campaign;
	private int releaseNumber;
	
	//Recipient Table only Fields
	private int sequence;	  
    private int recipientType;
	
    //Body Table only Fields
    private String emailText;
    private int paragraphFlag;
	public String getExternalEmailSource() {
		return externalEmailSource;
	}
	public void setExternalEmailSource(String externalEmailSource) {
		this.externalEmailSource = externalEmailSource;
	}
	public int getExternalEmailID() {
		return externalEmailID;
	}
	public void setExternalEmailID(int externalEmailID) {
		this.externalEmailID = externalEmailID;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getCcFlag() {
		return ccFlag;
	}
	public void setCcFlag(int ccFlag) {
		this.ccFlag = ccFlag;
	}
	public int getAttachmentFlag() {
		return attachmentFlag;
	}
	public void setAttachmentFlag(int attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public java.util.Date getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(java.util.Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	public java.util.Date getSentDateTime() {
		return sentDateTime;
	}
	public void setSentDateTime(java.util.Date sentDateTime) {
		this.sentDateTime = sentDateTime;
	}
	public String getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	public int getBodyType() {
		return bodyType;
	}
	public void setBodyType(int bodyType) {
		this.bodyType = bodyType;
	}
	public int getCampaign() {
		return campaign;
	}
	public void setCampaign(int campaign) {
		this.campaign = campaign;
	}
	public int getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(int recipientType) {
		this.recipientType = recipientType;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	public int getParagraphFlag() {
		return paragraphFlag;
	}
	public void setParagraphFlag(int paragraphFlag) {
		this.paragraphFlag = paragraphFlag;
	}
    
    
}
