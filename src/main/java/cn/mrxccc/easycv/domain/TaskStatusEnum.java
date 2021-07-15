package cn.mrxccc.easycv.domain;

/**
 * @author mrxccc
 * @create 2021/7/15
 * @since 1.0.0
 */
public enum TaskStatusEnum {
    WORKING(1),
    STOP(-1),
    ORIGINAL(0);
    private Integer code;

    TaskStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
