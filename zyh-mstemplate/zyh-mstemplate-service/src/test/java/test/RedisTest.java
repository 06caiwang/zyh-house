package test;

import com.zyh.commonredis.service.RedisService;
import com.zyh.mstemplateservice.MsTemplateServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangyuheng
 */
@SpringBootTest(classes = MsTemplateServiceApplication.class)
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    void test() {
        redisService.addMember("key1", "123", "34523");
    }
}
