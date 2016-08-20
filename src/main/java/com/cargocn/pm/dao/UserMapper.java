package com.cargocn.pm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cargocn.pm.bean.User;

public interface UserMapper {
	public int deleteByPrimaryKey(Long id);

	public int insert(User record);

	public int insertSelective(User record);

	public User selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(User record);

	public int updateByPrimaryKey(User record);

	public List<User> findAll();

	public User findByUsername(@Param("username") String username);
}