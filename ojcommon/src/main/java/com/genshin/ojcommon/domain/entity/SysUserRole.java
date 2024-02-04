package com.genshin.ojcommon.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴嘉豪
 * @date 2023/11/17 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole {
    @TableId("user_id")
    public Long userId;
    public Long roleId;
}
