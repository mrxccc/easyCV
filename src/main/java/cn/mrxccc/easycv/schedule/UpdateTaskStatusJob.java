package cn.mrxccc.easycv.schedule;

import cn.mrxccc.easycv.serivce.ImgRecordTaskService;
import cn.mrxccc.easycv.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mrxccc
 * @create 2021/7/14
 */

@Component
@Slf4j
public class UpdateTaskStatusJob {
    @Autowired
    private ImgRecordTaskService imgRecordTaskService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void updateTaskStatusJob() {
        log.info("updateTaskStatusJob exec" + DateUtil.now());
        try {
            log.info("UpdateTaskStatusOnStart开始执行");
            imgRecordTaskService.updateStatusAllDate();
            log.info("UpdateTaskStatusOnStart结束执行");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
