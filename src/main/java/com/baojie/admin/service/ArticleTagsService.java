package com.baojie.admin.service;

import com.baojie.admin.jpa.entity.ArticleTags;
import com.baojie.admin.jpa.entity.Tags;

import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/22
 */
public interface ArticleTagsService extends BaseService<ArticleTags> {

    List<Tags> findByArticleId(long articleId);
}
