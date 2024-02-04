package com.genshin.ojcommon.domain.vo;

import com.genshin.ojcommon.domain.dto.question.JudgeConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/6 10:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "题目展示实体类")
public class QuestionShowVo  extends QuestionReturnVo{
    @ApiModelProperty(value = "String", name = "题目内容")
    private String content;
    @ApiModelProperty(value = "List", name = "语言范围")
    private List<String> language;
    @ApiModelProperty(value = "JudgeConfig", name = "判题限制")
    private JudgeConfig judgeConfig;
    // 需要先获取通过createUserId 获取到用户当前的nickName
    @ApiModelProperty(value = "createBy", name = "创建人")
    private String createBy;

    private Boolean isFavour;
    private Boolean isThumb;
}
