package cn.edu.jxnu.blog.domin;

import java.util.Date;

/**
 * 
 * @author： liguobin
 * @Description： 图册实体类。
 * @时间： 2017-12-15 上午10:57:18
 * @version： V1.0
 * 
 */
public class Picture {
	private Integer id; // 图片id
	private String url; // 图片地址
	private String name; // 图片名称
	private String publisher; // 图片发布人
	private Date date; // 图片更新日期
	private String dateStr;
	private Integer click; // 点赞数

	public Picture(Integer id, String url, String publisher, Date date,
			Integer click, String name) {
		super();
		this.id = id;
		this.url = url;
		this.publisher = publisher;
		this.date = date;
		this.click = click;
		this.setName(name);
	}

	public Picture() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", url=" + url + ", publisher="
				+ publisher + ",  dateStr="  +dateStr + ", click=" + click
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
