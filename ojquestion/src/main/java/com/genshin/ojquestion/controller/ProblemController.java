package com.genshin.ojquestion.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.genshin.ojcommon.common.ErrorCode;
import com.genshin.ojcommon.domain.dto.question.JudgeInfo;
import com.genshin.ojcommon.domain.dto.question.QuestionAddRequest;
import com.genshin.ojcommon.domain.dto.question.QuestionSubmitAddRequest;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.enums.QuestionSubmitLanguageEnum;
import com.genshin.ojcommon.domain.vo.*;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojcommon.domain.entity.TagList;
import com.genshin.ojcommon.exception.BusinessException;
import com.genshin.ojcommon.utils.JsonTransforUtils;
import com.genshin.ojcommon.utils.RedisCache;
import com.genshin.ojquestion.mapper.TagListMapper;
import com.genshin.ojquestion.service.*;
import com.genshin.ojcommon.utils.GetLoginUserUtils;
import com.genshin.ojquestion.utils.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import static com.genshin.ojcommon.constants.RedisConstants.QUESTION_NUMS;

/**
 * @author 吴嘉豪
 * @date 2023/12/10 13:49
 */
@Slf4j
@RestController
@RequestMapping("/problem")
@Api(tags = "出题者题目相关接口")
public class ProblemController {
    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagListMapper tagListMapper;
    @Autowired
    private TestcaseService testcaseService;
    @Autowired
    private UserSolveService userSolveService;

    @Autowired
    private UserQuestionService userQuestionService;
    @Autowired
    private SubmitRecordsService submitRecordsService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;
    private final Long templateId = 1743254891350691841L;
//    private static final String basepath ="D:\\fileandpicture\\";
    /**
     *
     * @param file
     * @return
     * @apiNote 用于上传题目, 我们可以通过校验模块来获取用户的名字, 如果是管理者权限直接问题直接上传成功，否则进入审核队列
     * @apiNote2  先看到某个用户是否等于4，
     */
    @ApiOperation("上传包括了保存题目题目接口")
    @PreAuthorize("hasAnyAuthority('pmk')")
    @ApiImplicitParam(value = "file", name = "上传的题目文件")
    @PostMapping("/upload")
    public ResponseResult uploadProblem(MultipartFile file){

        Long userId = GetLoginUserUtils.getUserId();
        // 先判断是否 类型为4的题目， 如果有就把 file 传入进来， 更新即可， 否则进入构建操作
        questionService.uploadFileWithoutSub(file, userId);
        return new ResponseResult(200, "上传成功", 1);
    }
    /**
     *
     * @return
     * @apiNote 用于上传题目, 我们可以通过校验模块来获取用户的名字, 如果是管理者权限直接问题直接上传成功，否则进入审核队列
     *
     */
    @ApiOperation("确认上传题目接口")
    @PreAuthorize("hasAnyAuthority('pmk')")
    @ApiImplicitParam(value = "file", name = "上传的题目文件")
    @PutMapping("/submitUpload")
    public ResponseResult submitUploadProblem(@RequestBody QuestionAddRequest questionAddRequest){
        Long userId = GetLoginUserUtils.getUserId();
        boolean rep = questionService.updateProblemStatus(userId , questionAddRequest);
        if(rep)
        return  new ResponseResult(200, "提交成功，请等待审核", true);
        else return new ResponseResult(200, "提交失败， 请更换题目名称", false);
    }

    /**
     *
     * @param id
     * @param userName
     * @return
     * @apiNote 如果是出题者删除需要获取管理的审批，如果是管理者可以自己删除
     */
    @ApiOperation("删除请求题目接口")
    @PreAuthorize("hasAnyAuthority('pmk')")
    @ApiImplicitParams({
            @ApiImplicitParam(value="id", name = "上传的题目编号"),
            @ApiImplicitParam(value="userName", name = "用户名")
    })
    @DeleteMapping("/delete")
    public ResponseResult deleteProblem(Long id, String userName){

        return  null;
    }

    /**
     *
     * @return
     * @apiNote 展示所有题目《不》包括那些在请求队列中的
     */
    @ApiOperation("展示题目接口")
    @PreAuthorize("hasAnyAuthority('vip')")
    @GetMapping("/showQuestion")
    public ResponseResult showQuestion(int page, int pageSize){
        List<QuestionReturnVo> collect = questionService.showRequestForVip(page, pageSize);

        return new ResponseResult(200, "获取题目列表", collect);
    }

    @PostMapping("/getLatestSubmitRecords")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getLatestRecords (String userId){
        if(StrUtil.isEmpty(userId)) userId = GetLoginUserUtils.getUserId().toString();
        SubmitRecords submitRecords =  submitRecordsService.getLatestSubmit(userId);
        SubmitRecordsVo recordsVo = submitEntityToVo(submitRecords);
        return new ResponseResult<>(200, "转换成功", recordsVo);
    }


