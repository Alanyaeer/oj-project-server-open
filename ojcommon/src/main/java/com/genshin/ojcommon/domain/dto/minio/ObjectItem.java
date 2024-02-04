package com.genshin.ojcommon.domain.dto.minio;

import lombok.Data;

@Data
public class ObjectItem {
    private String objectName;
    private Long size;
}

