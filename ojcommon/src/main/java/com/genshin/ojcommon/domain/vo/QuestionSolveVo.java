package com.genshin.ojcommon.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/29 16:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSolveVo extends UserInfoVo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String titleName;
    // 截取前 60个字符
    private String content;
    private List<String> tags;
    private Integer articleThumbNums;
    private Integer  articleReads;
    private Integer articleTalks;
    private LocalDateTime dateTime;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long userId;
}