    @PostMapping("/getQuestionNum")
    public ResponseResult getQuestionNum(){
        Map<String, Integer> map = redisTemplate.opsForHash().entries(QUESTION_NUMS);
        return new ResponseResult<>(200, "获取成功", map);
    }
    @ApiOperation("获取题目内容")
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/getQuestionContent")
    public ResponseResult getQuestionContent(String id, HttpServletRequest request){
        String token = request.getHeader("token");
        QuestionShowVo questionShowVo =  questionService.getQuestionContent(id, token);

        return new ResponseResult(200, "获取题目内容成功", questionShowVo);
    }
    @ApiOperation("获取题目内容通过题目名称")
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/getQuestionContentByTitleName")
    public ResponseResult getQuestionContentByTn(String titleName, HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        String decode = URLDecoder.decode(titleName, "UTF-8");
        System.out.println(decode);
        QuestionShowVo questionShowVo =  questionService.getQuestionContentByTitleName(decode, token);

        return new ResponseResult(200, "获取题目成功", questionShowVo);
    }
    /**
     * @apiNote 导入题目模板
     * @return
     */
    @ApiOperation("获取题目模板接口")
    @PreAuthorize("hasAuthority('pmk')")
    @GetMapping("/getQuestionTem")
    public ResponseResult getQuestionTemp(){
        Question question = questionService.getById(templateId);
        return new ResponseResult(200, "获取模板成功", question.getContent());
    }

    @ApiOperation("获取未提交的题目模板")
    @PreAuthorize("hasAuthority('pmk')")
    @PostMapping("/getLastEdit")
    public ResponseResult getLastEdit() {
        Long userId = GetLoginUserUtils.getUserId();
        Question question = questionService.getByUserId(userId);
        // 数据脱离敏感
        return new ResponseResult(200, "获取题目成功", question.getContent());
    }
    /**
     * @note 获取题目的所有的标签
     * @return
     */
    @ApiOperation("获取所有的标签")
    @PreAuthorize("hasAuthority('vip')")
    @GetMapping("/getTagList")
    public ResponseResult getTagList() {
        List<TagList> allTag = tagListMapper.getAllTag();

        List<TagListTransForVo> tagList = allTag.stream().map(e -> {
            TagListTransForVo tagListTransForDto = new TagListTransForVo();
            tagListTransForDto.setLabel(e.getTagName());
            tagListTransForDto.setValue(e.getId());
            return tagListTransForDto;
        }).collect(Collectors.toList());
        return new ResponseResult<>(200, "获取所有的标签", tagList);
    }

