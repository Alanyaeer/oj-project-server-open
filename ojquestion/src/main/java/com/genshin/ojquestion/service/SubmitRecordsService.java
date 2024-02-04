package com.genshin.ojquestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.dto.SubmitRecordsDto;
import com.genshin.ojcommon.domain.dto.question.QuestionSubmitAddRequest;
import com.genshin.ojcommon.domain.entity.LoginUser;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.vo.SolveVo;
import com.genshin.ojcommon.domain.vo.SubmitVo;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 15:32
 */
public interface SubmitRecordsService extends IService<SubmitRecords> {
    void insert(String id, String location, Integer status, Long lastReason, Integer lastCase, Long titleId);

    List<SubmitRecordsDto> queryById(Long id, Long titleId);

    List<SubmitRecordsDto> queryByIdWithoutTitleId(Long id);

    Long runQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, LoginUser user, String token);

    List<SolveVo> getSolveVoByScore(int score, Long userId);

    List<SolveVo> getPassMessage(long userId);

    SubmitRecords getTheLatestMsg(LambdaQueryWrapper<SubmitRecords> wrapper);

    List<SubmitVo> getSubmitRecordByUserIdAndQuestionId(String userId, String questionId);

    List<SubmitVo> queryPage(Integer page, Integer pageSize, String userId, String questionId);

    SubmitRecords getLatestSubmit(String userId);
}
