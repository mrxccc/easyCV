package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.dto.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author mrxccc
 * @create 2020/12/17
 */
@Tag(name = "image", description = "the images api")
public interface ImageApi {

    @Operation(summary = "图片上传")
    @ResponseBody
    @PostMapping(value = "/images/upload", produces = { "application/json" })
    ResponseResult<String> upload(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "object", format = "binary"))) MultipartFile file);
}
