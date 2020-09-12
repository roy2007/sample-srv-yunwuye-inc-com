package com.yunwuye.sample.common.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.CommonConst;
import com.yunwuye.sample.common.enums.BiddingDocFrameNodeType;
import com.yunwuye.sample.common.enums.NodeType;
import com.yunwuye.sample.common.utils.ListUtil;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午11:08:42
 */
@Slf4j
public enum BaseNodeTreeHelper {
  /**
   * 枚举单例实例
   */
  INSTANCE;

  private static AtomicInteger AUTO_SELF_NODE_ID;

  /**
   * 将flatten列表（实用于数据库查询列表）转成Map<id,VO>
   * 
   * @param baseNodes
   * @return
   */
  private static Map<Long, BaseNode> flattenListDataToMap(List<BaseNode> baseNodes) {
    log.info("BaseNodeTreeHelper.flattenDataToMap the parameter vos:{}", JSON.toJSONString(baseNodes));
    Map<Long, BaseNode> map = new HashMap<>(16);
    /**
     * 设置默认树的根是0,根的父为空
     */
    BaseNode rootVO = new BaseNode();
    rootVO.setId(0L);
    rootVO.setSelfNodeId(0L);
    if (!CollectionUtils.isEmpty(baseNodes)) {
      map = baseNodes.stream().collect(
          Collectors.toMap(BaseNode::getSelfNodeId, vo -> vo, ((oldValue, newValue) -> oldValue)));
    }
    map.put(0L, rootVO);
    return map;
  }

  /**
   * 将投标文件框架结构Tree列表（前端提交列表）转成Map<id,VO>
   * 
   * @param baseNodes
   * @return
   */
  public static Map<Long, BaseNode> treeDataToMap(List<BaseNode> baseNodes) {
    log.info("BaseNodeTreeHelper.treeDataToMap the parameter vos:{}", JSON.toJSONString(baseNodes));
    AUTO_SELF_NODE_ID = new AtomicInteger(1);
    Map<Long, BaseNode> map = new HashMap<>();
    /**
     * 设置默认树的根是0，根的父为空
     */
    BaseNode rootVO = new BaseNode();
    rootVO.setId(0L);
    rootVO.setSelfNodeId(0L);
    int i = 1;
    if (!CollectionUtils.isEmpty(baseNodes)) {
      List<BaseNode> allVOs = new ArrayList<>();
      for (BaseNode vo : baseNodes) {
        vo.setSelfNodeId(Long.valueOf(AUTO_SELF_NODE_ID.get()));
        vo.setParentNodeId(rootVO.getSelfNodeId());
        vo.setPath((StringUtils.isNotBlank(rootVO.getPath())) ? rootVO.getPath() + CommonConst.DOT_SEPARATOR
            + rootVO.getSelfNodeId() : rootVO.getSelfNodeId() + "");
        vo.setType(NodeType.TITLE.getCode());
        vo.setSerialNum((byte) i);
        i++;
        AUTO_SELF_NODE_ID.incrementAndGet();
        treeToList(vo, allVOs);
      }
      map = flattenListDataToMap(allVOs);
    }
    map.put(0L, rootVO);
    return map;
  }

  /**
   * 将tree结构数据转成flattenList结构
   * 
   * @param list
   * @return
   */
  private static void treeToList(BaseNode vo, List<BaseNode> list) {
    if (list == null) {
      list = new ArrayList<>();
    }
    /**
     * 设置当前节点的必要数据
     */
    BaseNode willAddVO = new BaseNode();
    BeanUtils.copyProperties(vo, willAddVO);
    /**
     * 数据库中不需要保存子，所以将子设置为空list
     */
    willAddVO.setChildren(new ArrayList<BaseNode>());
    list.add(willAddVO);
    /**
     * 遍历递归子节点
     */
    if (!CollectionUtils.isEmpty(vo.getChildren())) {
      List<BaseNode> childrenVOs = ListUtil.copyProperties(vo.getChildren(), BaseNode.class);
      /**
       * 框架节点对应文档列表
       */
      List<NodeRelationVO> currenNodeDocs = new ArrayList<>();
      for (int i = 0; i < childrenVOs.size(); i++) {
        BaseNode indexChildrenVO = childrenVOs.get(i);
        /**
         * 如果节点类型是文档，直接转成NodeRelationVO 并添加到BaseNode对应文档列表中 nodeRelationVOs
         */
        if (indexChildrenVO == null) {
          continue;
        }
        if (indexChildrenVO.getType().equals(BiddingDocFrameNodeType.DOCUMENT.getCode())) {
          NodeRelationVO nodeRelationVO = convertNodeRelationVO(indexChildrenVO);
          if (nodeRelationVO != null) {
            nodeRelationVO.setSerialNum((byte) (i + 1));
            currenNodeDocs.add(nodeRelationVO);
          }
          continue;
        }

        indexChildrenVO.setSelfNodeId(Long.valueOf(AUTO_SELF_NODE_ID.get()));
        indexChildrenVO.setParentNodeId(vo.getSelfNodeId());
        indexChildrenVO.setPath((StringUtils.isNotBlank(vo.getPath())) ? vo.getPath() + CommonConst.DOT_SEPARATOR
            + vo.getSelfNodeId() : vo.getSelfNodeId() + "");
        indexChildrenVO.setType(NodeType.TITLE.getCode());
        indexChildrenVO.setSerialNum((byte) (i + 1));
        AUTO_SELF_NODE_ID.incrementAndGet();
        treeToList(indexChildrenVO, list);
      }

      if (!CollectionUtils.isEmpty(currenNodeDocs)) {
        willAddVO.setNodeRelationVOs(currenNodeDocs);
      }
    }
  }

