package com.genshin.ojcommon.domain.dto.codesandbox.impl;

import com.genshin.ojcommon.domain.dto.codeRun.CodeRun;
import com.genshin.ojcommon.domain.dto.codeRun.CodeRunFactory;
import com.genshin.ojcommon.domain.dto.codesandbox.CodeSandbox;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeRequest;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:32
 */
public class MyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 到时候想想 docker 代码沙盒要怎么实现
        Integer language = executeCodeRequest.getLanguage();
        // 选择 cpp or java or go
        CodeRun codeRun = CodeRunFactory.newInstance(language);
        return codeRun.run(executeCodeRequest);
    }
}
