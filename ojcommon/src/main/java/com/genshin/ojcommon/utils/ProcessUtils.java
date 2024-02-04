package com.genshin.ojcommon.utils;


import com.genshin.ojcommon.common.ExecuteMessage;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.enums.JudgeInfoMessageEnum;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import org.apache.tomcat.jni.Proc;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 进程工具类
 */
public class ProcessUtils {
    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    /**
     * 执行进程并获取信息
     * @param runProcess
     * @return
     */
    public static ExecuteMessage compileProcess(Process runProcess){
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            //正常退出
            if (exitValue ==0){
                // 分批获取程序的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                // 逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine())!= null){
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
                System.out.println(compileOutputStringBuilder);
            }else {
                InputStream errorStream = runProcess.getErrorStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = errorStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                byte[] errorBytes = outputStream.toByteArray();
                UniversalDetector detector = new UniversalDetector(null);
                detector.handleData(errorBytes, 0, errorBytes.length);
                detector.dataEnd();
                String detectedCharset = detector.getDetectedCharset();
                detector.reset();
                BufferedReader errorBufferedReader;
                if (detectedCharset != null) {
                    errorBufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(errorBytes), detectedCharset));
                } else {
                    // 如果无法检测到编码，则使用系统默认编码
                    errorBufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(errorBytes)));
                }
                StringBuilder errorCompileOutputStringBuilder = new StringBuilder();
                // 逐行获取
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
//                    errorOutputStrList.add(errorCompileOutputLine);
                    errorCompileOutputStringBuilder.append(errorCompileOutputLine + "\n");
                }
                errorBufferedReader.close();
                executeMessage.setErrorMessage(errorCompileOutputStringBuilder.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return executeMessage;
    }
    private static void inputValueToCode(Integer times, Process process, String input, boolean isCloseChannel) throws IOException {
        CompletableFuture.runAsync(()->{
            try {
                System.out.println("程序正在运行中 。。。  ");
                Thread.sleep(times);
                process.destroy();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        // 将数据写入到 程序之中
        OutputStream outputStream = process.getOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(input);
        if(isCloseChannel){
            writer.flush();
            // 关闭输出流，表示输入结束 报错管道正在被关闭
            writer.close();
        }

    }

    private static String getOutPutFromCode(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();

        // 使用 BufferedReader 读取输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        // 读取输出
        while ((line = reader.readLine()) != null) {
//                    outputList.add(line);
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param executeName 传入 完成的执行指令
     * @param times 程序运行需要的时间
     * @param inputList 输入的list
     * @return
     */
    public static ExecuteCodeResponse executeCode(String executeName, Integer times, List<String> inputList) {
        System.out.println("开始运行啦 ");
        long totalTime = 0;
        // 不准确所以先随便弄弄
        long totalCache = 0;
        List<String> outputList = new ArrayList<>();
        for(int i = 0; i < inputList.size(); ++i){
            try {
                String input = inputList.get(i);
                Process process = Runtime.getRuntime().exec(executeName);
                long start = System.currentTimeMillis();
                long usedStart = memoryMXBean.getHeapMemoryUsage().getUsed();
//                memoryMXBean.getNonHeapMemoryUsage()
                long usedNonHeapStart = memoryMXBean.getNonHeapMemoryUsage().getUsed();

                inputValueToCode(times, process, input, true);
                String outputValue = getOutPutFromCode(process);
                int exitCode = process.waitFor();
                long end = System.currentTimeMillis();
                long usedEnd = memoryMXBean.getHeapMemoryUsage().getUsed();
                long usedNonHeapEnd = memoryMXBean.getNonHeapMemoryUsage().getUsed();
                totalTime += end - start;
                // 做一个假的内存消耗， 因为不知道 怎么检测
                if(usedEnd - usedStart != 0)
                totalCache += usedEnd - usedStart;
                else totalCache += usedNonHeapEnd - usedNonHeapStart;
                outputList.add(outputValue);
                if(exitCode != 0){
                    ExecuteCodeResponse executeCodeResponseFail = new ExecuteCodeResponse();
                    executeCodeResponseFail.setJudgeInfo(new JudgeInfo(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getText(), totalTime + 2000L, totalCache));
                    executeCodeResponseFail.setOutputList(outputList);
                    return executeCodeResponseFail;
                }
                // 输入结果
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutputList(outputList);
        response.setJudgeInfo(new JudgeInfo(JudgeInfoMessageEnum.ACCEPTED.getText(), totalTime, totalCache));
        return response;
    }
}