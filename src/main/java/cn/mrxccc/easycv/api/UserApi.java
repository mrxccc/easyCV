package cn.mrxccc.easycv.api;

import cn.mrxccc.easycv.domain.ResponseCodeEnum;
import cn.mrxccc.easycv.dto.ResponseResult;
import cn.mrxccc.easycv.request.LoginRequest;
import cn.mrxccc.easycv.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@Tag(name = "user", description = "the user api")
@RequestMapping("/api")
public interface UserApi {
    @Operation(summary = "登录", tags = "用户管理")
    @ResponseBody
    @PostMapping("/user/login")
    public ResponseResult<UserVo> login(@RequestBody LoginRequest loginRequest);

    @Operation(summary = "用户信息", tags = "用户管理")
    @GetMapping("/user/info")
    public ResponseResult<UserVo> loginInfo(String token);

    @Operation(summary = "退出", tags = "用户管理")
    @PostMapping("/user/logout")
    public ResponseResult<String> Logout(@RequestBody LoginRequest loginRequest);
}
