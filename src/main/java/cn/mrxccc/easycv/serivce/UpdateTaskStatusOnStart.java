package cn.mrxccc.easycv.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author mrxccc
 * @create 2020/12/25
 */
@Component
@Slf4j
public class UpdateTaskStatusOnStart implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private ImgRecordTaskService imgRecordTaskService;

    @Autowired
    EasyDarwin easyDarwin;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("UpdateTaskStatusOnStart开始执行");
        imgRecordTaskService.updateStatusAllDate();
        log.info("UpdateTaskStatusOnStart结束执行");
    }
}
