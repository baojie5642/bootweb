package com.baojie.admin.controller;

import com.baojie.admin.dto.ModifyResult;
import com.baojie.admin.dto.PageBean;
import com.baojie.admin.dto.PasswordHelper;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.jpa.entity.Links;
import com.baojie.admin.jpa.entity.User;
import com.baojie.admin.pojo.AccountQuery;
import com.baojie.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final UserService usv;

    private final PasswordHelper pwdhper;

    @Autowired
    public AccountController(UserService usv, PasswordHelper pwdhper) {
        if (null == usv || null == pwdhper) {
            throw new NullPointerException();
        } else {
            this.usv = usv;
            this.pwdhper = pwdhper;
        }
    }
    @RequiresUser
    @ResponseBody
    @RequestMapping("/query")
    public ModifyResult queryByName(@RequestBody AccountQuery query) {
        if(null==query){
            throw new NullPointerException();
        }
        System.out.println(query);
        return new ModifyResult(true, ModifyEnums.SUCCESS);
    }
    @RequiresUser
    @ResponseBody
    @RequestMapping("/adduser")
    public ModifyResult add(@RequestBody User user) {
        if (null == user) {
            throw new NullPointerException();
        }
        String un = user.getUsername();
        String pwd = user.getPassword();
        if (StringUtils.isBlank(un) || StringUtils.isBlank(pwd)) {
            return new ModifyResult(false, ModifyEnums.INPUT_ERROR);
        }
        User ufd = usv.findByName(un);
        if (null != ufd) {
            return new ModifyResult(false, ModifyEnums.ADD_EXIST_ERROR);
        }
        String salt = user.getSalt();
        if (StringUtils.isBlank(salt)) {
            salt = pwdhper.randomSalt();
            user.setSalt(salt);
        }
        String secPwd = pwdhper.encryptPassword(pwd, salt);
        user.setPassword(secPwd);
        //user.setId(null);
        try {
            usv.save(user);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            return new ModifyResult(false, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/findByPage")
    public PageBean findByPage(User user,
            @RequestParam(value = "pageCode", required = false) Integer pageCode,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return usv.findByPage(user, pageCode, pageSize);
    }


    @RequiresUser
    @ResponseBody
    @RequestMapping("/findById")
    public User findById(@RequestParam("id") Long id) {
        return usv.findById(id);
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping("/save")
    public ModifyResult save(@RequestBody User user) {
        try {
            usv.save(user);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModifyResult(false, e.getMessage());
        }
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping("/update")
    public ModifyResult update(@RequestBody User user) {
        try {
            usv.update(user);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModifyResult(false, e.getMessage());
        }
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping("/delete")
    public ModifyResult delete(@RequestBody Long... ids) {
        try {
            usv.delete(ids);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModifyResult(false, e.getMessage());
        }
    }


}
