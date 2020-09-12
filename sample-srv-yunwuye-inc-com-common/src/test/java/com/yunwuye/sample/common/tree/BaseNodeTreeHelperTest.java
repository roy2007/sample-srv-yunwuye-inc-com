package com.yunwuye.sample.common.tree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.enums.BiddingDocFrameNodeType;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-下午2:48:26
 */
public class BaseNodeTreeHelperTest {

  List<BaseNode> treeList = new ArrayList<>();

  private void setProperty(BaseNode vo11, String name) {
    vo11.setCreator("wb491327");
    vo11.setFrameId(1000L);
    vo11.setName(name);
    vo11.setGmtCreate(new Date());
    vo11.setType(BiddingDocFrameNodeType.TITLE.getCode());
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    BaseNode vo11 = new BaseNode();
    setProperty(vo11, "一.投标涵");
    vo11.setChildren(new ArrayList<BaseNode>() { // 1-> 5/6/7, 7>8/9
      private static final long serialVersionUID = -7312674815838720997L;
      {
        BaseNode vo105 = new BaseNode();
        setProperty(vo105, "(一).投标涵描述1");
        vo105.setNodeRelationVOs(new ArrayList<NodeRelationVO>() {
          private static final long serialVersionUID = 1L;
          {
            NodeRelationVO relationVO = new NodeRelationVO();
            relationVO.setFrameId(1000L);
            relationVO.setDocId(1000L);
            relationVO.setName("投标技术分类说明文案");
            relationVO.setShowType("2");
            add(relationVO);
          }
          {
            NodeRelationVO relationVO = new NodeRelationVO();
            relationVO.setFrameId(1000L);
            relationVO.setDocId(1000L);
            relationVO.setName("投标资质");
            relationVO.setShowType("1");
            add(relationVO);
          }
        });
        add(vo105);
      }
      {
        BaseNode vo106 = new BaseNode();
        setProperty(vo106, "(二).投标涵描述2");
        add(vo106);
      }
      {
        BaseNode vo107 = new BaseNode();
        setProperty(vo107, "(三).投标涵描述3");
        vo107.setChildren(new ArrayList<BaseNode>() {
          private static final long serialVersionUID = 1L;
          {
            BaseNode vo108 = new BaseNode();
            setProperty(vo108, "1.投标涵A公司");
            add(vo108);
          }
          {
            BaseNode vo109 = new BaseNode();
            setProperty(vo109, "2.投标涵B公司");
            add(vo109);
          }
        });
        add(vo107);
      }
    });
    treeList.add(vo11);

    BaseNode vo12 = new BaseNode();
    setProperty(vo12, "二.投标功能");
    // 2-> 10/11/12m, 10> 13\14, 14-> 17/18 ,17-> 19\20,18->21\22, 12-> 15\16
    vo12.setChildren(new ArrayList<BaseNode>() {
      private static final long serialVersionUID = 6081510385777079518L;
      {
        BaseNode vo1010 = new BaseNode();
        setProperty(vo1010, "(一).投标功能概述");
        vo1010.setChildren(new ArrayList<BaseNode>() {
          private static final long serialVersionUID = 1L;
          {
            BaseNode vo108 = new BaseNode();
            setProperty(vo108, "1.投标金额");
            add(vo108);
          }
          {
            BaseNode vo109 = new BaseNode();
            setProperty(vo109, "2.投标物理组织");
            vo109.setChildren(new ArrayList<BaseNode>() {
              private static final long serialVersionUID = 12134L;
              {
                BaseNode vo17 = new BaseNode();
                setProperty(vo17, "2.1.云上科技股份有限公司");
                vo17.setChildren(new ArrayList<BaseNode>() {
                  private static final long serialVersionUID = 11212L;
                  {
                    BaseNode vo178 = new BaseNode();
                    setProperty(vo178, "2.1.1云上科技股份有限公司-信息化部");
                    add(vo178);
                  }
                  {
                    BaseNode vo179 = new BaseNode();
                    setProperty(vo179, "2.1.2云上科技股份有限公司-北京分公司");
                    add(vo179);
                  }
                });
                add(vo17);
              }
              {
                BaseNode vo18 = new BaseNode();
                setProperty(vo18, "2.2.云上2科技股份有限公司");
                vo18.setChildren(new ArrayList<BaseNode>() {
                  private static final long serialVersionUID = 1887781212L;
                  {
                    BaseNode vo178 = new BaseNode();
                    setProperty(vo178, "2.2.1 云上21111科技股份有限公司");
                    add(vo178);
                  }
                  {
                    BaseNode vo179 = new BaseNode();
                    setProperty(vo179, "2.2.2 云上222222科技股份有限公司");
                    add(vo179);
                  }
                });
                add(vo18);

              }
            });
            add(vo109);
          }
        });
        add(vo1010);
      }
      {
        BaseNode vo106 = new BaseNode();
        setProperty(vo106, "(二).投标功能2");
        add(vo106);
      }
      {
        BaseNode vo107 = new BaseNode();
        setProperty(vo107, "(三).投标功能3");
        vo107.setChildren(new ArrayList<BaseNode>() {
          private static final long serialVersionUID = 1L;
          {
            BaseNode vo108 = new BaseNode();
            setProperty(vo108, "3.1 投标功能2级节点");
            add(vo108);
          }
          {
            BaseNode vo109 = new BaseNode();
            setProperty(vo109, "3.2 投标功能2级节点");
            add(vo109);
          }
        });
        add(vo107);
      }
    });
    treeList.add(vo12);

    BaseNode vo13 = new BaseNode();
    setProperty(vo13, "三.投标技术架构");
    vo13.setChildren(new ArrayList<BaseNode>() {
      private static final long serialVersionUID = 60813385777079518L;
      {
        BaseNode vo109 = new BaseNode();
        setProperty(vo109, "4.1 投标技术架构2级节点");
        add(vo109);
      }
    });
    treeList.add(vo13);

    BaseNode vo14 = new BaseNode();
    setProperty(vo14, "四.投标产品环境");
    vo14.setChildren(new ArrayList<BaseNode>() {
      private static final long serialVersionUID = 60815103857770518L;
      {
        BaseNode vo109 = new BaseNode();
        setProperty(vo109, "4.1 投标产品环境2级节点");
        add(vo109);
      }
    });
    treeList.add(vo14);
  }

  @Test
  public void treeDataToMapTest() {
    Map<Long, BaseNode> map = BaseNodeTreeHelper.treeDataToMap(treeList);
    System.err.println(JSON.toJSONString(map.values(), Boolean.TRUE));
    Assert.assertEquals(25, map.size());
  }

  @Test
  public void flattenListToTreeTest() {
    Map<Long, BaseNode> map = BaseNodeTreeHelper.treeDataToMap(treeList);
    map.remove(0L);
    System.err.println(JSON.toJSONString(map.values(), Boolean.TRUE));
    List<BaseNode> flattenList = new ArrayList<>(map.values());
    List<BaseNode> tree = BaseNodeTreeHelper.flattenListToTree(flattenList);
    System.err.println(JSON.toJSONString(tree.get(0).getChildren(), Boolean.TRUE));
    Assert.assertTrue((tree.get(0).getChildren()).equals(treeList));
  }
}
