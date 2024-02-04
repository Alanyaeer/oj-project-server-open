package com.genshin.ojcommon.domain.dto.codeRun;

import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeRequest;
import com.genshin.ojcommon.domain.dto.codesandbox.ExecuteCodeResponse;

/**
 * @author 吴嘉豪
 * @date 2024/1/16 23:20
 */
public interface CodeRun {
    ExecuteCodeResponse run (ExecuteCodeRequest request);
}
