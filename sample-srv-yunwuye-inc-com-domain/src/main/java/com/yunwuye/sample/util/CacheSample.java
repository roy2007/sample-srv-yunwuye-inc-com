/**
 *
 */
package com.yunwuye.sample.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author Roy
 *
 */
public class CacheSample {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheSample.class);

    private static final String SOURCE_NAME_KEY = "source";
    //private static final String PARENT_NO_NAME_KEY = "parentNo";
   // private static Set<String> industryPaentNos = new HashSet<>();

//    private static MaterialCenterHelper materialCenterHelper;
//    private static MaterialConfigHelper materialConfigHelper;
//
//    @Autowired
//    public void setMaterialConfigHelper(MaterialConfigHelper materialConfigHelper) {
//        CacheMaterialCategoryInfo.materialConfigHelper = materialConfigHelper;
//    }
//
//    @Autowired
//    public void setMaterialCenterHelper(MaterialCenterHelper materialCenterHelper) {
//        CacheMaterialCategoryInfo.materialCenterHelper = materialCenterHelper;
//    }

    private volatile static boolean loadFinishFlag = false;
    // 同名称类目缓存容器
    private static Map<String, Set<String>> CACHE_SAME_CATEGORY_MAP = new ConcurrentHashMap<String, Set<String>>();
    // 所有类目编号对应名称缓存容器,后面Object可以改成基于范型
    private static Map<String, Object> CACHE_CATEGORY_MAP = new ConcurrentHashMap<String, Object>();

    //缓存容器
    private static Map<String, Object> CACHE_INDUSTRY_MAP = new ConcurrentHashMap<String, Object>();

    private CacheSample() {}

    private static class CacheManagerFactory {
        private static final CacheSample MATERIAL_CACHE = new CacheSample();
    }

    public static CacheSample getCacheManagerInstance() {
        return CacheManagerFactory.MATERIAL_CACHE;
    }

    @PostConstruct
    private void initData() {
        try {
            String categorySource = null;
            List<String> sameCategoryParentNos = new ArrayList<>();

//            if (!StringUtils.isEmpty(materialConfigHelper.getSameCategoryParentNoString())) {
//                sameCategoryParentNos =
//                    JSONObject.parseArray(materialConfigHelper.getSameCategoryParentNoString(), String.class);
//            }
//
//            if (!StringUtils.isEmpty(materialConfigHelper.getIndustryPaentNoString())) {
//                List<String> industryList =
//                    JSONObject.parseArray(materialConfigHelper.getIndustryPaentNoString(), String.class);
//                industryPaentNos.addAll(industryList);
//            }
//
//            if (!StringUtils.isEmpty(materialConfigHelper.getCategorySource())) {
//                categorySource = new String(materialConfigHelper.getCategorySource().getBytes("ISO-8859-1"), "gbk");
//            }

            LOGGER.info("[init data] same category cache, sameCategoryParentNos:{0},categorySource:{1}",
                JSON.toJSONString(sameCategoryParentNos), categorySource);
            if (!loadFinishFlag) {
                synchronized (CacheSample.class) {
                    if (!loadFinishFlag) {
                        // 初始化一级分类缓存值
                        try {
                            Map<String, Object> parameterMap = new HashMap<>();
                            parameterMap.put(SOURCE_NAME_KEY, categorySource);
//                            String metarialByEnum = materialCenterHelper.getMetarialByEnum(parameterMap,
//                                MaterialCenterApiUriEnum.QUERY_TYPE_CASCADE);
//                            if (!StringUtils.isEmpty(metarialByEnum)) {
//                                JSONArray allCateInfos = (JSONArray)JSONPath.read(metarialByEnum, "$.data");
//                                if (allCateInfos != null && !allCateInfos.isEmpty()) {
//                                    JSONArray fristCateInfos = (JSONArray)cateInfo.get("data");
//                                    if (fristCateInfos != null && !fristCateInfos.isEmpty()) {
//                                        for (Object obj : fristCateInfos) {
//                                            JSONObject jsonObj = (JSONObject)obj;
//                                            CategoryVO currentVO =
//                                                JSON.parseObject(jsonObj.toJSONString(), CategoryVO.class);
//                                            CACHE_CATEGORY_MAP.put(currentVO.getTypeNo(), currentVO);
//                                        }
//                                    }
//                                }
//                            }
                        } catch (Exception e) {
                            LOGGER.error("[init frist category vo] error,msg:{0}", e.getMessage());
                        }
                        // 初始化同名及逐级缓存
                        List<Object> allCategoryVOs = new ArrayList<Object>();
                        for (int i = 0; i < sameCategoryParentNos.size(); i++) {
                            //String parentNo = sameCategoryParentNos.get(i);
                           // String prefix = sameCategoryParentNos.get(i) + "$";
                           // queryNextLayerCategory(prefix, categorySource, parentNo, allCategoryVOs);
                            for (int k = 0; k < allCategoryVOs.size(); k++) {
                                String key = "";//allCategoryVOs.get(k).getTypeName();
                                String typeNo = "";//allCategoryVOs.get(k).getLastTypePath();
                                CACHE_SAME_CATEGORY_MAP.computeIfAbsent(key, x -> new HashSet<String>()).add(typeNo);
                            }
                        }
                        loadFinishFlag = Boolean.TRUE;
                        LOGGER.info( "[init data] same category code path:{0},all category :{1}",
                            JSON.toJSONString(CACHE_SAME_CATEGORY_MAP), JSON.toJSONString(CACHE_CATEGORY_MAP));
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error( "[init data] error,msg:{0}", e.getMessage());
            loadFinishFlag = false;
        }
    }

    /**
     * 递归查询所有层级的逐级目录
     *
     * @param sourceName
     * @param parentNo
     * @return
     */
//    private  List<CategoryVO> queryNextLayerCategory( String prefix,String sourceName, String parentNo,
//        List<CategoryVO> allCategoryVOs) {
//        Map<String, Object> parameterMap = new HashMap<String, Object>();
//        parameterMap.put(SOURCE_NAME_KEY, sourceName);
//        parameterMap.put(PARENT_NO_NAME_KEY, parentNo);
//        final String interfaceUrl = MaterialCenterApiUriEnum.QUERY_TYPE_PARENT.getCode();
//        final String jsonPath = MaterialCenterApiUriEnum.QUERY_TYPE_PARENT.getDesc()+"[0].data";
//          JSONArray cascadeInfos = (JSONArray)JSONPath.read(
//            materialCenterHelper.callTheMaterialHttpsService(parameterMap, interfaceUrl, Boolean.TRUE),
//            jsonPath);
//        //JSONArray cascadeInfos = materialCenterService.queryNextCascadeInfoFromMetarialCenter(parameterMap);
//        if(cascadeInfos == null) {
//            return null;
//        }
//
//        if(industryPaentNos.contains(parentNo)) {
//            if(!CACHE_INDUSTRY_MAP.containsKey(parentNo)) {
//                CACHE_INDUSTRY_MAP.put(parentNo, cascadeInfos);
//            }
//        }
//        @SuppressWarnings("unchecked")
//        List<JSONObject> categoryVOs = JSONObject.parseObject(cascadeInfos.toJSONString(), List.class);
//        if (CollectionUtils.isEmpty(categoryVOs)) {
//            return null;
//        }
//
//        if (!prefix.endsWith("$")) {
//            prefix += "$";
//        }
//        for (int i = 0; i < categoryVOs.size(); i++) {
//            CategoryVO currentVO = JSON.parseObject(categoryVOs.get(i).toJSONString(),CategoryVO.class);
//            currentVO.setLastTypePath(prefix+currentVO.getTypeNo());
//            allCategoryVOs.add(currentVO);
//            String newParentNo = currentVO.getTypeNo();
//            CACHE_CATEGORY_MAP.put(newParentNo, currentVO);
//            queryNextLayerCategory(currentVO.getLastTypePath(),sourceName, newParentNo, allCategoryVOs);
//        }
//        return allCategoryVOs;
//    }

    /**
     * 根据类目编号获取名称
     * @param typeNo
     * @return
     */
//    public CategoryVO getCategoryVOByNo(String typeNo) {
//        LoggerUtils.info(LOGGER, "[getTypeNameByNo] from cache get type name by typeNo:{0}", typeNo);
//        if (!loadFinishFlag) {
//            initData();
//        }
//        return CACHE_CATEGORY_MAP.getOrDefault(typeNo,new CategoryVO());
//    }

    /**
     * 根据同名类目获取所有同名类目的编号
     * @param sameName
     * @return
     */
    public Set<String> getSameTypeNoByNameData(String sameName) {
        LOGGER.info("[getSameTypeNoByNameData] from cache get typeNo by typeName:{0}", sameName);
        if (!loadFinishFlag) {
            initData();
        }
        return CACHE_SAME_CATEGORY_MAP.get(sameName);
    }



    /**
     * 从缓存中获取行业列表
     * @param parentNo
     * @return
     */
    public Object getIndustryJsonArray(String parentNo) {
        LOGGER.info("[getIndustryJsonArray] from cache get industry JsonArray by parentNo:{0}", parentNo);
        if (!loadFinishFlag) {
            initData();
        }
        return CACHE_INDUSTRY_MAP.get(parentNo);
    }

    /**
     * 根据行业编号获取对应主题树
     * @param parentNo
     * @return
     */
//    public List<CategoryVO> getTopicTree(String parentNo) {
//        LOGGER.info("[getTopicTree] from cache get topic tree by parentNo:{0}", parentNo);
//        if (!loadFinishFlag) {
//            initData();
//        }
//        try {
//            List<CategoryVO> allCategoryVOs = new ArrayList<>(CACHE_CATEGORY_MAP.values());
//            List<CategoryVO> rootVOs = new ArrayList<>();
//            /*for (CategoryVO vo : allCategoryVOs) {
//                if (vo.getTypeNo().equals(parentNo)) {
//                    rootVOs.add(vo);
//                }
//            }*/
//            rootVOs.add(CACHE_CATEGORY_MAP.get(parentNo));
//            for (CategoryVO rootVO : rootVOs) {
//                List<CategoryVO> childListVOs = getChild(rootVO.getTypeNo(), allCategoryVOs);
//                rootVO.setChildren(childListVOs);
//            }
//            return rootVOs;
//        } catch (Exception e) {
//            LOGGER.error("[getTopicTree]from cache get topic tree error, parentNo:{0},error:{1}", parentNo,
//                e.getMessage());
//            return new ArrayList();
//        }
//    }

    /**
     * 获取子主题分类信息
     * @param parentNo
     * @param allCategoryVOs
     * @return
     */
//    private List<CategoryVO> getChild(String parentNo, List<CategoryVO> allCategoryVOs) {
//        List<CategoryVO> childListVOs = new ArrayList<>();
//        for (CategoryVO vo : allCategoryVOs) {
//            if (vo.getParentNo().equals(parentNo)) {
//                childListVOs.add(vo);
//            }
//        }
//
//        for (CategoryVO childvo : childListVOs) {
//            childvo.setChildren(getChild(childvo.getTypeNo(), allCategoryVOs));
//        }
//        if (childListVOs.size() == 0) {
//            return new ArrayList<CategoryVO>();
//        }
//        return childListVOs;
//    }

}
