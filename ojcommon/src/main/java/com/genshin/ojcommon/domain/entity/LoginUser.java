package com.genshin.ojcommon.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel(description = "用户权限信息实体类 ")
public class LoginUser implements UserDetails {
    @ApiModelProperty(value = "用户", required = true)
    public User user;
    @ApiModelProperty(value = "权限列表", required = true)

    public List<String> permissions;
    public LoginUser(User user , List<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }
    // 为了防止不能进行序列化，所以这个不能进行序列化
    @ApiModelProperty(value="作者权限" , required = true)
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 把permission中的String 类型的权限信息封装成实现类simpleGrantedAuthority
        if(authorities!=null)return authorities;
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        //TODO 我这里做了修改需要被解决
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
