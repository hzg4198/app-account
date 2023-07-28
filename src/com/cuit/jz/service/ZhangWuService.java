package com.cuit.jz.service;

import com.cuit.jz.dao.ZhangWuDao;
import com.cuit.jz.domain.ZhangWu;

import java.util.List;

public class ZhangWuService {
	ZhangWuDao zwd=new ZhangWuDao();

	public List<ZhangWu> findAll(){
		return zwd.findAll();
	}
}
