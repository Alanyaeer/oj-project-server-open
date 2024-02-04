package com.genshin.ojquestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.dto.question.QuestionAddRequest;
import com.genshin.ojcommon.domain.vo.QuestionManaReturnVo;
import com.genshin.ojcommon.domain.vo.QuestionReturnVo;
import com.genshin.ojcommon.domain.entity.Question;
import com.genshin.ojcommon.domain.vo.QuestionShowVo;
import com.genshin.ojcommon.domain.vo.SubmitProfileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/19 13:00
 */
public interface QuestionService extends IService<Question> {
    void uploadFile(String titleName, Integer score, String createBy);

    void removeByIdAndBuck(Long userId);

    boolean updateProblemStatus(Long userId, QuestionAddRequest questionAddRequest);

    List<QuestionManaReturnVo> showRequest(int page, int pageSize);

    List<QuestionReturnVo> showRequestForVip(int size, int sizePage);

    boolean passQuestion(String id);

    boolean removeByTitle(String titleName);

    boolean removeByIdU(String id);

    void saveFile(MultipartFile file);

    void uploadFileWithoutSub(MultipartFile file, Long userId);

    QuestionShowVo getQuestionContent(String id, String token);

    Question getByUserId(Long userId);

    QuestionShowVo getQuestionContentByTitleName(String titleName, String token);

    Integer getScoreById(Long questionId);

    List<Integer> getCountByWrapper();

    boolean isPassThisQuestionByUserId(Long questionId, Long userId);

    Long getRandomProblem(HttpServletRequest request);

    Long getNextOrLast(String id, Integer direction);

    List<SubmitProfileVo> getProfileSubmitList();
}
