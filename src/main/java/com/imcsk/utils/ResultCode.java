package com.imcsk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author csk
 * @date 2020-12-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCode {

    private int code;
    private Object data;
}
