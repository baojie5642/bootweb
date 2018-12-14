package com.baojie.admin.jpa.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity(name = "baojie_boot_tumo_users")
public class User implements Serializable {

    private static final long serialVersionUID = 8892253191532681448L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;             //编号
    @NotEmpty
    private String username;     //用户名
    private String nickname;     //昵称
    @NotEmpty
    private String password;     //密码
    @NotEmpty
    private String salt;         //盐
    @NotEmpty
    private String email;        //邮箱

    @Transient
    private String checkPass;    //用于旧密码校验的属性,设置的值为原始密码,这个字段考虑重构掉,主要用于前端更新记录旧密码

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
