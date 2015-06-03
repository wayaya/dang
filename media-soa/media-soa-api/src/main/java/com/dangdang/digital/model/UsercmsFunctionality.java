package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class UsercmsFunctionality implements Serializable{

	private static final long serialVersionUID = -5504263495254496902L;

	private Long usercmsFunctionalityId;

    private Long usercmsId;

    private Long funcId;

    private String creator;

    private Date creationDate;

    public Long getUsercmsFunctionalityId() {
        return usercmsFunctionalityId;
    }

    public void setUsercmsFunctionalityId(Long usercmsFunctionalityId) {
        this.usercmsFunctionalityId = usercmsFunctionalityId;
    }

    public Long getUsercmsId() {
        return usercmsId;
    }

    public void setUsercmsId(Long usercmsId) {
        this.usercmsId = usercmsId;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		result = prime * result
				+ ((usercmsId == null) ? 0 : usercmsId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsercmsFunctionality other = (UsercmsFunctionality) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		if (usercmsId == null) {
			if (other.usercmsId != null)
				return false;
		} else if (!usercmsId.equals(other.usercmsId))
			return false;
		return true;
	}
}