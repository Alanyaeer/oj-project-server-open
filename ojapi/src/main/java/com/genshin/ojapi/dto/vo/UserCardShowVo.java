package com.genshin.ojapi.dto.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author 吴嘉豪
 * @date 2024/1/8 23:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel(description = "用户卡片展示实体类")
public class UserCardShowVo implements Serializable {
//    @ApiModelProperty(value = "String", name = "用户头像")
    private String avatar;

    //    private Integer User
//    @ApiModelProperty(value = "Integer", name = "用户收藏人数")
    private Integer UserFavourNum;
//    @ApiModelProperty(value = "Integer", name = "用户关注人数")

    private Integer Followers;
//    @ApiModelProperty(value = "Integer", name = "用户点赞人数")

    private Integer UserThumbNum;
//    @ApiModelProperty(value = "String", name = "用户昵称")
    private String NickName;
//    @ApiModelProperty(value = "String", name = "用户描述")

    private String UserDescription;

//    @ApiModelProperty(value = "String", name = "是否关注该用户")
    private Boolean isFollow;
}
