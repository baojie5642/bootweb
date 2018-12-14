package com.baojie.admin.jpa.executor;

import com.baojie.admin.dto.PageBean;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.exception.ModifyException;
import com.baojie.admin.jpa.entity.User;
import com.baojie.admin.jpa.repos.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

public class UserExec {

    private static final Logger log = LoggerFactory.getLogger(UserExec.class);

    private final UserRepo repo;

    public UserExec(UserRepo repo) {
        this.repo = repo;
    }

    public PageBean findByPage(User user, int pageCode, int pageSize) {
        if (null == user) {
            return new PageBean(0, new ArrayList(0));
        }
        if (pageCode < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("pageCode=" + pageCode + ", pageSize=" + pageSize);
        }
        PageRequest range = PageRequest.of(pageCode, pageSize);
        Page page = null;
        try {
            page = repo.findAll(range);
        } catch (Throwable te) {
            log.error(te.toString() + ", user=" + user, te);
        }
        if (null != page) {
            return new PageBean(page.getSize(), page.getContent());
        } else {
            return new PageBean(0, new ArrayList(0));
        }
    }

    public User findById(long id) {
        Optional<User> optional = repo.findById(id);
        return optional.orElse(null);
    }

    public User save(User user) {
        if (!check(user, true)) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
        User saved = store(user);
        if (null == saved) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
        return saved;
    }

    public User update(User user) {
        if (!check(user, false)) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
        User update = store(user);
        if (null == update) {
            throw new ModifyException(ModifyEnums.INNER_ERROR);
        }
        return update;
    }

    private final User store(User user) {
        try {
            return repo.save(user);
        } catch (Throwable e) {
            log.error(e.toString() + ", user=" + user, e);
            throw new ModifyException(ModifyEnums.ERROR);
        }
    }

    public final boolean check(User user, boolean ec) {
        if (null == user) {
            throw new NullPointerException("user null");
        }
        String na = user.getUsername();
        String pwd = user.getPassword();
        String salt = user.getSalt();
        if (StringUtils.isBlank(na) || StringUtils.isBlank(pwd)) {
            return false;
        }
        if (StringUtils.isBlank(salt)) {
            return false;
        }
        if (ec) {
            String email = user.getEmail();
            if (StringUtils.isBlank(email)) {
                return false;
            }
        }
        return true;
    }

    public void delete(Long... ids) {
        if (null == ids) {
            return;
        }
        if (ids.length <= 0) {
            return;
        }
        for (long id : ids) {
            try {
                repo.deleteById(id);
            } catch (Throwable te) {
                log.error(te.toString(), te);
                throw new ModifyException(ModifyEnums.INNER_ERROR);
            }
        }
    }

    public User findByName(String un) {
        if (null == un) {
            throw new NullPointerException();
        }
        try {
            return repo.findByUsername(un);
        } catch (Throwable te) {
            log.error(te.toString() + ", name=" + un, te);
        }
        return null;
    }

}
