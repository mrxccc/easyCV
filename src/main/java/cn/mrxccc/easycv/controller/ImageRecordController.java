package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.serivce.EasyDarwin;
import cn.mrxccc.easycv.serivce.ImgRecordTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/21
 */
@Controller
@RequestMapping("/imgRecord")
@Slf4j
public class ImageRecordController {
    @Autowired
    ImgRecordTaskService imgRecordTaskService;

    @Autowired
    EasyDarwin easyDarwin;

    /**
     * 增加图片录像
     *
     * @return
     */
    @Operation(summary = "开始图片录像", tags = "录制管理")
    @PostMapping("/addImgRecord")
    public String addImgRecord(@RequestParam Integer imageId) {
        Integer taskId = 0;
        try {
            imgRecordTaskService.recordImgByImgId(imageId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (taskId != 0) {
                imgRecordTaskService.stopByTaskId(taskId);
            }
        }
        return "redirect:/imgRecord/list";
    }

    /**
     * 图片录像列表
     *
     * @return
     */
    @Operation(summary = "图片录像列表", tags = "录制管理")
    @GetMapping("/list")
    public String EasyDarwinPushers(Model model) {
        try {
            List<ImgRecordTaskDto> imgRecordTaskDtos = imgRecordTaskService.selectImgRecordTask();
            model.addAttribute("imgRecordTaskDtos", imgRecordTaskDtos);
            return "pusherList";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "pusherList";
    }

    /**
     * 图片录像
     *
     * @return
     */
    @Operation(summary = "开始图片录像", tags = "录制管理")
    @ResponseBody
    @PostMapping("/startImgRecordTask")
    public ResponseResult<String> startImgRecordTask(@RequestParam Integer taskId) {
        ResponseResult<String> responseResult = new ResponseResult();
        try {
            boolean result = imgRecordTaskService.startImgRecordTask(taskId);
            if (result){
                responseResult.setState(0);
                responseResult.setMessage("开启成功");
            } else {
                responseResult.setState(1);
                responseResult.setMessage("开启失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("开启任务失败");
            responseResult.setData(null);
            responseResult.setState(-1);
            responseResult.setMessage(e.getMessage());
            return responseResult;
        }

        return responseResult;
    }

    /**
     * 停止图片录像
     *
     * @return
     */
    @Operation(summary = "停止图片录像", tags = "录制管理")
    @ResponseBody
    @PostMapping("/stopImgRecordTask")
    public ResponseResult<String> stopImgRecordTask(@RequestParam Integer taskId) {
        ResponseResult<String> responseResult = new ResponseResult();
        try {
            boolean result = imgRecordTaskService.stopImgRecordTask(taskId);
            if (result){
                responseResult.setState(0);
                responseResult.setMessage("关闭成功");
            } else {
                responseResult.setState(1);
                responseResult.setMessage("关闭失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("开启任务失败");
            responseResult.setData(null);
            responseResult.setState(-1);
            responseResult.setMessage(e.getMessage());
            return responseResult;
        }

        return responseResult;
    }
}
