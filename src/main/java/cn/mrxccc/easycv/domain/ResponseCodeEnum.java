package cn.mrxccc.easycv.domain;

import lombok.Data;

public enum ResponseCodeEnum {
    SUCCESS(20000),FAILED(-1);
    private Integer code;

    ResponseCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
