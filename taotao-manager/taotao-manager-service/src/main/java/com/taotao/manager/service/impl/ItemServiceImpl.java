package com.taotao.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.manager.mapper.TbItemMapper;
import com.taotao.manager.pojo.TbItem;
import com.taotao.manager.pojo.TbItemExample;
import com.taotao.manager.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer size) {
		if (page == null) page = 1;
		if (size == null) size = 30;
		// 设置分页
		PageHelper.startPage(page, size);
		// 查询数据
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo pageInfo = new PageInfo(list);
		// 包装数据
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		
		return result;
	}

}
