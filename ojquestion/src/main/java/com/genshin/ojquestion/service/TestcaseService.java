package com.genshin.ojquestion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genshin.ojcommon.domain.po.Testcase;
import com.genshin.ojcommon.dto.CaseDto;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/1 19:14
 */
public interface TestcaseService extends IService<Testcase> {
    List<CaseDto> selectById(String id, int i);

    void insertById(String id, String s, int type, String os);
}
