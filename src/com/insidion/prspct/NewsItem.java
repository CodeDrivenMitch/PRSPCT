package com.insidion.prspct;

public class NewsItem {

	private long id = -1;
	private long nid = -1;
	private String title = null;
	private String content = null;
	private long date = -1;
	private String icon_URL = null;
	private String featured_URL = null;
	
	public NewsItem(){}
	
	public boolean isValid()
	{
		if(this.nid > 0 && this.date > 0 && title != null && content != null) return true;
		return false;
	}
	
	public boolean hasDatabaseID()
	{
		if(this.id > -1) return true;
		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNid() {
		return nid;
	}

	public void setNid(long nid) {
		this.nid = nid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getIcon_URL() {
		return icon_URL;
	}

	public void setIcon_URL(String icon_URL) {
		this.icon_URL = icon_URL;
	}

	public String getFeatured_URL() {
		return featured_URL;
	}

	public void setFeatured_URL(String featured_URL) {
		this.featured_URL = featured_URL;
	}

}
