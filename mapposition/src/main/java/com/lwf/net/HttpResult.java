package com.lwf.net;

/**
 * @param <T>
 * @author wqr
 * @Description: 网络结果集 公共部分
 * @date 2014-11-7 下午2:51:42
 */
public class HttpResult<T> {

    /**
     * 结果码
     */
    private int code;

    /**
     * 接口名称
     */
    private String msg;

    /**
     * 返回数据体
     */
    private T body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public boolean isSucc() {
        return 0 == this.code && getBody() != null;
    }

    public boolean isSuccIgnoreBody() {
        return 0 == this.code;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("HttpResult [code=").append(getCode()).append(", ")
            .append(getMsg() != null ? "msg=" + getMsg() + ", " : "").append(getBody() != null ? "data=" + getBody() : "").append("]")
            .toString();
    }
}
