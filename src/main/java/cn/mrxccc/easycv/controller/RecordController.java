package cn.mrxccc.easycv.controller;

import java.util.List;

import cn.mrxccc.easycv.api.RecordApi;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.entity.RecordInfo;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.serivce.RecordService;
import cn.mrxccc.easycv.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Controller
@RequestMapping("/record")
@Slf4j
public class RecordController implements RecordApi {
    @Autowired
    RecordService recorderService;

    /**
     * 视频录像
     *
     * @return
     */
    @Override
    @ResponseBody
    public ResponseResult start(@RequestParam(required = true) String src, @RequestParam(required = true) String out) {
        ResponseResult<Object> result = new ResponseResult<>();
        if (CommonUtil.isAllNullOrEmpty(src, out)) {
            result.setMessage("失败");
            return result;
        }

        RecordTask rt = null;
        try {
            rt = recorderService.record(src, out);
            if (rt != null) {
                result.setState(1);
                result.setMessage(rt.getId().toString());
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        result.setMessage("失败");
        return result;
    }

    /**
     * 停止录像
     *
     * @return
     */
    @Override
    @ResponseBody
    public ResponseResult stop(@RequestParam(required = true) Integer id) {
        ResponseResult<Object> result = new ResponseResult<>();
        result.setState(0);
        try {
            if (recorderService.stop(id)) {
                result.setState(1);
                result.setMessage("成功");
                return result;
            }
        } catch (Exception e) {
        }
        result.setMessage("失败");
        return result;
    }

    /**
     * 列表
     *
     * @param isWork
     * @return
     */
    @Override
    @ResponseBody
    public List<?> list(@RequestParam(required = false) String isWork) {
        boolean flag = (isWork != null && isWork.length() > 0 && isWork.equals("true")) ? true : false;
        return recorderService.list(flag);
    }

    /**
     * 根据id查询
     *
     * @return
     */
    @Override
    @Operation(method = "根据id查询",tags = "录制管理")
    @ResponseBody
    @GetMapping("/get")
    public ResponseResult get(@RequestParam(required = true) Integer id) {
        ResponseResult<RecordInfo> result = new ResponseResult<>();
        result.setState(0);
        if (CommonUtil.isNull(id)) {
            result.setMessage("id不能为空");
            return result;
        }
        RecordInfo info = recorderService.get(id);
        System.err.println(info);
        if (info != null) {
            result.setData(info);
            return result;
        }
        result.setMessage("失败");
        return result;
    }
}
