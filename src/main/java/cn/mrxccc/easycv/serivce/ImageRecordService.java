package cn.mrxccc.easycv.serivce;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.query.PushersQuery;
import cn.mrxccc.easycv.vo.EasyDarwinPushers;

import java.io.IOException;

/**
 * @author mrxccc
 * @create 2020/12/22
 */
public interface ImageRecordService {

    RecordTask record(String src, String out) throws IOException, Exception;

    EasyDarwinPushers list(PushersQuery pushersQuery);
}
