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
public class CodeRunForJava implements CodeRun {
    private final static String location = "/docker/code/";
    @Override
    public ExecuteCodeResponse run(ExecuteCodeRequest request) {
        List<String> inputList = request.getInputList();
        String code = request.getCode();
        // 将代码存入到 文件里面去 // d:\\D:\program
        String fileName = TranCodeToFile.tranCode(code,   location +  "Main.java");

        try {
            String format = String.format("javac -encoding utf-8 %s", fileName);
            Process process = Runtime.getRuntime().exec(format);
            ExecuteMessage executeMessage = ProcessUtils.compileProcess(process);
            // 如果不是正常退出的话， 就是错误的哦
            if(executeMessage.getExitValue() != 0){
                ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
                executeCodeResponse.setJudgeInfo(new JudgeInfo(JudgeInfoMessageEnum.COMPILE_ERROR.getText(), 0L, 0L));
                executeCodeResponse.setOutputList(new ArrayList<String>());
                executeCodeResponse.setMessage(executeMessage.getErrorMessage());
                CompletableFuture.runAsync(() -> {
                    FileUtils.delFileAndClass(fileName);
                });
                return executeCodeResponse;
            }
            String preLocation = location.substring(0, location.lastIndexOf("\\"));
            String executeCommand =  String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main", preLocation);

            // 开始执行程序
            ExecuteCodeResponse executeCodeResponse = ProcessUtils.executeCode(executeCommand, 50000, inputList);

            return executeCodeResponse;
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }

    }
}
