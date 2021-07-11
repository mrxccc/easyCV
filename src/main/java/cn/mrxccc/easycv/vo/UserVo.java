package cn.mrxccc.easycv.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserVo {
    String name;
    String[] roles;
    String introduction;
    String avatar;
    String token;
}
