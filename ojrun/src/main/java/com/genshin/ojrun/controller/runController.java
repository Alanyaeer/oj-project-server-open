package com.genshin.ojrun.controller;

import com.genshin.ojapi.client.QuestionClient;
import com.genshin.ojapi.dto.CaseApiDto;
import com.genshin.ojapi.dto.SubmitDto;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.utils.OjStatusSummary;
import com.genshin.ojcommon.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.genshin.ojcommon.constants.RedisConstants.PASS_PROBLEM_PERSON;
import static com.genshin.ojcommon.constants.RedisConstants.TRY_PROBLEM_PERSON;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 17:44
 */
@RestController
@RequestMapping("/run")
@Api(tags="代码判题服务接口")
public class runController {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private QuestionClient questionClient;
    // 暂时可能要搬离到判题服务里面
    /**
     * @apiNote  提交问题， 然后开始判题，需要使用 队列优化来解决这个问题。
     * @param file
     * @param id 题目id
     * @return
     */
    @PostMapping("/submitQuestion")
    @Deprecated
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("提交问题接口")
    // 注意这个id有点问题
    public ResponseResult submitQuestion(MultipartFile file, String id, String titleId, HttpServletRequest request, int codeType) throws InterruptedException {
        boolean isSuccess = false;
        String location = "temp";
        Integer status = 0;
        Long lastReason = -1L;
        // 如果是-1的话， 那么就插入成功， 否则就算是失败了
        Integer lastCase = -1;
        // 模拟提交场景, TODO 未来，记得要删除这段代码
        String token = request.getHeader("token");
        List<CaseApiDto> textCase = questionClient.getTextCase(titleId, token);
        for (CaseApiDto caseApiDto : textCase) {
            String op = caseApiDto.getOutputText();
            String ip = caseApiDto.getInputText();
            System.out.println(ip);
            System.out.println(op);
        }
        // 异步插入数据到数据库中
        SubmitDto submitDto = new SubmitDto();

        questionClient.insert(new SubmitDto(id, location, status, lastReason, lastCase, titleId), request.getHeader("token"));
        // 更新 redis 中 提交人数


        // 如果回答正确， 先判断是否已经提交过该题目， 然后根据分数判断应该是什么类别的
        if(lastCase == -1){
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser)authentication.getPrincipal();
            Long userId = loginUser.getUser().getId();
            Long questionId = Long.valueOf(titleId);
            questionClient.RecordQuestion(questionId, userId,token );
        }
        return new ResponseResult<>(200, OjStatusSummary.getOJStatus(lastReason), lastCase);
    }
}
