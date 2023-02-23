package com.myykk.model;


public class EmailDTO{
	
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getDocnum() {
		return docnum;
	}
	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCustnum() {
		return custnum;
	}
	public void setCustnum(String custnum) {
		this.custnum = custnum;
	}
	public String getLangflg() {
		return langflg;
	}
	public void setLangflg(String langflg) {
		this.langflg = langflg;
	}
	public String getFrmemail() {
		return frmemail;
	}
	public void setFrmemail(String frmemail) {
		this.frmemail = frmemail;
	}
	private String emailid;
	private String docnum; 
	private String createtime; 
	private String custnum;
	private String langflg; 
	private String frmemail; 	
}
