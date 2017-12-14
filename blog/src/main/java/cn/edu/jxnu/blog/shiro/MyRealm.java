package cn.edu.jxnu.blog.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.service.BloggerService;

/**
 * @Description 自定义realm
 */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private BloggerService bloggerService;

	/**
	 * 为当前登陆的用户授予角色和权限
	 * 
	 * @param principalCollection
	 * @return 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		// 因为我们是个人博客 所以不存在角色权限
		return null;
	}

	/**
	 * 身份认证
	 * 
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		AuthenticationInfo authcInfo = null;
		// 获取页面传送过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = (String) token.getUsername(); // 获取输入的用户名
		System.out.println("前台获取的用户名：" + username);
		Blogger blogger = bloggerService.getBloggerByName(username); // 根据输入的用户名从数据库查询用户信息
		if (blogger != null) {
			System.out.println("从数据库用户获取到的用户名-密码：" + blogger.getUserName()
					+ "-" + blogger.getPassword());
			// 不等于空的时候还要判断密码正确
			System.out.println("正在验证密码......");
			/***** 注意：此处把当前登录的用户的信息保存在session中 ********/
			SecurityUtils.getSubject().getSession().setTimeout(1000*60);
			SecurityUtils.getSubject().getSession()
					.setAttribute("currentUser", blogger);// 把当前用户存到session中  //当前session共享系统session的过期时间
			// 把数据库的用户名，密码，取出交给SimpleAuthenticationInfo
			authcInfo = new SimpleAuthenticationInfo(username,
					blogger.getPassword(), "MyRealm");
		}

		return authcInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
