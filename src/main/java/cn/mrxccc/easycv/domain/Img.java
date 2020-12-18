package cn.mrxccc.easycv.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "img")
public class Img {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 图片名称
     */
    @Column(name = "img_name")
    private String imgName;

    /**
     * 图片路径
     */
    @Column(name = "img_path")
    private String imgPath;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改日期
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}