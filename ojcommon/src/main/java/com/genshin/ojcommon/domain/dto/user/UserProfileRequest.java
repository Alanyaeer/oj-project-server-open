package com.genshin.ojcommon.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 吴嘉豪
 * @date 2024/1/27 23:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest implements Serializable {
    private String id;
    private String description;
    private String avatar;
    private String nickName;
    private String sex;
    private String email;
    private String phoneNumber;
    private LocalDateTime birthdate;

}
