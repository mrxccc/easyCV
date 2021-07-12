package cn.mrxccc.easycv.controller;

import cn.mrxccc.easycv.api.UserApi;
import cn.mrxccc.easycv.domain.ResponseCodeEnum;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.request.LoginRequest;
import cn.mrxccc.easycv.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController implements UserApi {
    @PostMapping("/user/login")
    @Override
    public ResponseResult<UserVo> login(@RequestBody LoginRequest loginRequest){
        log.info(loginRequest.toString());
        UserVo userVo = UserVo.of("SuperAdmin", new String[]{"admin"}, "I am a super administrator", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", "adminToken");
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(), userVo);
    }

    @GetMapping("/user/info")
    @Override
    public ResponseResult<UserVo> loginInfo(String token){
        log.info(token);
        UserVo userVo = UserVo.of("SuperAdmin", new String[]{"admin"}, "I am a super administrator", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", "adminToken");
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(), userVo);
    }

    @PostMapping("/user/logout")
    @Override
    public ResponseResult<String> Logout(@RequestBody LoginRequest loginRequest){
        log.info(loginRequest.toString());
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(), "success");
    }
}
