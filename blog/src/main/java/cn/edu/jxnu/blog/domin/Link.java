package cn.edu.jxnu.blog.domin;



/**
 * @Description 友情链接实体类
 */
public class Link {

	/**
	 * 
	 */
	private Integer id;// 连接id
	private String linkName;// 连接名
	private String linkUrl;// 连接url
	private Integer orderNum;// 排序

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
