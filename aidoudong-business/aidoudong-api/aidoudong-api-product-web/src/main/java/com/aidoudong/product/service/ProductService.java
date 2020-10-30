package com.aidoudong.product.service;

import java.util.List;

import com.aidoudong.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductService extends IService<Product> {
	
	/**
	 * 通过用户id查询所拥有的权限
	 * @param userId
	 * @return
	 */
	public Product findById(Long pid);
	
	public List<Product> findAll();
	
	public Product update(Product product);
	
}
