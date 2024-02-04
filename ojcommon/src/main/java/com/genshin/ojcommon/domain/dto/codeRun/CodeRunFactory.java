package com.genshin.ojcommon.domain.dto.codeRun;

import com.genshin.ojcommon.domain.dto.codeRun.impl.CodeRunForC;
import com.genshin.ojcommon.domain.dto.codeRun.impl.CodeRunForCpp;
import com.genshin.ojcommon.domain.dto.codeRun.impl.CodeRunForGo;
import com.genshin.ojcommon.domain.dto.codeRun.impl.CodeRunForJava;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 23:19
 */
public class CodeRunFactory {
    public static CodeRun newInstance (Integer language){
        switch (language){
            case 0:
                return new CodeRunForCpp();
            case 1:
                return new CodeRunForJava();
            case 2:
                return new CodeRunForGo();
            case 3:
                return new CodeRunForC();
            // 暂时写这个
            default:
                return new CodeRunForCpp();
        }
    }
}
