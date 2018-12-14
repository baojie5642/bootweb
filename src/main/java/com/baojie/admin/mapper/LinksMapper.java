package com.baojie.admin.mapper;

import com.baojie.admin.jpa.entity.Links;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
@Mapper
public interface LinksMapper {

    List<Links> findAll();

    Page findByPage(Links links);

    Links findById(long id);

    int save(Links links);

    int update(Links links);

    int delete(long id);

    Long findAllCount();
}
