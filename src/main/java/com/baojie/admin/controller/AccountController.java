package com.baojie.admin.controller;

import com.baojie.admin.dto.ModifyResult;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.pojo.AccountQuery;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

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


}
