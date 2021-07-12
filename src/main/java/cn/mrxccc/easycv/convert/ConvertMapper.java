package cn.mrxccc.easycv.convert;


import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.util.DateUtil;
import cn.mrxccc.easycv.vo.ImgVo;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class, DateUtil.class})
public interface ConvertMapper {

    @Mappings({
            @Mapping(target = "name", source = "imgName"),
            @Mapping(target = "path", source = "imgPath"),
            @Mapping(target = "uploadTime",
                    expression = "java(DateUtil.format(img.getCreateTime()))")
    })
    ImgVo imgToImgVo(Img img);

    @Mappings({
            @Mapping(target = "id", source = "imgRecordTaskDto.img.id"),
            @Mapping(target = "name", source = "imgRecordTaskDto.img.imgName"),
            @Mapping(target = "path", source = "imgRecordTaskDto.img.imgPath"),
            @Mapping(target = "uploadTime",
                    expression = "java(DateUtil.format(imgRecordTaskDto.getImg().getCreateTime()))"),
            @Mapping(target = "taskNum",
                    expression = "java(imgRecordTaskDto.getImgRecordTask()==null?0:1)")
    })
    ImgVo imgRecordTaskDtoToImgVo(ImgRecordTaskDto imgRecordTaskDto);

    List<ImgVo> imgRecordTaskDtoToImgVoList(List<ImgRecordTaskDto> imgRecordTaskDto);

    List<ImgVo> imgToImgVoList(List<Img> imgList);

}
