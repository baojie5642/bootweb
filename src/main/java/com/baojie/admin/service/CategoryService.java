package com.baojie.admin.service;

import com.baojie.admin.jpa.entity.Category;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
public interface CategoryService extends BaseService<Category> {

    Category findByName(String name);
}
