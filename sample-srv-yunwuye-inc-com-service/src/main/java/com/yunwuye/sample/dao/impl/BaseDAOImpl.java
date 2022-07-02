package com.yunwuye.sample.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yunwuye.sample.dal.entity.ext.BaseEntity;
import com.yunwuye.sample.dal.mapper.ext.BaseMapper;
import com.yunwuye.sample.dao.IBaseDAO;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.util.ListUtil;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午4:07:48
 */
public abstract class BaseDAOImpl implements IBaseDAO<BaseDTO>{

    private static final Logger logger = LoggerFactory.getLogger (BaseDAOImpl.class);

    /**
     * 获取具体实体Mapper对象
     * 
     * @return
     */
    protected abstract BaseMapper<? extends BaseEntity> getMapper ();

    @Override
    public int insert (BaseDTO dto) {
        BaseEntity entity = asEntity (dto);
        return getMapper ().insert (entity);
    }

    @Override
    public int update (BaseDTO dto) {
        return getMapper ().update (asEntity (dto));
    }

    @Override
    public int deleteByPrimaryKey (Long id) {
        return getMapper ().deleteByPrimaryKey (id);
    }

    @Override
    public int delete (BaseDTO dto) {
        return getMapper ().delete (asEntity (dto));
    }

    @Override
    public BaseDTO findByPrimaryKey (Long id) {
        BaseEntity entity = getMapper ().findByPrimaryKey (id);
        return this.asDTO (entity);
    }

    @Override
    public BaseDTO findByEntity (BaseDTO dto) {
        return this.asDTO (getMapper ().findByEntity (asEntity (dto)));
    }

    @Override
    public List<BaseDTO> findByListEntity (BaseDTO dto) {
        BaseEntity entity = asEntity (dto);
        Page<?> page = PageHelper.startPage (1, 2);
        System.out.println (entity.getClass ().getSimpleName () + "设置第一页两条数据!");
        List<? extends BaseEntity> entitys = getMapper ().findByListEntity (entity);
        List<BaseDTO> list = ListUtil.copyProperties (entitys, BaseDTO.class);
        System.out.println ("总共有:" + page.getTotal () + "条数据,实际返回:" + list.size () + "两条数据!");
        return list;
    }

    @Override
    public List<? extends BaseDTO> findAll () {
        List<BaseDTO> list = null;
        List<? extends BaseEntity> entitys = getMapper ().findAll ();
        list = ListUtil.copyProperties (entitys, BaseDTO.class);
        return list;
    }

    @Override
    public Object findByObject (Object obj) {
        Object result = null;
        try {
            result = getMapper ().findByObject (obj);
        } catch (Exception e) {
            logger.error ("查询" + obj + "失败!原因是:", e);
        }
        return result;
    }

//    /**
//     * 通过常规反射形式 DTO对象转换为实体对象。如命名不规范或其他原因导致失败。
//     * 
//     * @param t
//     *          源转换的对象
//     * @param e
//     *          目标转换的对象
//     */
//    public static <T, E> void transalte (T t, E e) {
//        Method[] tms = t.getClass ().getDeclaredMethods ();
//        Method[] tes = e.getClass ().getDeclaredMethods ();
//        for (Method m1 : tms) {
//            if (m1.getName ().startsWith ("get")) {
//                String mNameSubfix = m1.getName ().substring (3);
//                String forName = "set" + mNameSubfix;
//                for (Method m2 : tes) {
//                    if (m2.getName ().equals (forName)) {
//                        // 如果类型一致，或者m2的参数类型是m1的返回类型的父类或接口
//                        boolean canContinue = m2.getParameterTypes ()[0].isAssignableFrom (m1.getReturnType ());
//                        if (canContinue) {
//                            try {
//                                m2.invoke (e, m1.invoke (t));
//                                break;
//                            } catch (Exception ex) {
//                                logger.debug ("DTO to Entity fail!");
//                                ex.printStackTrace ();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    public BaseEntity asEntity (BaseDTO dto) {
        BaseEntity entity = new BaseEntity ();
        BeanUtils.copyProperties (entity, dto);
        return entity;
    }

    @Override
    public BaseDTO asDTO (BaseEntity entity) {
        BaseDTO dto = new BaseDTO ();
        BeanUtils.copyProperties (entity, dto);
        return dto;
    }
}
