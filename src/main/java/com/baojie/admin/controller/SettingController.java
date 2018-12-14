package com.baojie.admin.controller;

import com.baojie.admin.dto.ModifyResult;
import com.baojie.admin.dto.PasswordHelper;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.jpa.entity.User;
import com.baojie.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/setting")
public class SettingController {

    private final UserService usv;

    private final PasswordHelper pwdhper;

    @Autowired
    public SettingController(UserService usv, PasswordHelper pwdhper) {
        if (null == usv || null == pwdhper) {
            throw new NullPointerException();
        } else {
            this.usv = usv;
            this.pwdhper = pwdhper;
        }
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

}
