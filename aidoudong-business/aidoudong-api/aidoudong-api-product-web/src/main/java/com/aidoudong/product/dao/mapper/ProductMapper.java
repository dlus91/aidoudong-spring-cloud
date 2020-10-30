package com.aidoudong.product.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.product.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ProductMapper extends BaseMapper<Product>{

	Product selectProductById(@Param("pid") Long pid);
	
	List<Product> findAll();
	
}
