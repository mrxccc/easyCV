package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.cache.DefaultCache;
import cn.mrxccc.easycv.cache.RecordInfoStorage;
import cn.mrxccc.easycv.serivce.RecordServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Service("recordService")
@Slf4j
public class RecordServiceImpl extends RecordServiceTemplate {

    @Override
    public RecordInfoStorage initCache() {
        DefaultCache cache = new DefaultCache(maxSize);
        return cache;
    }

}
