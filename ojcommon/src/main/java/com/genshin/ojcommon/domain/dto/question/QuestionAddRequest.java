package com.genshin.ojcommon.domain.dto.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.IdentNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 20:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "题目添加请求实体类")
public class QuestionAddRequest implements Serializable {
    /**
     *  语言限制
     */
    @ApiModelProperty(value = "String", name = "语言限制")
    private List<String> language;

    /**
     *  测试用例 ， 输入输出
     */
    @ApiModelProperty(value = "String", name = "判题案例")
    private List<JudgeCase> judgeCase;

    @Deprecated
    @ApiModelProperty(value = "String", name = "判题答案")
    private String ans;
    /**
     *  tags
     */
    @ApiModelProperty(value = "String", name = "标签列表JSON风格")
    private List<Integer> tags;

    @ApiModelProperty(value = "String", name = "标题名字")
    private String titleName;

    @ApiModelProperty(value = "JudgeConfig", name = "题目判题的限制")
    private JudgeConfig judgeConfig;

}
