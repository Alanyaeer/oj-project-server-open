package com.genshin.ojcommon.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/3 23:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户信息前端实体类")
public class UserInfoVo {
    /**
     * 用户名
     */
    @ApiModelProperty(value="id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "用户id",required = true)

    public String userName;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",required = false)

    public String nickName;
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",required = false)

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",required = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime updateTime;

    @ApiModelProperty(value = "用户描述")
    private String description;

    @ApiModelProperty(value = "用户收藏量")
    private Integer favourNum;
    @ApiModelProperty(value = "用户点赞量")
    private Integer thumbNum;
    @ApiModelProperty(value = "用户现在关注的人数")
    private Integer following;
    @ApiModelProperty(value="关注了该用户的人数")
    private Integer followed;
    @ApiModelProperty(value = "各种语言提交的个数")
    private List<SolveMsgVo> solveMsgVoList;
    private boolean FollowPerson;
    private boolean self;
    private int reads;
}
