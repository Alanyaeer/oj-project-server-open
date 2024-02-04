package com.genshin.ojquestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.po.Testcase;
import com.genshin.ojcommon.dto.CaseDto;
import com.genshin.ojquestion.mapper.TestCaseMapper;
import com.genshin.ojquestion.service.TestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 吴嘉豪
 * @date 2024/1/1 19:14
 */
@Service
public class TestcaseServiceImpl extends ServiceImpl<TestCaseMapper, Testcase> implements TestcaseService {
    @Autowired
    private TestCaseMapper testCaseMapper;


    @Override
    public List<CaseDto> selectById(String id, int i) {
        LambdaQueryWrapper<Testcase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Testcase::getQid, id);
        queryWrapper.eq(Testcase::getType, i);
        List<Testcase> list = list(queryWrapper);
        List<CaseDto> collect = list.stream().map(e -> {
            CaseDto dto = new CaseDto();
            String inputText = e.getInputText();
            String outputText = e.getOutputText();
            dto.setInputText(inputText);
            dto.setOutputText(outputText);
            return dto;
//            return text;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void insertById(String id, String s, int type, String os) {
        Testcase testcase = new Testcase();
        testcase.setQid(Long.valueOf(id));
        testcase.setInputText(s);
        testcase.setOutputText(os);
        testcase.setType(type);
        testCaseMapper.insert(testcase);
    }
}
