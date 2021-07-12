package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.vo.ImgVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/17
 */
@Tag(name = "image", description = "the images api")
@RequestMapping("/api")
public interface ImageApi {

    @Operation(summary = "图片上传")
    @ResponseBody
    @PostMapping(value = "/images/upload")
    ResponseResult<String> upload(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "object", format = "binary"))) MultipartFile[] files);

    @Operation(summary = "图片列表")
    @ResponseBody
    @GetMapping(value = "/images/list", produces = { "application/json" })
    ResponseResult<List<ImgVo>> list();

    @Operation(summary = "图片删除")
    @ResponseBody
    @PostMapping(value = "/images/delete")
    ResponseResult<String> delete(@RequestBody List<Integer> imageIds);

    @Operation(summary = "图片是否存在任务")
    @ResponseBody
    @GetMapping(value = "/images/hasTask")
    ResponseResult<String> hasTask(@RequestParam Integer imageId);
}
