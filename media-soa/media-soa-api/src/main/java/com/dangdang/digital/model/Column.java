package com.dangdang.digital.model;

import java.io.Serializable;


public class Column  implements Serializable{
		/** 书藉栏目类型,默认 */
		public static final Integer TYPE_MEDIA=0;
		
		/** 频道栏目类型  */
		public static final Integer TYPE_CHANNEL=1;
		
		private static final long serialVersionUID = 1L;
		private Integer columnId;
	    
		private String columnCode;//channel_code
	    
	    private String name;

	    private Integer parentId;

	    private String startDate;

	    private String endDate;

	    private Boolean isactiverForever;

	    private String creator;

	    private String createDate;

	    private String lastChangeDate;

	    private String modifer;
	    
	    private String path;
	    
	    /** 栏目类型,0:书的栏目,1:频道栏目 */
	    private Integer type = TYPE_MEDIA;
	    
	    
		private String channel;
		
		
		private String code;
	    public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		
	    
	    public String getCode() {
			return code;
		}
	    
		public void setCode(String code) {
			this.code = code == null ? null : code.trim();
		}
		public String getPath() {
			return path;
		}
		
		public void setPath(String path) {
			this.path = path ==  null ? null : path.trim();
		}

		/**
	     * 栏目图标
	     */
	    private String icon;
	    

		//是否显示小喇叭
	    private Boolean isShowHorn;
	    
	    //小喇叭显示内容
	    private String tips;
	    
	    //栏目描述
	    private String descs;
	    
	    public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel == null ? null :channel.trim() ;
		}
		
	    public Boolean getIsShowHorn() {
			return isShowHorn;
		}

		public void setIsShowHorn(Boolean isShowHorn) {
			this.isShowHorn = isShowHorn;
		}

		public String getTips() {
			return tips;
		}

		public void setTips(String tips) {
			this.tips = tips == null ? null:tips.trim();
		}

		public String getDescs() {
			return descs;
		}

		public void setDescs(String descs) {
			this.descs = descs == null ? null:descs.trim();
		}


	    public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon == null ?  null:icon.trim();
		}

		public Integer getColumnId() {
	        return columnId;
	    }

	    public void setColumnId(Integer columnId) {
	        this.columnId = columnId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public Integer getParentId() {
	        return parentId;
	    }

	    public void setParentId(Integer parentId) {
	        this.parentId = parentId;
	    }

	    public String getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(String startDate) {
	        this.startDate = startDate == null ? null:startDate.trim().isEmpty()?null:startDate.trim();
	    }

	    public String getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(String endDate) {
		this.endDate = endDate == null ? null : endDate.trim().isEmpty()?null:endDate.trim();
	    }

	    public Boolean getIsactiverForever() {
	        return isactiverForever;
	    }

	    public void setIsactiverForever(Boolean isactiverForever) {
	        this.isactiverForever = isactiverForever;
	    }

	    public String getCreator() {
	        return creator;
	    }

	    public void setCreator(String creator) {
	        this.creator = creator == null ? null : creator.trim();
	    }

	    public String getCreateDate() {
	        return createDate;
	    }

	    public void setCreateDate(String createDate) {
	        this.createDate = createDate;
	    }

	    public String getLastChangeDate() {
	        return lastChangeDate;
	    }

	    public void setLastChangeDate(String lastChangeDate) {
	        this.lastChangeDate = lastChangeDate;
	    }

	    public String getModifer() {
	        return modifer;
	    }

	    public void setModifer(String modifer) {
	        this.modifer = modifer == null ? null : modifer.trim();
	    }
	    
	    public String getColumnCode() {
			return columnCode;
		}

		public void setColumnCode(String columnCode) {
			this.columnCode = columnCode == null? null : columnCode.trim();
		}

}