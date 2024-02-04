package com.genshin.ojcommon.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/30 19:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleShowContentVo{
    private UserInfoVo userInfo;
    // 文章的id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    private List<String> tags;
    private String content;
}
