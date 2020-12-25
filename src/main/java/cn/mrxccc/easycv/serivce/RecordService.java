package cn.mrxccc.easycv.serivce;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.query.PushersQuery;
import cn.mrxccc.easycv.vo.EasyDarwinPushers;

/**
 * @author mrxccc
 * @create 2020/12/22
 */
public interface RecordService {

    ImgRecordTask recordImgById(Integer imageId);

    EasyDarwinPushers list(PushersQuery pushersQuery);
}
