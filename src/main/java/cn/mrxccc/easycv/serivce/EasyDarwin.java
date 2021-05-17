package cn.mrxccc.easycv.serivce;

import cn.mrxccc.easycv.config.MyProperties;
import cn.mrxccc.easycv.query.PushersQuery;
import cn.mrxccc.easycv.vo.EasyDarwinPushers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrxccc
 * @create 2020/12/21
 * @since 1.0.0
 */

@Service
public class EasyDarwin {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MyProperties myProperties;

    /**
     * 默认没有参数会使用get方法
     *
     * @return
     */
    public EasyDarwinPushers getPushers(PushersQuery pushersQuery) {
        ResponseEntity<EasyDarwinPushers> forEntity = restTemplate.getForEntity(myProperties.getEasyDarwinUrl() + "pushers", EasyDarwinPushers.class, pushersQuery);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            return forEntity.getBody();
        } else {
            throw new RuntimeException("EasyDarwin Pushers 接口异常");
        }
    }
}
