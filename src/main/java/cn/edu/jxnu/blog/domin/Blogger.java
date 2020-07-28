package cn.edu.jxnu.blog.domin;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * @Description 博主信息实体类
 */
@Component
// 注册bean 为spring容器管理
public class Blogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer id;
	private String userName; // 用户名
	private String password; // 密码
	private String profile; // 博主信息
	private String nickName; // 昵称
	private String sign; // 博主签名
	private String imageName; // 图片路径

	public Blogger(String userName, String password, String profile,
			String nickName, String sign, String imageName) {
		this.userName = userName;
		this.password = password;
		this.profile = profile;
		this.nickName = nickName;
		this.sign = sign;
		this.imageName = imageName;
	}

	public Blogger() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "Blogger{" + "id=" + id + ", userName='" + userName + '\''
				+ ", password='" + password + '\'' + ", profile='" + profile
				+ '\'' + ", nickName='" + nickName + '\'' + ", sign='" + sign
				+ '\'' + ", imageName='" + imageName + '\'' + '}';
	}
}