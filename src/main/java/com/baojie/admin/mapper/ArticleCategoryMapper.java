package com.baojie.admin.mapper;

import com.baojie.admin.jpa.entity.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresRoles;

@Mapper
public interface ArticleCategoryMapper {

    @RequiresRoles("admin")
    int save(ArticleCategory articleCategory);

    boolean exists(@Param("articleId") long articleId, @Param("categoryId") long categoryId);

    int delete(long id);
}
