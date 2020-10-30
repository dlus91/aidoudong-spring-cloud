package com.aidoudong.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "t_product")
public class Product implements Serializable{
	private static final long serialVersionUID = -7650924080922036232L;
	
	@TableId(type = IdType.AUTO)
	private Long pid;
	@Size(min = 2,max = 10,message = "USERNAME_SIZE")
	private String pname;
	private String type;
	private BigDecimal price;
	private Date createTime;
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", type=" + type + ", price=" + price + ", createTime="
				+ createTime + "]";
	}

}