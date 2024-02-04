package com.genshin.ojquestion.controller;

import com.genshin.ojcommon.domain.dto.TagListDto;
import com.genshin.ojcommon.common.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴嘉豪
 * @date 2023/12/10 15:04
 */
@Slf4j
@RestController
@RequestMapping("/agTemplate")
@Api(tags = "算法模板相关接口")
public class AgTemplateController {

    /**
     *
     * @param file
     * @return
     * @apiNote 上传算法模板的接口, 算法模板不需要添加图片，所以到时候，我们需要吧图片给抹掉
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传算法模板")
    @PreAuthorize("hasAuthority('vip')")
    @ApiImplicitParam(value = "file")
    public ResponseResult uploadAg(MultipartFile file){
        return null;
    }

    /**
     *
     * @param tagListDto
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation(value = "提交算法模板")
    @PreAuthorize("hasAuthority('vip')")
    @ApiImplicitParam(value = "tagListDto")
    public ResponseResult submitAg(@RequestBody TagListDto tagListDto){
        return null;
    }
    /**
     *
     * @return 算法模板列表显示接口
     */
    @ApiOperation("算法模板列表显示接口")
//    @PreAuthorize("('vip')")
    @GetMapping("/showTep")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "page", name = "页数"),
            @ApiImplicitParam(value = "pageSize", name = "每页的长度"),
            @ApiImplicitParam(value = "name", name = "模板名称", required = false)
    })
    public ResponseResult showAg(int page, int pageSize, String name){

        return null;
    }
    /**
     * @param id
     * @return 算法模板列表显示接口
     * @apiNote 算法模板编号id
     */
    @ApiOperation("算法模板选择接口")
    @PreAuthorize("('vip')")
    @GetMapping("/TepById")
    @ApiImplicitParam(value = "id", name = "算法模板id")
    public ResponseResult showAg(Long id){

        return null;
    }
}
