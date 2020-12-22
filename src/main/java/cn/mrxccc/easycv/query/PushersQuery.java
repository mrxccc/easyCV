package cn.mrxccc.easycv.query;

import lombok.Data;

/**
 * @author mrxccc
 * @create 2020/12/22
 */
@Data
public class PushersQuery {
    /**
     * 分页开始,从零开始
     */
    Integer start;
    /**
     * 分页大小
     */
    Integer limit;
    /**
     * 排序字段
     */
    String sort;
    /**
     * 排序顺序
     * 允许值: ascending, descending
     */
    String order;
    /**
     * 查询参数
     */
    String q;
}
