package cn.edu.jxnu.blog.domin;

import java.util.Date;

/**
 * @Description 留言实体类
 * 
 * @author liguobin
 * 
 */
public class Message {
	private Integer id; // id
	private String userIp; // 留言者ip
	private String content; // 留言内容
	private Date MessageDate; // 留言日期
	private String MessageDateStr; // 留言日期str
	private Integer state; // 留言日期str
	private String email;// 留言者邮箱;

	private String address;// ip地址解析

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMessageDate() {
		return MessageDate;
	}

	public void setMessageDate(Date messageDate) {
		MessageDate = messageDate;
	}

	public String getMessageDateStr() {
		return MessageDateStr;
	}

	public void setMessageDateStr(String messageDateStr) {
		MessageDateStr = messageDateStr;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Message(Integer id, String userIp, String content, Date messageDate,
			String messageDateStr, Integer state, String email, String address) {
		super();
		this.id = id;
		this.userIp = userIp;
		this.content = content;
		MessageDate = messageDate;
		MessageDateStr = messageDateStr;
		this.state = state;
		this.email = email;
		this.address = address;
	}

	public Message() {
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", userIp=" + userIp + ", content="
				+ content + ", MessageDate=" + MessageDate
				+ ", MessageDateStr=" + MessageDateStr + ", state=" + state
				+ ", email=" + email + ", address=" + address + "]";
	}
}
