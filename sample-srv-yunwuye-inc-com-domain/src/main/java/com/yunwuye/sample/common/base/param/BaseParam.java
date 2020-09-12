package com.yunwuye.sample.common.base.param;

import java.util.Date;

import com.yunwuye.sample.common.ToString;
import com.yunwuye.sample.common.base.annotation.FieldCNName;

/**
 *
 * @author Roy
 *
 */
public class BaseParam extends ToString {
    private static final long serialVersionUID = -6084448362419962582L;

    /**
     * 唯一主键
     */
    @FieldCNName(value = "id", comment = "记录唯一主键id")
    private Long id;
    /**
     * 创建人
     */
    @FieldCNName(value = "创建人id", comment = "记录创建人id，登录人员对应用户表id")
    private String creatorId;
    /**
     * 创建人名称
     */
    @FieldCNName(value = "创建人", comment = "记录创建人，登录人员用户表中名称")
    private String creatorName;
    /**
     * 修改人
     */
    @FieldCNName(value = "修改人id", comment = "记录修改人id，登录人员用户表中名称")
    private String modifierId;
    /**
     * 修改人名称
     */
    @FieldCNName(value = "修改人", comment = "记录修改人，登录人员用户表中名称")
    private String modifierName;
    /**
     * 数据创建时间
     */
    @FieldCNName(value = "创建 时间", comment = "记录创建时间")
    private Date gmtCreate;
    /**
     * 数据修改时间
     */
    @FieldCNName(value = "修改 时间", comment = "记录修改时间")
    private Date gmtModified;
    /**
     * 是否删除标记， 0-未删除 ，如果删除将等于存储id值
     */
    @FieldCNName(value = "是否删除", comment = "是否删除标记")
    private Long isDeleted;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
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
     * @param creatorId the creatorId to set
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
     * @param creatorName the creatorName to set
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
     * @param modifierId the modifierId to set
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
     * @param modifierName the modifierName to set
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
     * @param gmtCreate the gmtCreate to set
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
     * @param gmtModified the gmtModified to set
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
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }
}