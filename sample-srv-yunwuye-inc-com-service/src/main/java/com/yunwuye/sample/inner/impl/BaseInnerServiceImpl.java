package com.yunwuye.sample.inner.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yunwuye.sample.dao.IBaseDAO;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.inner.IBaseInnerService;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午4:07:48
 */
public abstract class BaseInnerServiceImpl implements IBaseInnerService<BaseDTO>{

    private static final Logger logger = LoggerFactory.getLogger (BaseInnerServiceImpl.class);

    /**
     * 获取具体实体IBaseDAO对象
     * 
     * @return
     */
    protected abstract IBaseDAO<? extends BaseDTO> getDAO ();

    @Override
    public int insert (BaseDTO dto) {
        return getDAO ().insert (dto);
    }

    @Override
    public int update (BaseDTO dto) {
        return getDAO ().update (dto);
    }

    @Override
    public int deleteByPrimaryKey (Long id) {
        return getDAO ().deleteByPrimaryKey (id);
    }

    @Override
    public int delete (BaseDTO dto) {
        return getDAO ().delete (dto);
    }

    @Override
    public BaseDTO findByPrimaryKey (Long id) {
        BaseDTO dto = getDAO ().findByPrimaryKey (id);
        return (dto);
    }

    @Override
    public BaseDTO findByEntity (BaseDTO dto) {
        return getDAO ().findByEntity (dto);
    }

    @Override
    public List<? extends BaseDTO> findByList (BaseDTO dto) {
        Page<?> page = PageHelper.startPage (1, 2);
        System.out.println (dto.getClass ().getSimpleName () + "设置第一页两条数据!");
        List<? extends BaseDTO> dtos = getDAO ().findByListEntity (dto);
        System.out.println ("总共有:" + page.getTotal () + "条数据,实际返回:" + dtos.size () + "两条数据!");
        return dtos;
    }

    @Override
    public List<? extends BaseDTO> findAll () {
        List<? extends BaseDTO> baseDTOs = getDAO ().findAll ();
        return baseDTOs;
    }

    @Override
    public Object findByObject (Object obj) {
        Object result = null;
        try {
            result = getDAO ().findByObject (obj);
        } catch (Exception e) {
            logger.error ("查询" + obj + "失败!原因是:", e);
        }
        return result;
    }

}
