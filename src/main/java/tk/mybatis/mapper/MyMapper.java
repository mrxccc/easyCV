package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 自己的 Mapper
 * 特别注意，该接口不能被扫描到，否则会出错
 * @author mrxccc
 * @create 2020/11/27
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
