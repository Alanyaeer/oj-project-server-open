package com.genshin.ojcommon.domain.dto.codesandbox;

import com.genshin.ojcommon.domain.dto.codesandbox.impl.ExampleCodeSandbox;
import com.genshin.ojcommon.domain.dto.codesandbox.impl.MyCodeSandbox;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:30
 */
public class CodeSandboxFactory {

    // 给定一个 type 类型
    public static CodeSandbox newInstance(String type){
        switch (type) {
            case "my":
                return new MyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }

}
