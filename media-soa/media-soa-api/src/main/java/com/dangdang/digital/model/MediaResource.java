package com.dangdang.digital.model;

import java.util.Date;

public class MediaResource {
    private Long fileId;

    private String cdnPath;

    private String fileName;

    private Long fileSize;

    private Date uploadDate;
    
    private Integer dirId;
    
    private String dirPath;
    
    private String uploader;
    
    private Integer isCdn;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getCdnPath() {
        return cdnPath;
    }

    public void setCdnPath(String cdnPath) {
        this.cdnPath = cdnPath == null ? null : cdnPath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

	public Integer getDirId() {
		return dirId;
	}

	public void setDirId(Integer dirId) {
		this.dirId = dirId;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public Integer getIsCdn() {
		return isCdn;
	}

	public void setIsCdn(Integer isCdn) {
		this.isCdn = isCdn;
	}
	
}