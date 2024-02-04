package com.genshin.ojcommon.domain.dto.codesandbox;
import lombok.Builder;
import lombok.Data;

import java.util.List;
/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:34
 */
@Builder
@Data
public class ExecuteCodeRequest {
    private List<String> inputList;

    private String code;
    /**
     *  语言类型 根据  枚举类来判断语言的类型
     */
    private Integer language;
}
