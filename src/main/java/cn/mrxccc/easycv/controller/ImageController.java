package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.serivce.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/15
 */
@RestController("/images")
public class ImageController {
    @Autowired
    private ImgService imgService;

    /**
     * 获取全部资源
     *
     * @return
     */
//    @GetMapping("/list")
//    public ResponseResult<List<Img>> list() {
//        return new ResponseResult<>(Integer.valueOf(HttpStatus.OK.value()), HttpStatus.OK.toString(), imgService.selectAll());
//    }
}
