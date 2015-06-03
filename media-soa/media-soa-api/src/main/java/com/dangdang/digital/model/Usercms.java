package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class Usercms implements Serializable{

	private static final long serialVersionUID = 5129743010377915289L;
	/**
	 * id
	 */
    private Long usercmsId;
    /**
	 * login用的名字
	 */
    private String loginName;
    /**
	 * 加密后的password
	 */
    private String password;
    /**
   	 * 名字
   	 */
    private String name;

    private String email;
    /**
   	 * 若是cp,那么 关联cpid
   	 */
    private Long cpid;
    /**
	 * cp 名称
	 */
    private String cpname;
    /**
	 * 部门
	 */
    private String department;

    private String creator;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;
    /**
   	 * 0 禁用 ；3已禁用，没有重置密码; 1启用  2已启用但没有重置密码   
   	 */
    private Byte status;
    /**
   	 * 0 管理员 1受角色控制的其他用户， 非管理员不能创建管理员
   	 */
    private Byte previledge;
    /**
   	 * 0 下线 1在线 ，可用于限制多点登录
   	 */
    private Byte onlineStatus;
    /**
     * 加密此用户密码用的密钥
     */
    private String passwordEncryptionKey;

    public Long getUsercmsId() {
        return usercmsId;
    }

    public void setUsercmsId(Long usercmsId) {
        this.usercmsId = usercmsId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getCpid() {
        return cpid;
    }

    public void setCpid(Long cpid) {
        this.cpid = cpid;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname == null ? null : cpname.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getPreviledge() {
        return previledge;
    }

    public void setPreviledge(Byte previledge) {
        this.previledge = previledge;
    }

    public Byte getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Byte onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getPasswordEncryptionKey() {
        return passwordEncryptionKey;
    }

    public void setPasswordEncryptionKey(String passwordEncryptionKey) {
        this.passwordEncryptionKey = passwordEncryptionKey == null ? null : passwordEncryptionKey.trim();
    }
}