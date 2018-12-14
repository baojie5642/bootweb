package com.baojie.admin.service;

import com.baojie.admin.jpa.entity.Tags;

import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
public interface TagsService extends BaseService<Tags> {

    Tags findByName(String name);

    List<Tags> findByArticleTagsId(long articleId, long tagsId);
}
