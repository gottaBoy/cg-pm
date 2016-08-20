package com.cargocn.pm.dao;

import java.util.List;

import com.cargocn.pm.bean.Role;

public interface RoleMapper {
	public int deleteByPrimaryKey(Long id);

	public int insert(Role record);

	public int insertSelective(Role record);

	public Role selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Role record);

	public int updateByPrimaryKey(Role record);
    
    public List<Role> findAll();
}