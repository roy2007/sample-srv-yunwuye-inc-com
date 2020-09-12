package com.yunwuye.sample.common.tree;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;
/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午10:40:02
 */
@ToString
@Data
public class BaseNode implements Serializable {

  /**
   * BaseNode.java -long
   */
  private static final long serialVersionUID = -8714920514865119463L;
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
  private Long selfNodeId;

  /**
   * 
   * 节点名称
   */
  private String name;

  /**
   * 
   * 父id,默认 0
   */
  private Long parentNodeId;

  /**
   * 
   * 节点全路径,用点分隔
   */
  private String path;

  /**
   * 
   * 同级节点对应tree中显示序号
   */
  private Byte serialNum;

  /**
   * 
   * 节点类型 1：标题, 2: 章节, 3：文档
   */
  private String type;

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
   * 子标题节点列表
   */
  private List<BaseNode> children;

  /**
   * 
   * 节点对应关系
   */
  private List<NodeRelationVO> nodeRelationVOs;

  /**
   * 
   * 如果节点类型是文档，则需要要：文档id、文档名称、 文档显示类型字段
   */
  private Long documentId;

  /**
   * 
   * 文档名称
   */
  private String documentName;

  /**
   * 
   * 文档显示类型 1：图片格式, 2: 文字格式
   */
  private String showType;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    BaseNode other = (BaseNode) obj;
    if (frameId == null) {
      if (other.frameId != null) {
        return false;
      }
    } else if (!frameId.equals(other.frameId)) {
      return false;
    }

    if (id == null) {
      if (other.id != null) {
        return false;
      }

    } else if (!id.equals(other.id)) {
      return false;
    }

    if (parentNodeId == null) {
      if (other.parentNodeId != null) {
        return false;
      }
    } else if (!parentNodeId.equals(other.parentNodeId)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((frameId == null) ? 0 : frameId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((parentNodeId == null) ? 0 : parentNodeId.hashCode());
    return result;
  }

}
