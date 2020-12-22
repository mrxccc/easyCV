package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.query.PushersQuery;
import cn.mrxccc.easycv.serivce.EasyDarwin;
import cn.mrxccc.easycv.serivce.impl.ImgRecordServiceImpl;
import cn.mrxccc.easycv.vo.EasyDarwinPushers;
import cn.mrxccc.easycv.vo.Pushers;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * @author mrxccc
 * @create 2020/12/21
 */
@Controller
@RequestMapping("/imgRecord")
@Slf4j
public class ImageRecordController {
    @Autowired
    ImgRecordServiceImpl imgRecordService;

    @Autowired
    EasyDarwin easyDarwin;


    /**
     * 图片录像
     *
     * @return
     */
    @ResponseBody
    @Operation(summary = "开始图片录像",tags = "录制管理")
    @PostMapping("/imageRecord")
    public ResponseResult<Pushers> imageRecordById(@RequestParam(required = true) Integer imageId, Model model) {
        ResponseResult<Pushers> result = new ResponseResult<>();
        Integer taskId = 0;
        try {
            ImgRecordTask imgRecordTask = imgRecordService.recordImgById(imageId);
            taskId = imgRecordTask.getId();
            EasyDarwinPushers easyDarwinPushers = easyDarwin.getpPushers(new PushersQuery());
            Optional<Pushers> pushers = easyDarwinPushers.getRows().parallelStream().filter((e) -> e.getPath().equals(imgRecordTask.getPlayUrl())).findFirst();
            if (pushers.isPresent()) {
                result.setState(1);
                result.setMessage(imgRecordTask.getId().toString());
                result.setData(pushers.get());
                model.addAttribute("result", result);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (taskId != 0){
                imgRecordService.stop(taskId);
            }
        }
        result.setMessage("失败");
        model.addAttribute("result", result);
        return result;
    }

    /**
     * 图片录像列表
     *
     * @return
     */
    @ResponseBody
    @Operation(summary = "图片录像列表",tags = "录制管理")
    @PostMapping("/imgRecordList")
    public ResponseResult<EasyDarwinPushers> imgRecordList(Model model) {
        ResponseResult<EasyDarwinPushers> result = new ResponseResult<>();
        RecordTask rt = null;
        try {
            EasyDarwinPushers easyDarwinPushers = imgRecordService.list(null);
            result.setState(1);
            result.setMessage(rt.getId().toString());
            result.setData(easyDarwinPushers);
            model.addAttribute("result", result);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        result.setMessage("失败");
        model.addAttribute("result", result);
        return result;
    }
}
