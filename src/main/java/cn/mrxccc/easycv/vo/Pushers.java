package cn.mrxccc.easycv.vo;

import lombok.Data;

/**
 * @author mrxccc
 * @create 2020/12/22
 */
@Data
public class Pushers {
    /**
     * id
     */
    String id;
    /**
     * 路径
     */
    String path;
    /**
     * 播放地址
     */
    String url;
    /**
     * 源地址
     */
    String source;
    /**
     * 传输模式：TCP/UDP
     */
    String transType;
    /**
     * 入口流量
     */
    Long inBytes;
    /**
     * 出口流量
     */
    Long outBytes;
    /**
     * 开始时间
     */
    String startAt;
    /**
     * 在线人数
     */
    Integer onlines;
}
