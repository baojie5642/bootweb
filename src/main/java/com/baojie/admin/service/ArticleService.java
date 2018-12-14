package com.baojie.admin.service;

import com.baojie.admin.dto.ArticleArchives;
import com.baojie.admin.dto.PageBean;
import com.baojie.admin.jpa.entity.Article;

import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/16
 */
public interface ArticleService extends BaseService<Article> {

    /**
     * 分页查询（为博客前端服务）
     *
     * @param pageCode
     * @param pageSize
     * @return
     */
    PageBean findByPageForSite(Integer pageCode, Integer pageSize);

    /**
     * 根据分类名称查询文章数据
     *
     * @param category
     * @return
     */
    List<Article> findByCategory(String category);

    /**
     * 查询按照时间归档的整合数据，
     * 格式：[{"2018-01", [{article},{article}...]}, {"2018-02", [{article}, {article}...]}]
     *
     * @return
     */
    List<ArticleArchives> findArchives();

    /**
     * 模糊查询，为前端搜索框服务
     *
     * @param title
     * @return
     */
    List<Article> findFuzzyByTitle(String title);

    /**
     * 增加文章浏览量
     */
    void addEyeCount(long id);
}
