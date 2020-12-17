package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.dto.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mrxccc
 * @create 2020/12/17
 */
@Tag(name = "image", description = "the images api")
public interface ImageApi {

    @Operation(summary = "图片列表")
    @GetMapping("/images/list")
    String index(Model model);

    @PostMapping("/images/upload")
    @Operation(summary = "图片上传")
    ResponseResult<String> upload(@RequestParam("file") MultipartFile file);
}
