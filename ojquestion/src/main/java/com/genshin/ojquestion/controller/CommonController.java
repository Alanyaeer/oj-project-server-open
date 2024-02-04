package com.genshin.ojquestion.controller;

import com.genshin.ojcommon.domain.dto.minio.MiniosEntity;
import com.genshin.ojcommon.common.ResponseResult;
import com.genshin.ojquestion.utils.MinioUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/19 11:53
 */
@Api(tags = "公共文件上传接口")
@Slf4j
@RestController
@RequestMapping("/questionAndPictrueCommon")
public class CommonController {
    @Autowired
    private MinioUtils minioUtils;
    @Value("${oj.minio.end-point}")
    private String endPoint;

    /**
     *
     * @param file
     * @return
     * @note 用户上传图片或者其他的文件
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult uploadFile(MultipartFile file){
        List<String> list = minioUtils.upload(new MultipartFile[]{file});
        return new ResponseResult<MiniosEntity>(200, "上传成功", new MiniosEntity("oj-question-file", list.get(0) ));
    }
    // 暂时只能这么做了，临时
    @GetMapping("/download")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult getFileUrl(String buckName, String fileName){
        return new ResponseResult(200, "请求成功",  endPoint + "/" + buckName + "/" + fileName);
    }

    @PostMapping("/uploadAndGetUrl")
    @PreAuthorize("hasAuthority('vip')")
    public ResponseResult GetImgUrl(MultipartFile file){
//        file.getBytes().var
        List<String> list = minioUtils.upload(new MultipartFile[]{file});
        String s = list.get(0);
        return new ResponseResult<>(200, "上传成功", endPoint + "/" + "oj-question-file" + "/" + s);
    }
}
