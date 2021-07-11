package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.dto.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/18
 */
@Tag(name = "record", description = "the record api")
@RequestMapping("/api")
public interface RecordApi {

    /**
     * 视频录像
     *
     * @return
     */
    @Operation(summary = "添加图片录像", tags = "录制管理")
    @ResponseBody
    @PutMapping("/record/add")
    ResponseResult<String> addImgRecord(@RequestParam Integer imageId);

    /**
     * 停止录像
     *
     * @return
     */
    @Operation(summary = "停止图片录像", tags = "录制管理")
    @ResponseBody
    @PostMapping("/record/stop")
    ResponseResult<String> stopImgRecordTask(@RequestParam Integer taskId);

    /**
     * 列表
     *
     * @param
     * @return
     */
    @Operation(summary = "图片录像列表", tags = "录制管理")
    @ResponseBody
    @GetMapping("/record/list")
    ResponseResult<List<ImgRecordTaskDto>> list();

    /**
     * 根据id查询
     *
     * @return
     */
    @Operation(summary = "开始图片录像", tags = "录制管理")
    @ResponseBody
    @PostMapping("/record/start")
    ResponseResult<String> startImgRecordTask(@RequestParam Integer taskId);
}
