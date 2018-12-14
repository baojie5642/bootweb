package com.baojie.admin.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther TyCoding
 * @date 2018/10/21
 */
@Component
public class PasswordHelper {

    //实例化RandomNumberGenerator对象，用于生成一个随机数
    private final RandomNumberGenerator random = new SecureRandomNumberGenerator();
    //散列算法名称
    private final String algorithName = "MD5";
    //散列迭代次数
    private final int hashInterations = 2;

    @Autowired
    public PasswordHelper() {

    }

    //加密算法
    public final String encryptPassword(String pwd, String salt) {
        if (null == pwd || null == salt) {
            throw new NullPointerException();
        }
        if (StringUtils.isBlank(pwd) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException();
        }
        String spwd = securityPwd(pwd, salt);
        return spwd;
    }

    public final String randomSalt() {
        return random.nextBytes().toHex();
    }

    // 如果没有盐值就进行随机生成盐值
    // 但是Shiro进行密码校验并不会再次生成盐值
    // 因为是随机盐，Shiro会根据数据库中储存的盐值以及你注入的加密方式进行校验
    // 而不是使用这个工具类进行校验的
    // 对user对象设置盐：salt
    // 这个盐值是randomNumberGenerator生成的随机数
    // 所以盐值并不需要我们指定
    //调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
    private final String securityPwd(String normalPwd, String salt) {
        ByteSource bys = ByteSource.Util.bytes(salt);
        return new SimpleHash(algorithName, normalPwd, bys, hashInterations).toHex();
    }

}
