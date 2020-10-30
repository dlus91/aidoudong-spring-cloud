package com.aidoudong.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class SysPermission implements Serializable{
	private static final long serialVersionUID = -7650924080922036232L;
	
	@TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 父资源id,给它初始值 0
     * 新增和修改页面上默认的父资源id
     */
    private Long parentId = 0L;
    private String name;
    private String code;
    private String url;
    /**
     * 菜单：1，按钮：2
     */
    private Integer type;
    private String icon;
    private String remark;
    private Date createDate;
    private Date updateDate;
    
    /**
     * 用于新增和修改页面上默认的根菜单名称
     */
    @TableField(exist = false)
    private String parentName = "根菜单";
    
    /**
     * 所有子权限对象集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<SysPermission> children;

    /**
     * 所有子权限 URL 集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<String> childrenUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<SysPermission> getChildren() {
		return children;
	}

	public void setChildren(List<SysPermission> children) {
		this.children = children;
	}

	public List<String> getChildrenUrl() {
		return childrenUrl;
	}

	public void setChildrenUrl(List<String> childrenUrl) {
		this.childrenUrl = childrenUrl;
	}

	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", parentId=" + parentId + ", name=" + name + ", code=" + code + ", url=" + url + ", type=" + type + ", icon=" + icon + ", remark="
				+ remark + ", createDate=" + createDate + ", updateDate=" + updateDate + ", parentName=" + parentName + ", children=" + children + ", childrenUrl=" + childrenUrl
				+ "]";
	}
}