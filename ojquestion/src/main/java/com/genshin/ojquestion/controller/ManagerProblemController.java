package com.genshin.ojquestion.controller;

import com.genshin.ojcommon.domain.vo.QuestionManaReturnVo;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojquestion.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/10 13:50
 */
@RestController
@RequestMapping("/managerProblem")
@Slf4j
@Api(tags = "管理者题目相关接口")
public class ManagerProblemController {
    @Autowired
    private QuestionService questionService;

    /**
     *
     * @return
     * @apiNote 展示所有题目包括那些在请求队列中的
     */
    @ApiOperation("展示题目包括题目请求接口")
    @PreAuthorize("hasAnyAuthority('manager')")
    @GetMapping("/showQuestion")
    public ResponseResult showRequest(int page ,int pageSize){
        List<QuestionManaReturnVo> manaReturnDto = questionService.showRequest(page, pageSize);
        return new ResponseResult(200, "查找成功", manaReturnDto);
    }

    @ApiOperation("获取题目个数")
    @PreAuthorize("hasAnyAuthority('manager')")
    @PostMapping("/getQuestionNumber")
    public ResponseResult getQuestionNumber(){

        return new ResponseResult(200, "查询成功", questionService.count());
    }

    /**
     * @apiNote  删除方法是将questionType变成2
     * @param id
     * @return
     */
    @ApiOperation("删除题目")
    @PreAuthorize("hasAnyAuthority('manager')")
    @DeleteMapping("/deleteQuestionByNumber")
    public ResponseResult deleteQuestionByNumber(String id){
        boolean remove = questionService.removeByIdU(id);
        return new ResponseResult(200, "删除成功", remove);
    }
    @ApiOperation("删除题目通过标题")
    @PreAuthorize("hasAnyAuthority('manager')")
    @DeleteMapping("/deleteQuestionByTitle")
    public ResponseResult deleteQuestionByTitle(String titleName){
        boolean remove = questionService.removeByTitle(titleName);
//        boolean remove = questionService.remove(new LambdaQueryWrapper<Question>().eq(Question::getTitleName, titleName));
        if(remove)
        return new ResponseResult(200, "删除成功", 1);
        else
            return new ResponseResult(200, "删除失败", 0);

    }
    @ApiOperation("通过题目审核")
    @PreAuthorize("hasAnyAuthority('manager')")
    @PutMapping("/passQuestion")
    public ResponseResult passQuestionById(String id){
        boolean judge = questionService.passQuestion(id);

        return new ResponseResult<>(200, "删除成功", judge);
    }
}

