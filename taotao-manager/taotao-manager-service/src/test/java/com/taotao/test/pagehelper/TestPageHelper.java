package com.taotao.test.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.mapper.TbItemMapper;
import com.taotao.manager.pojo.TbItem;
import com.taotao.manager.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testHelper() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper bean = context.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 3);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = bean.selectByExample(example);
		List<TbItem> list2 = bean.selectByExample(example);
		
		PageInfo<TbItem> info = new PageInfo(list);
		System.out.println("总记录"+info.getTotal());
		for (TbItem tbitem : list) {
			System.out.println(tbitem);
		}
	}
}
