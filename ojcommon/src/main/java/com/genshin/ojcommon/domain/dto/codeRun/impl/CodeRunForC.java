package com.genshin.ojcommon.domain.dto.codeRun.impl;

import com.genshin.ojcommon.common.ExecuteMessage;
import com.genshin.ojcommon.domain.dto.codeRun.CodeRun;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeRequest;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import com.genshin.ojcommon.utils.FileUtils;
import com.genshin.ojcommon.utils.ProcessUtils;
import com.genshin.ojcommon.utils.TranCodeToFile;
import com.rabbitmq.client.BlockedCallback;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 23:21
 */
public class CodeRunForC implements CodeRun {
    private final static String location = "/docker/code/";
    @Override
    public ExecuteCodeResponse run(ExecuteCodeRequest request) {
        List<String> inputList = request.getInputList();
        String code = request.getCode();
        // 将代码存入到 文件里面去 // d:\\D:\program
        String fileName = TranCodeToFile.tranCode(code, location + UUID.randomUUID().toString() + ".c");
        String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
        try {
            Process process = new ProcessBuilder("gcc", fileName, "-o", prefixName + ".exe").start();
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
            // 开始执行程序
            String executeFileName = prefixName + ".exe";
            System.out.println(executeFileName);

            ExecuteCodeResponse executeCodeResponse = ProcessUtils.executeCode(executeFileName, 10000, inputList);
            CompletableFuture.runAsync(() -> {
                FileUtils.delFileAndExecutable(fileName);
            });
            return executeCodeResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
