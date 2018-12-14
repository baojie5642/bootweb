package com.baojie.admin.controller;

import com.baojie.admin.dto.ModifyResult;
import com.baojie.admin.dto.PageBean;
import com.baojie.admin.dto.PasswordHelper;
import com.baojie.admin.jpa.entity.User;
import com.baojie.admin.enums.ModifyEnums;
import com.baojie.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService usv;

    private final PasswordHelper pwdhper;

    @Autowired
    public UserController(UserService usv, PasswordHelper pwdhper) {
        if (null == usv || null == pwdhper) {
            throw new NullPointerException();
        } else {
            this.usv = usv;
            this.pwdhper = pwdhper;
        }
    }

    /**
     * 分页查询
     *
     * @param //查询条件
     * @param pageCode 当前页
     * @param pageSize 每页显示的记录数
     * @return
     */
    @RequiresUser
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
        if(null==user){
            throw new NullPointerException();
        }
        try {
            String name = securityName();
            User udb = usv.findByName(name);
            if (null == udb) {
                log.error("user may has deleted, user name=" + name);
                return new ModifyResult(false, ModifyEnums.LOGIN_UNKNOWN);
            }
            udb.setUsername(user.getUsername());
            udb.setNickname(user.getNickname());
            udb.setEmail(user.getEmail());
            usv.save(udb);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            return new ModifyResult(false, e.getMessage());
        }
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping("/update")
    public ModifyResult update(@RequestBody User user) {
        if (null == user) {
            throw new NullPointerException();
        }
        try {
            if (permit(user)) {
                // 说明是更新密码操作
                String name = securityName();
                User udb = usv.findByName(name);
                if (null == udb) {
                    log.error("user may has deleted, user name=" + name);
                    return new ModifyResult(false, ModifyEnums.LOGIN_UNKNOWN);
                }
                String oldSalt = udb.getSalt();
                if (StringUtils.isBlank(oldSalt)) {
                    log.error("user salt must not blank, user name=" + name);
                    return new ModifyResult(false, ModifyEnums.LOGIN_UNKNOWN);
                }
                String oldPwd = user.getCheckPass();
                // 使用原有的盐值对新的密码进行加密比较
                user.setSalt(oldSalt);               // 设置user盐值
                String oldSecPwd = pwdhper.encryptPassword(oldPwd, oldSalt);
                if (!udb.getPassword().equals(oldSecPwd)) {
                    log.info("输入的旧密码不匹配：new:" + user.getPassword() + ", old:" + udb.getPassword());
                    return new ModifyResult(false, ModifyEnums.LOGIN_CHECK_ERROR);
                } else {
                    String newSecPwd = pwdhper.encryptPassword(user.getPassword(), oldSalt);
                    // 将界面传过来的pwd加密后保存在从数据库中查询出来的user中，然后更新老的user
                    udb.setPassword(newSecPwd);
                    updateUser(udb);
                }
            }
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            return new ModifyResult(false, e.getMessage());
        }
    }

    private boolean permit(User user) {
        String pwd = user.getPassword();
        String oldPwd = user.getCheckPass();
        if (StringUtils.isBlank(pwd)) {
            return false;
        }
        if (StringUtils.isBlank(oldPwd)) {
            return false;
        }
        return true;
    }

    private User updateUser(User user) {
        // 这里的使用的mybits来做的，后续修改成jpa时会出错
        return usv.update(user);
    }

    private String securityName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }


    @RequiresUser
    @ResponseBody
    @RequestMapping("/delete")
    public ModifyResult delete(@RequestBody Long... ids) {
        try {
            usv.delete(ids);
            return new ModifyResult(true, ModifyEnums.SUCCESS);
        } catch (Exception e) {
            return new ModifyResult(false, e.getMessage());
        }
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public User getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        User user = usv.findByName(username);
        return user;
    }

    /**
     * 手动定义logout退出登录方法，因为Shiro提供的默认的`/logout`会退出到系统的根目录下即`localhost:8084`
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
}
