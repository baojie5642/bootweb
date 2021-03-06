package com.baojie.admin.service.impl;

import com.baojie.admin.dto.PageBean;
import com.baojie.admin.jpa.entity.Links;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.exception.ModifyException;
import com.baojie.admin.mapper.LinksMapper;
import com.baojie.admin.service.LinksService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/19
 */
@Service
@SuppressWarnings("all")
public class LinksServiceImpl implements LinksService {

    @Autowired
    private LinksMapper linksMapper;

    @Override
    public Long findAllCount() {
        return linksMapper.findAllCount();
    }

    @Override
    public List<Links> findAll() {
        return linksMapper.findAll();
    }

    @Override
    public PageBean findByPage(Links links, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page page = linksMapper.findByPage(links);
        return new PageBean(page.getTotal(), page.getResult());
    }

    @Override
    public Links findById(long id) {
        return linksMapper.findById(id);
    }

    @Override
    public void save(Links links) {
        try {
            int saveCount = linksMapper.save(links);
            if (saveCount <= 0) {
                throw new ModifyException(ModifyEnums.ERROR);
            }
        } catch (Exception e) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
    }

    @Override
    public void update(Links links) {
        try {
            int updateCount = linksMapper.update(links);
            if (updateCount <= 0) {
                throw new ModifyException(ModifyEnums.ERROR);
            }
        } catch (Exception e) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
    }

    @Override
    public void delete(Long... ids) {
        try {
            for (long id : ids){
                int deleteCount = linksMapper.delete(id);
                if (deleteCount <= 0) {
                    throw new ModifyException(ModifyEnums.ERROR);
                }
            }
        } catch (Exception e) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
    }
}
