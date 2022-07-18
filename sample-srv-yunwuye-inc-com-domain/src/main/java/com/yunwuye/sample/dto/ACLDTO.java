package com.yunwuye.sample.dto;

import com.yunwuye.sample.common.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Roy
 *
 * @date 2022年7月10日-下午12:54:34
 */
@ToString
@Data
@EqualsAndHashCode (callSuper = false)
public class ACLDTO extends BaseDTO{

    /**
     * ACLDTO.java -long
     */
    private static final long serialVersionUID = 28971980407513893L;
    /**
     * 标识代码 
     */
    private String            identificationCode;
    /**
     * 名称中文 
     */
    private String            cnName;
    /**
     * 名称英文 
     */
    private String            enName;
    /**
     * 风险级别
     */
    private Integer           riskLevel;
    /**
     * 归属人id
     */
    private Long              belongId;
    /**
     * 所属应用id
     */
    private Long              appId;
    /**
     * 权限说明
     */
    private String            description;
    /**
     * 是否永久权限0-永久，1-固定期限，授权权时需要设置 时间段
     */
    private Integer           permanentlyValid;
}