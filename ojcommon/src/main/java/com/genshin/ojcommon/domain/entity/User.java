package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 用户表(User)实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="sys_user")
@ApiModel(description = "前端传过来的用户实体类")
public class User implements Serializable {
    public static final long serialVersionUID = -40356785423868312L;
    /**
    * 主键
    */
    @TableId
    @ApiModelProperty(value = "用户id",required = true)
    public Long id;
    /**
    * 用户名
    */
    @ApiModelProperty(value = "用户id",required = true)

    public String userName;
    /**
    * 昵称
    */
    @ApiModelProperty(value = "昵称",required = false)

    public String nickName;
    /**
    * 密码
    */
    @ApiModelProperty(value = "密码",required = true)

    public String password;
    /**
    * 账号状态（0正常 1停用）
    */
    @ApiModelProperty(value = "账号状态（0正常 1停用）",required = true)

    public String status;
    /**
    * 邮箱
    */
    @ApiModelProperty(value = "邮箱",required = false)

    public String email;
    /**
    * 手机号
    */
    @ApiModelProperty(value = "手机号",required = false)

    public String phonenumber;
    /**
    * 用户性别（0男，1女，2未知）
    */
    @ApiModelProperty(value = "用户性别（0男，1女，2未知）",required = false)

    public String sex;
    /**
    * 头像
    */
    @ApiModelProperty(value = "头像",required = false)

    public String avatar;
    /**
    * 用户类型（0管理员，1普通用户）
    */
    @ApiModelProperty(value = "用户类型（0管理员，1普通用户,2是出题者）",required = false)

    public String userType;
    /**
    * 创建人的用户id
    */
    @ApiModelProperty(value = "创建人的用户id",required = false)

    public Long createBy;
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间",required = false)

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime createTime;
    /**
    * 更新人
    */
    @ApiModelProperty(value = "更新人",required = false)

    public Long updateBy;
    /**
    * 更新时间
    */
    @ApiModelProperty(value = "更新时间",required = false)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime updateTime;
    /**
    * 删除标志（0代表未删除，1代表已删除）
    */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",required = false)
    public Integer delFlag;

    @ApiModelProperty(value="默认语言配置")
    public String defaultLanguage;

    @ApiModelProperty(value = "该用户被点赞次数")
    private Integer thumbNum;

    @ApiModelProperty(value = "该用户被收藏次数")
    private Integer favourNum;

    @ApiModelProperty(value = "用户个人简介")
    private String description;

    @ApiModelProperty(value= "用户关注了多少人")
    private Integer following;

    @ApiModelProperty(value =  "用户被多少人关注")
    private Integer followers;

}

