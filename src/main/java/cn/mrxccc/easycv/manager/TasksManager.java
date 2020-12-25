package cn.mrxccc.easycv.manager;

import cn.mrxccc.easycv.recorder.ImageRecord;

import java.io.IOException;
import java.util.List;

/**
 * 录制任务管理（可以从该处获录制器，并管理这些录制器）
 * @author mrxccc
 * @create 2020/12/16
 */
public interface TasksManager {

    /**
     * 获取一个新的录制器，如果为空表示工作线程已满
     * @param src -视频源
     * @param playUrl -输出地址（如果recordDir为空，以out为准）
     * @return
     * @throws IOException
     * @throws Exception
     */
    ImageRecord createRecorder(String src, String playUrl, Integer taskId) throws IOException, Exception;

    /**
     * 根据id获取录制任务
     * @param id
     * @return
     */
    ImageRecord getRecorderTask(Integer id);

    /**
     * 正在工作的录制任务列表
     * @return
     */
    List<ImageRecord> list();

    /**
     * 是否存在某个正在工作的任务（以src和out判断）
     * @return
     */
    boolean exist(String src,String out);

    /**
     * 停止并归还录像任务
     * @param task
     * @return
     */
    boolean stop(ImageRecord task);

    /**
     * 暂停
     * @param task
     * @return
     */
    boolean pause(ImageRecord task);

    /**
     * 继续（从暂停中恢复，停止后无法继续）
     * @param task
     * @return
     */
    boolean carryon(ImageRecord task);

    /**
     * 开始任务
     * @param task
     * @return
     */
    boolean start(ImageRecord task);

}
