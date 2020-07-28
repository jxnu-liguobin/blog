package cn.edu.jxnu.blog.domin;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author： liguobin
 * @Description： 公告 在这里公告只是一种提示，不需标题等
 * @时间： 2017-12-9 下午4:07:34
 * @version： V1.0
 * 
 */
public class Notice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; // 公告ID
	private String content; // 公告
	private String noticePublisher; // 发布人
	private Date noticeDate; // 公告发布日期
	private String noticeDateStr; // 公告发布日期Str
	private Integer level; // 公告级别 //0是一般 1 是重要 2 是特别重要

	public Notice() {

	}

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getNoticePublisher() {
		return noticePublisher;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public Integer getLevel() {
		return level;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNoticePublisher(String noticePublisher) {
		this.noticePublisher = noticePublisher;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getNoticeDateStr() {
		return noticeDateStr;
	}

	public void setNoticeDateStr(String noticeDateStr) {
		this.noticeDateStr = noticeDateStr;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", content=" + content
				+ ", noticePublisher=" + noticePublisher + ", noticeDate="
				+ noticeDate + ", noticeDateStr=" + noticeDateStr + ", level="
				+ level + "]";
	}

}
