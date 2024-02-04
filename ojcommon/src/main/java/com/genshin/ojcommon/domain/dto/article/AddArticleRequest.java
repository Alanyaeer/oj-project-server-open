package com.genshin.ojcommon.domain.dto.article;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/7 10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文章的实体类")
public class AddArticleRequest implements Serializable {
    @ApiModelProperty(value = "String", name = "标题名称")
    private String titleName;
    @ApiModelProperty(value = "String", name = "标签列表")
    private List<String> tags;

    @ApiModelProperty(value = "String", name = "文章内容")
    private String content;

    @ApiModelProperty(value = "Integer", name = "文章类别(0 表示 算法文章， 1表示题解文章，2 表示经验分享文章，3表示杂谈文章， 4表示竞赛文章，5表示算法板子文章)")
    private Integer articleType;

    private String articleId;


}
