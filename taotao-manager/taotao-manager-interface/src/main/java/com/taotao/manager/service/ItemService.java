package com.taotao.manager.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

public interface ItemService {
	EasyUIDataGridResult getItemList(Integer page, Integer size);
}