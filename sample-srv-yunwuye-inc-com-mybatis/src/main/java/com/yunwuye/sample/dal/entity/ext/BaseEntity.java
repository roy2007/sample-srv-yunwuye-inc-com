
package com.yunwuye.sample.dal.entity.ext;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Roy
 *
 * @date 2020年6月21日-下午10:32:54
 */
public class BaseEntity implements Serializable {

    /**
     * BaseEntity.java -long
     */
    private static final long serialVersionUID = -8695823101305648471L;
    /**
     * 唯一主键
     */
    private Long id;
    /**
     * 创建人
     */
    private String creatorId;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 修改人
     */
    private String modifierId;
    /**
     * 修改人名称
     */
    private String modifierName;
    /**
     * 数据创建时间
     */
    private Date gmtCreate;
    /**
     * 数据修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除标记， 0-未删除 ，等于主键id值-已删除
     */
    private Long isDeleted;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the creatorId
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     *            the creatorId to set
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName
     *            the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the modifierId
     */
    public String getModifierId() {
        return modifierId;
    }

    /**
     * @param modifierId
     *            the modifierId to set
     */
    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    /**
     * @return the modifierName
     */
    public String getModifierName() {
        return modifierName;
    }

    /**
     * @param modifierName
     *            the modifierName to set
     */
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    /**
     * @return the gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     *            the gmtCreate to set
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * @param gmtModified
     *            the gmtModified to set
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * @return the isDeleted
     */
    public Long getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted
     *            the isDeleted to set
     */
    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }
}
