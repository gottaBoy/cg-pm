package com.cargocn.pm.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class User {
	private Long id;

	private Long organizationId;

	private String username;
	
	private String realname;

	private String password;

	private String salt;

	private List<Long> roleIds; // 拥有的角色列表

	private Boolean locked = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public List<Long> getRoleIds() {
		if (roleIds == null) {
			roleIds = new ArrayList<Long>();
		}
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleIdsStr() {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (Long roleId : roleIds) {
			s.append(roleId);
			s.append(",");
		}
		return s.toString();
	}

	public void setRoleIdsStr(String roleIdsStr) {
		setRoleIds(new ArrayList<Long>());
		if (StringUtils.isEmpty(roleIdsStr)) {
			return;
		}
		String[] roleIdStrs = roleIdsStr.split(",");
		for (String roleIdStr : roleIdStrs) {
			if (StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			getRoleIds().add(Long.valueOf(roleIdStr));
		}
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", organizationId=" + organizationId + ", username='" + username + '\''
				+ ", password='" + password + '\'' + ", salt='" + salt + '\'' + ", roleIds=" + roleIds + ", locked="
				+ locked + '}';
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}