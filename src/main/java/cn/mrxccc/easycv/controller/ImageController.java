package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.api.ImageApi;
import cn.mrxccc.easycv.config.MyProperties;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.serivce.ImgService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
    /**
     * 获取全部资源
     *
     * @return
     */
    @Operation(summary = "图片列表")
    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @Operation(summary = "图片列表")
    @GetMapping("/images/list")
    public String imagesList(Model model) {
        model.addAttribute("imageList", imgService.selectAll());
        return "imagelist";
    }


    @Override
    public ResponseResult<String> upload(MultipartFile file) {
        String filePath= myProperties.getImageDirPath();
        System.out.println(filePath);
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
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
            return new ResponseResult(0, "上传成功", null);
        } catch (IOException e) {
            log.error("上传文件过程中发生异常！", e);
        }
        return new ResponseResult(-1, "上传失败", null);
    }
}