    /**
     *
     * @param id    测试用例id
     * @param inputFile
     * @param type  测试用例类型
     * @return
     */
    @ApiOperation("添加题目用例")
    @PreAuthorize("hasAuthority('pmk')")
    @PostMapping("/addQuestionTestcase")
    public  ResponseResult addQuestionTestcase(String id, MultipartFile inputFile, int type, MultipartFile outputFile) {
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        try {
            byte[] inBytes = inputFile.getBytes();
            ByteArrayInputStream inputStreamIp = new ByteArrayInputStream(inBytes);
            BufferedReader readerIp = new BufferedReader(new InputStreamReader(inputStreamIp));
            String line;

            while((line = readerIp.readLine()) != null){
               input.append(line);
            }
            byte[] opBytes = outputFile.getBytes();
            ByteArrayInputStream inputStreamOp = new ByteArrayInputStream(opBytes);
            BufferedReader readerOp = new BufferedReader(new InputStreamReader(inputStreamOp));
            while((line = readerOp.readLine()) != null){
                output.append(line);
            }
            readerIp.close();
            readerOp.close();
            inputStreamIp.close();
            inputStreamOp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String ips = input.toString();
        String ops = output.toString();

        log.info(ips);
        log.info(ops);
        if(ips.length() > 40 && type == 0){
            return new ResponseResult(241, "添加失败", "该测试用例不合适作为测试用例展示");
        }
        if(ops.length() > 40 && type == 0){
            return new ResponseResult(241, "添加失败", "该测试用例不合适作为测试用例展示" );
        }
        testcaseService.insertById(id, ips, type, ops);
        return new ResponseResult(200, "获取成功", 1);
    }

//    @PostMapping("/addQuestionRec")
//    @ApiOperation("提交题目")
//    public void RecordQuestion(Long titleId,Long userId){
//        // 判断题目是否被解决
//        boolean isSolve = userSolveService.issue(titleId, userId);
//        if(isSolve)return ;
//        // 根据题目难度 ， 给用户难度分值表更新
//        userQuestionService.insert(titleId, userId);
//
//    }
    @PostMapping("/test")
    public ResponseResult test(Long id){
        System.out.println(id);
        return new ResponseResult(200, "fa");
    }
    /**
     *
     * @return
     * codeType == 0 的时候代表 测试运行， 等于1的时候代表 直接提交
     * 可能废弃   ~~~
     *
     * // 语言配置， 代码, 题目id
     */

    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/submitCode")
    @ApiOperation("用户提交代码判题")
    public ResponseResult judgeCode(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request){
        if(questionSubmitAddRequest == null ||  questionSubmitAddRequest.getQuestionId() <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        LoginUser user = GetLoginUserUtils.getLoginUser();
        String token = request.getHeader("token");
        Long  questionSubmitId =  submitRecordsService.runQuestionSubmit(questionSubmitAddRequest, user, token);

        return new ResponseResult(200, "提交成功", questionSubmitId.toString());
    }
    @PreAuthorize("hasAuthority('vip')")
    @PostMapping("/getLanguageList")
    @ApiOperation("获取语言列表")
    public ResponseResult getLanguageList(){
        Map<Integer, String> languageConfig = QuestionSubmitLanguageEnum.getLanguageConfig();
        List<LanguageClass> collect = languageConfig.entrySet().stream().map(e -> {
            LanguageClass languageClass = new LanguageClass(e.getKey(), e.getValue());
            return languageClass;
        }).collect(Collectors.toList());
        return new ResponseResult(200, "获取语言限制", collect);
    }
    private static StringBuilder getCode(MultipartFile file) {
        StringBuilder codeSource = new StringBuilder();

        try {
            byte[] bytes = file.getBytes();
            String lines;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedReader readerIp = new BufferedReader(new InputStreamReader(inputStream));
            while((lines = readerIp.readLine()) != null ){
                codeSource.append(lines + "\n") ;

            }
            inputStream.close();
            readerIp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return codeSource;
    }

    @ApiOperation("给提交记录添加备注")
    @PostMapping("/updateTheSubmitAnnotation")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult updateTheSubmitAnnotation(String submitId, String annotation){
        SubmitRecords records = new SubmitRecords();
        records.setId(Long.valueOf(submitId));
        records.setAnnotation(annotation);
        boolean b = submitRecordsService.updateById(records);
        if(b == false) throw  new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败");
        return new ResponseResult<>(200, "更新成功", true);
}
    /**
     * 时间
     * 提交的状态 ； success fail
     * 语言 ：
     * 消耗时间 :
     * 消耗空间 ：
     * 备注 ：
     */
    @ApiOperation("查询一条提交记录的信息")
    @PostMapping("/getLatestSubmitMsg")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult  getLatestSubmitMsg (String submitId){
        LambdaQueryWrapper<SubmitRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubmitRecords::getId, submitId);
        SubmitRecords submitRecords =  submitRecordsService.getTheLatestMsg(wrapper);
        SubmitRecordsVo recordsVo = submitEntityToVo(submitRecords);
        return new ResponseResult(200, "获取成功",  recordsVo);
    }

    private static SubmitRecordsVo submitEntityToVo(SubmitRecords submitRecords) {
        SubmitRecordsVo recordsVo = new SubmitRecordsVo();
        BeanUtils.copyProperties(submitRecords, recordsVo);
        recordsVo.setJudgeInfo(JsonTransforUtils.JsonToObj(JudgeInfo.class, submitRecords.getJudgeInfo()));
        return recordsVo;
    }

    @ApiOperation("获取自己某人的所有提交记录")
    @PostMapping("/getSubmitRecord")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getSubmitRecord(String userId, String questionId, Integer page, Integer pageSize){
        //使用分页查询
        if(StrUtil.isEmpty( userId)) userId = String.valueOf(GetLoginUserUtils.getUserId());
        if(page != null){
            List<SubmitVo> rep =  submitRecordsService.queryPage(page, pageSize, userId, questionId);
            log.info(rep.toString());
            return new ResponseResult(200, "分页获取聊天记录", rep);
        }
        //  前面已经根据用户的id来获取到结果了
        List<SubmitVo> list = submitRecordsService.getSubmitRecordByUserIdAndQuestionId(userId, questionId);
        return new ResponseResult(200, "获取所有记录", list);
    }

    @ApiOperation("获取提交内容通过传递的Id")
    @PostMapping("/getSubmitContentById")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getSubmitContentById(String id){
        return new ResponseResult<>(200, "获取提交记录" ,submitRecordsService.getById(id));
    }

    /**
     * 获取随机的一条记录 （待优化）
     * @param request
     * @return
     */
    @ApiOperation("获取随机一道题目")
    @PostMapping("/getRandomProblem")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getRandomProblem(HttpServletRequest request){
        String token = request.getHeader("token");
        Long id =   questionService.getRandomProblem(request);
        return new ResponseResult<>(200, "获取随机的一条记录", id.toString());
    }

    @ApiOperation("上一道题和下一道题目")
    @PostMapping("/getQuestionNext")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getNextOrLast(String id, Integer direction){
        Long nId =  questionService.getNextOrLast(id, direction);
        return new ResponseResult(200, "获取成功", nId.toString());
    }

    @ApiOperation("获取profile页面中的提交记录")
    @PostMapping("/getProfileSubmit")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getProfileSubmit(){
        List<SubmitProfileVo> list =  questionService.getProfileSubmitList();
        return  new ResponseResult(200, "获取成功", list);
    }
}
