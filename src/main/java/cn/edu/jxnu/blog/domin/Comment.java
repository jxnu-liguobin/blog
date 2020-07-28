package cn.edu.jxnu.blog.domin;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 评论实体类
 */
@SuppressWarnings("serial")
public class Comment implements Serializable {

	/**
	 * 
	 */
	private Integer id; // id
	private String userIp; // 评论者ip
	private String content; // 评论内容
	private Date commentDate; // 评论日期
	private String commentDateStr; // 评论日期str
	private Integer state; // 审核状态 0待审核 1通过 2未通过
	private Blog blog; // 所评论博客
	private String address; // 所评论博客

	public Comment() {
	}

	public Comment(Integer id, String userIp, String content, Date commentDate,
			String address, Integer state, Blog blog) {
		this.id = id;
		this.userIp = userIp;
		this.content = content;
		this.commentDate = commentDate;
		this.state = state;
		this.blog = blog;
		this.address = address;
	}

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

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	@Override
	public String toString() {
		return "Comment [id=" + id + ", userIp=" + userIp + ", content="
				+ content + ", commentDate=" + commentDate
				+ ", commentDateStr=" + commentDateStr + ", state=" + state
				+ ", blog=" + blog + ", address=" + address + "]";
	}

	public String getCommentDateStr() {
		return commentDateStr;
	}

	public void setCommentDateStr(String commentDateStr) {
		this.commentDateStr = commentDateStr;
	}
}
