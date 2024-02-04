package com.genshin.ojquestion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.po.TestcaseReason;
import com.genshin.ojquestion.mapper.TestcaseReasonMapper;
import com.genshin.ojquestion.service.TestcaseReasonService;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 15:32
 */
@Service
public class TestcaseReasonServiceImpl extends ServiceImpl<TestcaseReasonMapper, TestcaseReason> implements TestcaseReasonService {
}
