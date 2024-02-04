package com.genshin.ojquestion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genshin.ojcommon.domain.po.Tags;
import com.genshin.ojquestion.mapper.TagsMapper;
import com.genshin.ojquestion.service.TagsService;
import org.springframework.stereotype.Service;

/**
 * @author 吴嘉豪
 * @date 2023/12/20 13:39
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {
}
