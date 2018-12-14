package com.baojie.admin.jpa.repos;

import com.baojie.admin.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

@Transactional
public interface UserRepo extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

}
