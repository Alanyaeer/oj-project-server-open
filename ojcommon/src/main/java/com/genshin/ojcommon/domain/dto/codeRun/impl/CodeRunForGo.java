package com.genshin.ojcommon.domain.dto.codeRun.impl;

import com.genshin.ojcommon.common.ExecuteMessage;
import com.genshin.ojcommon.domain.dto.codeRun.CodeRun;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeRequest;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.utils.FileUtils;
import com.genshin.ojcommon.utils.ProcessUtils;
import com.genshin.ojcommon.utils.TranCodeToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 23:21
 */
public class CodeRunForGo implements CodeRun {
    private final static String location = "/docker/code/";
    @Override
    public ExecuteCodeResponse run(ExecuteCodeRequest request) {
        List<String> inputList = request.getInputList();
        String code = request.getCode();
        // 将代码存入到 文件里面去 // d:\\D:\program
        String fileName = TranCodeToFile.tranCode(code, location + UUID.randomUUID().toString() + ".go");
        String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
        // 目前只能普通运行模式
            // 开始执行程序
        String executeFileName = String.format("go run %s" , fileName);
        System.out.println(executeFileName);

        ExecuteCodeResponse executeCodeResponse = ProcessUtils.executeCode(executeFileName, 10000, inputList);
        CompletableFuture.runAsync(() -> {
            FileUtils.delFileAndExecutable(fileName);
        });
        return executeCodeResponse;

    }
}
