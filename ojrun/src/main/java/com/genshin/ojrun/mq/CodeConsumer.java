package com.genshin.ojrun.mq;

import com.genshin.ojapi.client.QuestionClient;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.constants.MqConstants;
import com.genshin.ojcommon.domain.dto.mq.SubmitQuestionWithTokenDto;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.enums.QuestionSubmitStatusEnum;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojrun.service.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.genshin.ojcommon.constants.MqConstants.RUN_CODE_QUEUE;


/**
 * @author 吴嘉豪
 * @date 2024/1/16 10:58
 */
@Component
@Slf4j
public class CodeConsumer {
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private QuestionClient questionClient;
    @SneakyThrows
    @RabbitListener(queues = RUN_CODE_QUEUE, ackMode = "MANUAL")
    public void receiveMessage(SubmitQuestionWithTokenDto submitQuestionWithTokenDto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        String message = submitQuestionWithTokenDto.getMessage();
        String token = submitQuestionWithTokenDto.getToken();
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId, token);
            SubmitRecords questionSubmit = questionClient.getRecordSubmitById(questionSubmitId, token);
            if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.SUCCEED.getValue())) {
                channel.basicNack(deliveryTag, false, false);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "判题失败");
            }

            log.info("新提交的信息：" + questionSubmit);
            // 设置通过数
            Long questionId = questionSubmit.getQuestionId();
            log.info("题目:" + questionId);
            Question question = questionClient.getQuestionById(questionId, token);
            Integer passNum = question.getPassPerson();
            Question updateQuestion = new Question();
            synchronized (question.getSubmitNum()) {
                passNum = passNum + 1;
                updateQuestion.setId(questionId);
                // 更新尝试的个数
                // 如果成功的话就增加尝试的人数
                if(questionSubmit.getJudgeInfo().contains("成功"))
                    updateQuestion.setPassPerson(passNum);
                boolean save = questionClient.updateQuestion(updateQuestion, token);
                if (!save) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存数据失败");
                }
            }
            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
