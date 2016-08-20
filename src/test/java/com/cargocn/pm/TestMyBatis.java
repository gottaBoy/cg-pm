package com.cargocn.pm;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMyBatis {
	private static Logger logger = LoggerFactory.getLogger(TestMyBatis.class);

	@Resource
	private IUserService userService = null;

	@Test
	public void test1() {
		User user = userService.findOne(Long.valueOf(1));
		logger.info(JSON.toJSONString(user));
	}
}