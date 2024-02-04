package com.genshin.ojcommon.domain.dto.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴嘉豪
 * @date 2023/12/19 12:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "minio上传文件返回的桶名和文件名")
public class MiniosEntity {
    public Long size;


    @ApiModelProperty(name = "桶名", value = "buckName")
    public String buckName;
    @ApiModelProperty(name = "文件名", value = "fileName")
    public String fileName;

    public MiniosEntity(String s, String s1) {
        this.buckName = s;
        this.fileName = s1;
    }
}
