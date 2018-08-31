package com.max.ordermanage.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.domain.User;
import com.max.ordermanage.service.UserService;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String loginName = (String) authcToken.getPrincipal();
		
		// get user password
		String password = new String((char[]) authcToken.getCredentials());
		User user = userService.findUserByNameAndPassword(loginName, password);
		if (user == null) {
			throw new UnknownAccountException();
		}
		
		JSONObject userJson = new JSONObject();
		userJson.put("username", user.getName());
		userJson.put("password", user.getPassword());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				userJson.getString("username"),
				userJson.getString("password"),
				getName()
				);
		return authenticationInfo;
	}

}
