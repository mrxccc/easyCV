package cn.mrxccc.easycv.vo;

import lombok.Data;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/22
 */
@Data
public class EasyDarwinPushers {
    /**
     * 总数
     */
    Integer total;

    List<Pushers> rows;
}
