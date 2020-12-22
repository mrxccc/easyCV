package cn.mrxccc.easycv.serivce;

import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.manager.DefaultTasksManager;
import cn.mrxccc.easycv.manager.TasksManager;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author mrxccc
 * @create 2020/12/21
 */
public abstract class ImgRecordServiceTemplate implements ImageRecordService {
    @Value("${record.maxsize:10}")
    protected Integer maxSize;

    protected TasksManager manager = null;

    public static volatile int status = 0;

    public synchronized void init() {
        try {
            if (manager == null) {
                manager = new DefaultTasksManager(maxSize);
                status = 1;
            }
        } catch (Exception e) {
            status = -1;
        }
    }

    public boolean stop(int id) {
        init();
        RecordTask task = manager.getRecorderTask(id);
        return manager.stop(task);
    }
}
