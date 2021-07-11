package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.api.RecordApi;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.domain.ResponseCodeEnum;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/21
 */
@Controller
@Slf4j
public class ImageRecordController implements RecordApi {
    @Autowired
    ImgRecordTaskService imgRecordTaskService;

    @Autowired
    EasyDarwin easyDarwin;

    /**
     * 增加图片录像
     *
     * @return
     */
    @Override
    public ResponseResult<String> addImgRecord(@RequestParam Integer imageId) {
        try {
            imgRecordTaskService.recordImgByImgId(imageId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseResult<>(ResponseCodeEnum.FAILED.getCode(),"failed");
        }
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(),"success");
    }

    /**
     * 图片录像列表
     *
     * @return
     */
    @Override
    public ResponseResult<List<ImgRecordTaskDto>> list() {
        try {
            return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(), imgRecordTaskService.selectImgRecordTask());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ResponseResult<List<ImgRecordTaskDto>>(ResponseCodeEnum.FAILED.getCode(), new ArrayList<>());
    }

    /**
     * 图片录像
     *
     * @return
     */
    @Override
    public ResponseResult<String> startImgRecordTask(@RequestParam Integer taskId) {
        ResponseResult<String> responseResult = new ResponseResult();
        try {
            boolean result = imgRecordTaskService.startImgRecordTask(taskId);
            if (result){
                responseResult.setState(ResponseCodeEnum.SUCCESS.getCode());
                responseResult.setMessage("开启成功");
            } else {
                responseResult.setState(ResponseCodeEnum.FAILED.getCode());
                responseResult.setMessage("开启失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("开启任务失败");
            responseResult.setData(null);
            responseResult.setState(ResponseCodeEnum.FAILED.getCode());
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
    @Override
    public ResponseResult<String> stopImgRecordTask(@RequestParam Integer taskId) {
        ResponseResult<String> responseResult = new ResponseResult();
        try {
            boolean result = imgRecordTaskService.stopImgRecordTask(taskId);
            if (result){
                responseResult.setState(ResponseCodeEnum.SUCCESS.getCode());
                responseResult.setMessage("关闭成功");
            } else {
                responseResult.setState(ResponseCodeEnum.FAILED.getCode());
                responseResult.setMessage("关闭失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("开启任务失败");
            responseResult.setData(null);
            responseResult.setState(ResponseCodeEnum.FAILED.getCode());
            responseResult.setMessage(e.getMessage());
            return responseResult;
        }

        return responseResult;
    }
}
