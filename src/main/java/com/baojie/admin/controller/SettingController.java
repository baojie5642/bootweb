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

}
