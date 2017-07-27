package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

@Service("bosRealm")
@Transactional
public class BosRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("授权....");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		
		List<Role> roles = roleService.findByUsername(user);
		for (Role role : roles) {
			authorizationInfo.addRole(role.getKeyword());
		}
		
		List<Permission> permissions = permissionService.findByUser(user);
		
		for (Permission permission : permissions) {
			authorizationInfo.addStringPermission(permission.getKeyword());
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证....");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		
		User user = userService.findByUsername(usernamePasswordToken.getUsername());
		if (user==null) {
			//认证不成功
			return null;
		}else{
			return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		}
		
	}

}
