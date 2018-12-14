package com.baojie.admin.service;

import com.baojie.admin.dto.PageBean;
import com.baojie.admin.jpa.entity.User;
import com.baojie.admin.jpa.executor.UserExec;
import com.baojie.admin.jpa.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserExec exec;

    @Autowired
    public UserService(UserRepo repo) {
        if (null == repo) {
            throw new NullPointerException();
        } else {
            this.exec = new UserExec(repo);
        }
    }

    public PageBean findByPage(User user, int pageCode, int pageSize) {
        return exec.findByPage(user, pageCode, pageSize);
    }

    public User findById(long id) {
        return exec.findById(id);
    }

    public User save(User user) {
        return exec.save(user);
    }

    public User update(User user) {
        return exec.update(user);
    }

    public boolean check(User user, boolean ec) {
        return exec.check(user, ec);
    }

    public void delete(Long... ids) {
        exec.delete(ids);
    }

    public User findByName(String un) {
        return exec.findByName(un);
    }

}
