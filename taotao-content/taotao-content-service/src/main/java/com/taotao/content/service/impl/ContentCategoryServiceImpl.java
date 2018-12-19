package com.taotao.content.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.manager.mapper.TbContentCategoryMapper;
import com.taotao.manager.pojo.TbContentCategory;
import com.taotao.manager.pojo.TbContentCategoryExample;
import com.taotao.manager.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 设置条件
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		// 查询
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example);
		
		// 转成EsayUITreeNode数组
		ArrayList<EasyUITreeNode> arrayList = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory cate : selectByExample) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(cate.getId());
			easyUITreeNode.setName(cate.getName());
			easyUITreeNode.setState(cate.getIsParent() ? "closed" : "open");
			arrayList.add(easyUITreeNode);
		}
		
		return arrayList;
	}

}
