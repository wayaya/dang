package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 专题bean
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 下午6:34:46  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class SpecialTopic implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
    private Integer stId;

    /**
     * 类型Id
     */
    private Long stTypeId;

	/**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 渠道
     */
    private String channelType;
    
    /**
	 * 专题
	 */
    private String name;

    /**
     * 专题主图
     */
    private String picPath;

    /**
     * 状态
     */
    private Integer status;

    /**
     * banner所属分组ID
     */
    private Integer blockGroupId;
    
    /**
     * banner标示code
     */
    private String relationBlockCodes;

    /**
     * 排序
     */
    private String sort;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改时间
     */
    private Date lastModifiedDate;
    
    /**
     * 是否主页显示
     */
    private Integer showHomePage;
   
	/**
     * 关联栏目对象
     */
    private Column column;
    

	private String columnCode;
	
	public Integer getShowHomePage() {
			return showHomePage;
		}

	public void setShowHomePage(Integer showHomePage) {
			this.showHomePage = showHomePage;
		}
		
	public String getColumnCode() {
		if(null == columnCode){
			return this.column == null? null: this.column.getColumnCode();
		}
		return this.columnCode;
			
    }

	public void setColumnCode(String columnCode) {
			this.columnCode = columnCode;
	}
	/**
     * 专题说明
     */
    private String description;
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column ) {
		this.column = column;
	}

	
	
    public Long getStTypeId() {
		return stTypeId;
	}

	public void setStTypeId(Long stTypeId) {
		this.stTypeId = stTypeId;
	}
	
    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getBlockGroupId() {
		return blockGroupId;
	}

	public void setBlockGroupId(Integer blockGroupId) {
		this.blockGroupId = blockGroupId;
	}

	public String getRelationBlockCodes() {
        return relationBlockCodes;
    }

    public void setRelationBlockCodes(String relationBlockCodes) {
        this.relationBlockCodes = relationBlockCodes == null ? null : relationBlockCodes.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}