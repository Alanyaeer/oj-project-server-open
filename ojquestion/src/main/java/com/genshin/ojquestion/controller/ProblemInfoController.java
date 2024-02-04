package com.genshin.ojquestion.controller;

import com.genshin.ojapi.dto.CaseApiDto;
import com.genshin.ojapi.dto.SubmitDto;
import com.genshin.ojcommon.domain.dto.SubmitRecordsDto;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.vo.SolveVo;
import com.genshin.ojcommon.dto.CaseDto;
import com.genshin.ojcommon.utils.FileUtils;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojquestion.service.SubmitRecordsService;
import com.genshin.ojquestion.service.TestcaseService;
import com.rabbitmq.client.AMQP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.*;

/**
 * @author 吴嘉豪
 * @date 2023/12/29 15:16
 */
@RestController
@RequestMapping("/getQuestion")
@Slf4j
@Api(tags = "提交问题相关接口")
public class ProblemInfoController {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SubmitRecordsService submitRecordsService;
    @Autowired
    private TestcaseService testcaseService;

    @PutMapping("/insert")
    @ApiOperation("服务判题服务发送远端存储")
    public void insertCase(@RequestBody SubmitDto submitDto){
        log.info("插入服务");
        submitRecordsService.insert(submitDto.getId(), submitDto.getLocation(), submitDto.getStatus(), submitDto.getLastReason(),submitDto.getLastcase(), Long.valueOf(submitDto.getTitleId()));
    }
    @PostConstruct
    private void initMethod(){
        // 判断全局通过人数是否建立字段
        for (int i = 0; i < 3; i++) {
            Object cacheObject = redisCache.getCacheObject(PASS_PROBLEM_PERSON + ":" + i);
            Object cacheObject1 = redisCache.getCacheObject(TRY_PROBLEM_PERSON + ":" + i);
            if(cacheObject1 == null){
                redisCache.setCacheObject(PASS_PROBLEM_PERSON + ":" + i, 0);
            }
            if(cacheObject == null){
                redisCache.setCacheObject(TRY_PROBLEM_PERSON + ":" + i, 0);
            }
        }

    }

    /**
     *
     * @param id 用户查询的题目id
     * @return
     */
    @PostMapping("/getQuestionTestcase")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("获取题目的部分数据用例")
    public ResponseResult getQuestionTestCase(String id){
        // 获取题目展示测试用例 ， type 是 0的
        List<CaseDto> text = testcaseService.selectById(id, 0);
        // 返回结果
        return new ResponseResult(200, "获取展示数据成功", text);
    }


    @PostMapping("/getUserTestcase")
    @PreAuthorize("hasAuthority('vip')")
    @ApiOperation("获取用户错误的题目测试用例接口")
    public ResponseResult getUserTestCase(String id){

        // 从数据库里面搜索出这个提交的数据  --- 后期优化 就是加上用户自己错误的地方是什么
        SubmitRecords bId = submitRecordsService.getById(id);
        Integer lastCase = bId.getLastCase();
        Long titleId = bId.getQuestionId();
        testcaseService.selectById(titleId.toString(), 1);
        // 测试用例获取到的信息
        return null;
    }

    @PostMapping("/getTestcaseInPutFromMachine")
    @ApiOperation("判题机获取题目测试用例")
    @PreAuthorize("hasAuthority('vip')")
    public List<CaseApiDto> getTestcaseInPutFromMachine(String  titleId){
        List<CaseDto> list = testcaseService.selectById(titleId, 1);
        List<CaseApiDto> apiDto = list.stream().map(e -> {
            CaseApiDto caseApiDto = new CaseApiDto();
            BeanUtils.copyProperties(e, caseApiDto);
            return caseApiDto;
        }).collect(Collectors.toList());
        return apiDto;
    }

