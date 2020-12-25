package cn.mrxccc.easycv.vo;

import cn.mrxccc.easycv.domain.Img;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author mrxccc
 * @create 2020/12/23
 */
public class ImgRecordTask {
    private Pushers pushers;
    Img img;

    /**
     * 任务id
     */
    private Integer id;


    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
