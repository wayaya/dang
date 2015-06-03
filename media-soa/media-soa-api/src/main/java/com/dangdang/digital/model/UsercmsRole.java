package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class UsercmsRole implements Serializable{

	private static final long serialVersionUID = -6405477538531504710L;

	private Long usercmsRoleId;

    private Long usercmsId;

    private Long roleId;

    private String roleName;

    private String creator;

    private Date creationDate;

    public Long getUsercmsRoleId() {
        return usercmsRoleId;
    }

    public void setUsercmsRoleId(Long usercmsRoleId) {
        this.usercmsRoleId = usercmsRoleId;
    }

    public Long getUsercmsId() {
        return usercmsId;
    }

    public void setUsercmsId(Long usercmsId) {
        this.usercmsId = usercmsId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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
   		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
   		UsercmsRole other = (UsercmsRole) obj;
   		if (roleId == null) {
   			if (other.roleId != null)
   				return false;
   		} else if (!roleId.equals(other.roleId))
   			return false;
   		if (usercmsId == null) {
   			if (other.usercmsId != null)
   				return false;
   		} else if (!usercmsId.equals(other.usercmsId))
   			return false;
   		return true;
   	}
}