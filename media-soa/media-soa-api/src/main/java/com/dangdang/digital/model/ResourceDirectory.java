package com.dangdang.digital.model;

public class ResourceDirectory {
    private Integer dirId;

    private String name;

    private Integer parentId;

    private String code;

    private String path;
    
    private Integer isCdn;

    public Integer getDirId() {
        return dirId;
    }

    public void setDirId(Integer dirId) {
        this.dirId = dirId;
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
        this.path = path == null ? null : path.trim();
    }

	public Integer getIsCdn() {
		return isCdn;
	}

	public void setIsCdn(Integer isCdn) {
		this.isCdn = isCdn;
	}
    
}