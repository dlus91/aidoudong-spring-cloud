package com.aidoudong.entity.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "t_client_role")
public class ClientRole implements Serializable{
	private static final long serialVersionUID = -6278252395843221021L;
	
	@TableId(type = IdType.AUTO)
	private Long id;
    private String name;
    private String remark;
    private Date createDate;
    private Date updateDate;
    /**
     * 存储当前角色的权限资源对象集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<ClientPermission> perList = Lists.newArrayList();
    /**
     * 存储当前角色的权限资源ID集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<Long> perIds = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

	public List<ClientPermission> getPerList() {
		return perList;
	}

	public void setPerList(List<ClientPermission> perList) {
		this.perList = perList;
	}

	public List<Long> getPerIds() {
        if(CollectionUtils.isNotEmpty(perList)) {
            perIds = Lists.newArrayList();
            for(ClientPermission per : perList) {
                perIds.add(per.getId());
            }
        }
        return perIds;
    }

	public void setPerIds(List<Long> perIds) {
		this.perIds = perIds;
	}

	@Override
	public String toString() {
		return "ClientRole [id=" + id + ", name=" + name + ", remark=" + remark + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", perList=" + perList + ", perIds=" + perIds + "]";
	}


}