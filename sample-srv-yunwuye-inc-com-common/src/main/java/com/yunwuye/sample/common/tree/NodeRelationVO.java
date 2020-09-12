package com.yunwuye.sample.common.tree;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午10:45:20
 */
@ToString
@Data
public class NodeRelationVO implements Serializable {
  /**
   * NodeRelationVO.java -long
   */
  private static final long serialVersionUID = 3808112585229799680L;

  /**
   * 
   * 主键
   */
  private Long id;

  /**
   * 
   * 模板与框架表id
   */
  private Long frameId;

  /**
   * 
   * 节点自身id,程序自动生成
   */
  private Long nodeId;

  /**
   * 
   * 节点关联文 档
   */
  private Long docId;
  /**
   * 
   * 文档名称标题
   */
  private String name;

  /**
   * 
   * 创建人
   */
  private String creator;

  /**
   * 
   * 创建时间
   */
  private Date gmtCreate;

  /**
   * 
   * 修改者
   */
  private String modifier;

  /**
   * 
   * 修改时间
   */
  private Date gmtModified;

  /**
   * 
   * 是否逻辑删除 0: 不删除， 等 于id表示:删除
   */
  private Long isDeleted;

  /**
   * 
   * 同级节点对应tree中显示序号
   */
  private Byte serialNum;

  /**
   * 
   * 文档显示类型 1：图片格式, 2: 文字格式
   */
  private String showType;
}
