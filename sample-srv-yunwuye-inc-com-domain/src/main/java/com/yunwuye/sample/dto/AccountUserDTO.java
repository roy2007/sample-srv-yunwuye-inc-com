package com.yunwuye.sample.dto;

import java.util.Date;
import java.util.List;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.common.base.dto.RoleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:18:48
 */
@ToString
@Data
@EqualsAndHashCode (callSuper = false)
public class AccountUserDTO extends BaseDTO{

    /**
     * AccountUserDTO.java -long
     */
    private static final long  serialVersionUID = -4431670554502814682L;
    private String             loginName;
    private String             password;
    private String             email;
    private String             mobile;
    private Long               empId;
    private String             empName;
    private Long               deptId;
    private Boolean            lockStatus;
    private Date               birthday;
    private Date               lastLoginTime;
    private String             gender;
    private String             isTab;
    private String             theme;
    private String             avatar;
    private String             description;
    private List<RoleDTO>      roles;
    private List<ACLDTO>       acls;
    private List<UserGroupDTO> userGroups;
}
