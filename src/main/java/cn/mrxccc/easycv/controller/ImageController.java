package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.api.ImageApi;
import cn.mrxccc.easycv.config.MyProperties;
import cn.mrxccc.easycv.convert.ConvertMapper;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.domain.ResponseCodeEnum;
import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.serivce.ImgService;
import cn.mrxccc.easycv.vo.ImgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author mrxccc
 * @create 2020/12/15
 */
@Slf4j
@Controller
public class ImageController implements ImageApi {
    @Autowired
    private ImgService imgService;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private ConvertMapper convertMapper;

    @Override
    public ResponseResult<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return new ResponseResult(ResponseCodeEnum.FAILED.getCode(), "文件为空", null);
        }
        String filePath = myProperties.getImageDirPath();
        System.out.println(filePath);
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        log.info("转换后的文件名为：[{}]!!", fileName);
        File dest = new File(filePath + fileName);
        //判断父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest);
            Img img = new Img();
            img.setImgName(dest.getName());
            img.setImgPath(dest.getPath());
            img.setCreateTime(LocalDateTime.now());
            img.setUpdateTime(LocalDateTime.now());
            imgService.insertOrUpdate(img);
            return new ResponseResult(ResponseCodeEnum.SUCCESS.getCode(), "上传成功", null);
        } catch (IOException e) {
            log.error("上传文件过程中发生异常！", e);
        }
        return new ResponseResult(ResponseCodeEnum.FAILED.getCode(), "上传失败", null);
    }

    @Override
    public ResponseResult<List<ImgVo>> list() {
        return new ResponseResult(ResponseCodeEnum.SUCCESS.getCode(),
                convertMapper.imgRecordTaskDtoToImgVoList(imgService.selectImgRecordTask()));
    }

    @Override
    public ResponseResult<String> hasTask(String imageId) {
        if (imgService.hasTask(imageId) > 0){
            return new ResponseResult(ResponseCodeEnum.SUCCESS.getCode(),"已存在rtsp任务");
        }
        return new ResponseResult(ResponseCodeEnum.FAILED.getCode(),"未添加rtsp任务");
    }
}
