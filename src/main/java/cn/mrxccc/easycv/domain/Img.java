package cn.mrxccc.easycv.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "img")
public class Img {
    /**
     * 主键id
     */
    @Id
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
    private Date createTime;

    /**
     * 修改日期
     */
    @Column(name = "update_time")
    private Date updateTime;
}