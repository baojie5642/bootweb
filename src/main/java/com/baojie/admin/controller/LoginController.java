package com.baojie.admin.controller;

import com.baojie.admin.dto.ModifyResult;
import com.baojie.admin.enums.ModifyEnums;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private static final String REMB_ME = "true";

    @Autowired
    public LoginController() {

    }

    /**
     * 跳转到后台首页
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    /**
     * 跳转到登录页
     */
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping("/admin/login")
    public ModifyResult login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember", required = false) String remember) {
        if (legalParams(username, password)) {
            return check(username, password, remember);
        } else {
            return new ModifyResult(false, ModifyEnums.INPUT_ERROR);
        }
    }

    private boolean legalParams(String username, String password) {
        if (null == username || null == password) {
            return false;
        } else {
            return true;
        }
    }

    private ModifyResult check(String name, String pwd, String remb) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = token(name, pwd, remb);
        return userIn(subject, token);
    }

    private UsernamePasswordToken token(String name, String pwd, String remb) {
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
        remember(token, remb);
        return token;
    }

    private void remember(UsernamePasswordToken token, String remb) {
        if (null == remb) {
            token.setRememberMe(false);
        } else {
            if (REMB_ME.equals(remb)) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
        }
    }

    private ModifyResult userIn(Subject subject, UsernamePasswordToken token) {
        try {
            subject.login(token);
            System.out.println("是否登录：" + subject.isAuthenticated());
            return new ModifyResult(true, ModifyEnums.LOGIN_SUCCESS);
        } catch (UnknownAccountException e) {
            log.error(e.toString(), e);
            return new ModifyResult(false, ModifyEnums.LOGIN_UNKNOWN);
        } catch (IncorrectCredentialsException e) {
            log.error(e.toString(), e);
            return new ModifyResult(false, ModifyEnums.LOGIN_ERROR);
        } catch (Throwable e) {
            log.error(e.toString(), e);
            return new ModifyResult(false, ModifyEnums.INNER_ERROR);
        }
    }
}
