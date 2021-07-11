package cn.mrxccc.easycv.dto;

import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mrxccc
 * @create 2020/11/27
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 8486468806063544444L;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(Integer state) {
        super();
        this.code = state;
    }

    public ResponseResult(Integer state, String message) {
        super();
        this.code = state;
        this.message = message;
    }

    public ResponseResult(Integer state, Throwable throwable) {
        super();
        this.code = state;
        this.message = throwable.getMessage();
    }

    public ResponseResult(Integer state, T data) {
        super();
        this.code = state;
        this.data = data;
    }

    public ResponseResult(Integer state, String message, T data) {
        super();
        this.code = state;
        this.message = message;
        this.data = data;
    }


    public Integer getState() {
        return code;
    }

    public void setState(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResponseResult<?> other = (ResponseResult<?>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseResult [state=" + code + ", message=" + message + ", data=" + data + "]";
    }
}
