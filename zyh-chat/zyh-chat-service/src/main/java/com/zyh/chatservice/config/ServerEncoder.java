package com.zyh.chatservice.config;

import com.zyh.commoncore.utils.JsonUtil;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

/**
 * @author zhangyuheng
 */

public class ServerEncoder implements Encoder.Text<Object>{
    @Override
    public void destroy() {
        // 清理资源，如关闭文件等
    }

    @Override
    public void init(EndpointConfig arg0) {
        // 初始化编码器，可以读取配置参数
    }

    @Override
    public String encode(Object obj) {
        return JsonUtil.obj2String(obj);
    }
}
