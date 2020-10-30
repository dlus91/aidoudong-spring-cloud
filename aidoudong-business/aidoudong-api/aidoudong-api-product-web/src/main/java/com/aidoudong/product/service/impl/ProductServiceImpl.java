package com.aidoudong.product.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aidoudong.product.dao.mapper.ProductMapper;
import com.aidoudong.product.entity.Product;
import com.aidoudong.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

	@Override
//	@Cacheable(value="products", key="'product:id'.concat(#pid)")
	public Product findById(Long pid) {
		if(pid == null) return null;
		Product product = baseMapper.selectProductById(pid);
		return product;
	}
	
	@Override
//	@Cacheable(value="products", key="'listproduct:all'")
	public List<Product> findAll() {
		return baseMapper.findAll();
	}
	
	@Override
//	@CachePut(value="products", key="'product:id'.concat(#product.pid)", unless="#result == null")
	public Product update(Product product) {
		baseMapper.updateById(product);
		return baseMapper.selectProductById(product.getPid());
	}

}