    /**
     *
     */
    @PostMapping("/getPassMessage")
    @ApiOperation("获取单个用户通过这个题目的比例信息")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getPassMessage(Long userId) {
        if (userId == null)
            userId = GetLoginUserUtils.getUserId();
        ArrayList<SolveVo> solveVoArrayList = new ArrayList<>();
        for(int i = 0; i < 3; ++i){
            solveVoArrayList.add(new SolveVo(0, 0));
            try {
                Integer solvePass =  redisCache.getCacheObject(PASS_PROBLEM_MESSAGE + ":" + userId + ":" + i);
                if(solvePass == null){
                    redisCache.setCacheObject(PASS_PROBLEM_MESSAGE + ":" +userId + ":"+ i, 0);
                    solvePass = 0;

                }
                solveVoArrayList.get(i).setPassNum(solvePass);

            }
            catch (Exception e){
                System.out.println("异常错误");
                List<SolveVo> passMessage = submitRecordsService.getPassMessage(userId);
                return new ResponseResult(200, "获取题目", passMessage);
            }
        }
        return new ResponseResult(200, "获取题目", solveVoArrayList);

    }
    /**
     * 简单题 中等题 困难题的 个数 redis返回 // 很多人用 redis
     * 提交题目通过的的比例信息，
     */
    @PostMapping("/getSolveTotalMessage")
    @ApiOperation(("获取所有用户提交题目的比例信息"))
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getSolveTotalMessage() {
        ArrayList<SolveVo> solveVoArrayList = new ArrayList<>();

        for(int scoreType = 0; scoreType < 3; ++scoreType){
            try{
                solveVoArrayList.add(new SolveVo(0, 0));
                solveVoArrayList.get(scoreType).setPassNum(redisCache.getCacheObject(PASS_PROBLEM_PERSON + ":" + scoreType));
                solveVoArrayList.get(scoreType).setTryNum( redisCache.getCacheObject(TRY_PROBLEM_PERSON +  ":" + scoreType));
            }catch (Exception e){
                e.printStackTrace();
                List<SolveVo> solveVoByScore = submitRecordsService.getSolveVoByScore(0, -1L);
                return new ResponseResult(200, "获取成功",solveVoByScore);
            }
        }
        return new ResponseResult<>(200, "获取成功", solveVoArrayList);


//        for(int scoreType = 0; scoreType < 3; ++scoreType){
//            SolveVo solveVo = solveVoByScore.get(scoreType);
//            Integer passNum = solveVo.getPassNum();
//            Integer tryNum = solveVo.getTryNum();
//            redisCache.incrByCacheObject(PASS_PROBLEM_PERSON + ":" + scoreType, passNum);
//            redisCache.incrByCacheObject(TRY_PROBLEM_PERSON + ":"  + scoreType, tryNum);
//
//        }

//        redisCache.incrByCacheObject(PASS_PROBLEM_PERSON, );
//        redisCache.incrByCacheObject(TRY_PROBLEM_PERSON, 1);
//        redisCache.incrByCacheObject(TRY_PROBLEM_PERSON + ":" + GetLoginUserUtils.getUserId(), 1);
    }


    /**
     * 简单题， 中等题 困难题
     * 然后简单题里面又可以根据通过率来进行划分
     *
     * @return List<SolveVo>
     */
    @PostMapping("/getSolveMessage")
    @ApiOperation("获取用户提交题目的比例信息")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult  getSolveMessage(Long userId) {
        if(userId == null)
            userId = GetLoginUserUtils.getUserId();
        ArrayList<SolveVo> solveVoArrayList = new ArrayList<>();

        for(int scoreType = 0; scoreType < 3; ++scoreType){
            try{
                solveVoArrayList.add(new SolveVo(0, 0));
                Integer solvePass = redisCache.getCacheObject(PASS_PROBLEM_PERSON + ":" +userId + ":"+ scoreType);
                Integer solveTry =  redisCache.getCacheObject(TRY_PROBLEM_PERSON +  ":"+ userId + ":"+ + scoreType);
                if(solvePass == null){
                    redisCache.setCacheObject(PASS_PROBLEM_PERSON + ":" +userId + ":"+ scoreType, 0);
                    solvePass = 0;
                }
                if(solveTry == null){
                    redisCache.setCacheObject(TRY_PROBLEM_PERSON +  ":"+ userId + ":"+ + scoreType, 0);
                    solveTry = 0;
                }
                solveVoArrayList.get(scoreType).setPassNum(solvePass);
                solveVoArrayList.get(scoreType).setTryNum(solveTry);
            }catch (Exception e){
                e.printStackTrace();
                List<SolveVo> solveVoByScore = submitRecordsService.getSolveVoByScore(0, userId);
                return new ResponseResult(200, "获取成功",solveVoByScore);
            }
        }
        return new ResponseResult(200, "获取成功 ", solveVoArrayList);
//        List<SolveVo> solveVo =  submitRecordsService.getSolveVoByScore(0, userId);
////        for(int scoreType = 0; scoreType < 3; ++scoreType) {
////            SolveVo solveVos = solveVo.get(scoreType);
////            Integer passNum = solveVos.getPassNum();
////            Integer tryNum = solveVos.getTryNum();
////            redisCache.incrByCacheObject(PASS_PROBLEM_PERSON + ":" + GetLoginUserUtils.getUserId() + ":" + scoreType, passNum);
////
////            redisCache.incrByCacheObject(TRY_PROBLEM_PERSON + ":"+GetLoginUserUtils.getUserId() + ":" + scoreType,tryNum);
////        }
//        return new ResponseResult(200, "获取成功", solveVo);
    }
    //    // 等待修改
}
