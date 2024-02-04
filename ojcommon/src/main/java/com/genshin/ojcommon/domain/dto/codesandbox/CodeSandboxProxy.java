package com.genshin.ojcommon.domain.dto.codesandbox;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 23:30
 */
public class CodeSandboxProxy implements CodeSandbox{
    private CodeSandbox codeSandbox;
    public CodeSandboxProxy(CodeSandbox codeSandbox){
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println(executeCodeRequest.toString());
        // 执行代码
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
