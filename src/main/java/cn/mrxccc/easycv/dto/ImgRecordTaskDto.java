package cn.mrxccc.easycv.dto;

import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import lombok.Data;

/**
 * @author mrxccc
 * @create 2020/12/23
 */
@Data
public class ImgRecordTaskDto {
    public ImgRecordTask imgRecordTask;
    public Img img;
}
