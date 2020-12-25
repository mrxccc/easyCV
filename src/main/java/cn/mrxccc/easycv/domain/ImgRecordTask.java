package cn.mrxccc.easycv.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "img_record_task")
public class ImgRecordTask {
    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC", strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图片id
     */
    @Column(name = "imageId")
    private Integer imageid;

    /**
     * 执行状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 播放地址
     */
    @Column(name = "play_url")
    private String playUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 任务结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
}