package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.dto.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/18
 */
@Tag(name = "record", description = "the record api")
public interface RecordApi {
    /**
     * 图片录像
     *
     * @return
     */
    @Operation(summary = "图片录像",tags = "录制管理")
    @PostMapping("/imageRecord")
    public ResponseResult imageRecord(@RequestParam(required = true) String src, @RequestParam(required = true) String out);

    /**
     * 视频录像
     *
     * @return
     */
    @Operation(summary = "视频录像",tags = "录制管理")
    @PostMapping("/videoRecord")
    public ResponseResult videoRecord(@RequestParam(required = true) String src, @RequestParam(required = true) String out);

    /**
     * 停止录像
     *
     * @return
     */
    @Operation(method = "停止录像",tags = "录制管理")
    @GetMapping("/stop")
    public ResponseResult stop(@RequestParam(required = true) Integer id);

    /**
     * 列表
     *
     * @param isWork
     * @return
     */
    @Operation(method = "列表",tags = "录制管理")
    @GetMapping("/list")
    public List<?> list(@RequestParam(required = false) String isWork);

    /**
     * 根据id查询
     *
     * @return
     */
    @Operation(method = "根据id查询",tags = "录制管理")
    @GetMapping("/get")
    public ResponseResult get(@RequestParam(required = true) Integer id);
}
