package cn.edu.jxnu.blog.domin;

/**
 * 
 * @author： liguobin
 * @Description： 点赞表，保存ip与图片的关系
 * @时间： 2017-12-15 上午10:54:43
 * @version： V1.0
 * 
 */
public class Great {
	private Integer id; // 点赞表
	private String userIp; // 点赞的用户ip
	private Integer imageId; // 点赞的图片id;

	public Great(Integer id, String userIp, Integer imageId) {
		super();
		this.id = id;
		this.userIp = userIp;
		this.imageId = imageId;
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

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Great() {

	}

	@Override
	public String toString() {
		return "Great [id=" + id + ", userIp=" + userIp + ", imageId="
				+ imageId + "]";
	}

}
