package cn.mrxccc.easycv.cache;

import cn.mrxccc.easycv.entity.RecordInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 自定义持久化
 *
 * @author mrxccc
 * @create 2020/12/16
 */
public class DefaultCache implements RecordInfoStorage {
    protected List<RecordInfo> historylist = null;

    public DefaultCache(int maxSize) {
        historylist = new ArrayList<>(maxSize);
    }

    @Override
    public synchronized List<RecordInfo> list() {
        return historylist;
    }

    @Override
    public synchronized boolean save(RecordInfo info) {
        return info == null ? false : historylist.add(info);
    }

    @Override
    public synchronized RecordInfo get(int id) {
        RecordInfo info = new RecordInfo(id, null, null);
        int ret = Collections.binarySearch(historylist, info, new Comparator<RecordInfo>() {
            @Override
            public int compare(RecordInfo o1, RecordInfo o2) {
                if (o1.getId().equals(o2.getId())) {
                    return 0;
                }
                return -1;
            }
        });
        if (ret >= 0) {
            return historylist.get(ret);
        }
        return null;
    }

}
