package io.github.appaga.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import apgutil.UtilDate;
import io.github.appaga.config.SysProperty;
import lombok.Data;

/**
 * 기본 응답 클래스
 * 
 */
@Data
public class ResultData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int status = SysProperty.RESULT_OK;
    private String error = "";
    private String message = "";
    private String timestamp = UtilDate.getNowFullSSS();
    private String path = "";
    private Map<String, Object> data = new HashMap<>();
}