  /**
   * 转换文档类型节点为节点对应的文档关系记录
   * 
   * @param baseNode
   * @return
   */
  private static NodeRelationVO convertNodeRelationVO(BaseNode baseNode) {
    if (baseNode == null) {
      return null;
    }
    NodeRelationVO nodeRelationVO = new NodeRelationVO();
    nodeRelationVO.setDocId(baseNode.getDocumentId());
    nodeRelationVO.setSerialNum(baseNode.getSerialNum());
    nodeRelationVO.setShowType(baseNode.getShowType());
    nodeRelationVO.setName(StringUtils.isNotBlank(baseNode.getDocumentName()) ? baseNode.getDocumentName() : baseNode
        .getName());
    return nodeRelationVO;
  }

  /**
   * 根据 flattenList列表 返回tree
   * 
   * @param baseNodes
   * @return
   */
  public static List<BaseNode> flattenListToTree(List<BaseNode> baseNodes) {
    return getTreeByParentId(baseNodes, 0L);
  }

  /**
   * 根据 parentId,获取指定列表中树
   * 
   * @param baseNodes
   * @param parentId
   * @return
   */
  public static List<BaseNode> getTreeByParentId(List<BaseNode> baseNodes, Long parentId) {
    List<BaseNode> vos = new ArrayList<>();
    if (CollectionUtils.isEmpty(baseNodes)) {
      return vos;
    }
    try {
      Map<Long, BaseNode> idWithVOMap = flattenListDataToMap(baseNodes);
      List<BaseNode> allVOs = new ArrayList<>(idWithVOMap.values());
      List<BaseNode> rootVOs = new ArrayList<>();
      rootVOs.add(idWithVOMap.get(parentId));

      for (BaseNode rootVO : rootVOs) {
        List<BaseNode> childListVOs = getChild(rootVO.getSelfNodeId(), allVOs);
        rootVO.setChildren(childListVOs);
      }
      return rootVOs;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return vos;
  }

  /**
   * 根据父子 查询子
   * 
   * @param parentId
   * @param allVOs
   * @return
   */
  private static List<BaseNode> getChild(Long parentId, List<BaseNode> allVOs) {
    List<BaseNode> childListVOs = new ArrayList<>();

    /**
     * 遍历所有list
     */
    for (BaseNode vo : allVOs) {
      /**
       * 如果自身ID与父ID相等，表示错误数据不必处理
       */
      boolean ignore = vo.getParentNodeId() == null
          || (vo.getSelfNodeId() != null && vo.getSelfNodeId().equals(vo.getParentNodeId()));
      if (ignore) {
        continue;
      }
      /**
       * 通过父找子
       */
      if (vo.getParentNodeId().equals(parentId)) {
        childListVOs.add(vo);
      }
    }
    sort(childListVOs);
    log.info("-----sort after---\r\n childList{}", JSON.toJSONString(childListVOs));

    for (BaseNode childvo : childListVOs) {
      if (childvo.getSelfNodeId() == null || childvo.getType().equals(BiddingDocFrameNodeType.DOCUMENT.getCode())) {
        continue;
      }
      childvo.setChildren(getChild(childvo.getSelfNodeId(), allVOs));
    }
    if (childListVOs.size() == 0) {
      return new ArrayList<BaseNode>();
    }
    return childListVOs;
  }

  /**
   * 针对子节点按照料 serialNum 排序
   * 
   * @param childListVOs
   */
  public static void sort(List<BaseNode> childListVOs) {
    Collections.sort(childListVOs, new Comparator<BaseNode>() {
      @Override
      public int compare(BaseNode o1, BaseNode o2) {
        log.info("-----sort, o1:{},o2:{}", JSON.toJSONString(o1), JSON.toJSONString(o2));
        try {
          if (o1.getSerialNum() < o2.getSerialNum()) {
            return -1;
          }
          if (o1.getSerialNum() > o2.getSerialNum()) {
            return 1;
          }
        } catch (Exception e) {
          log.error("[getChild] children order by error ,childListVOs:{} ", JSON.toJSONString(childListVOs));
        }
        return 0;
      }
    });
  }
}
