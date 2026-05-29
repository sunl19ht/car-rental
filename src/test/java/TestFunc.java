import java.time.Duration;

import java.time.LocalDateTime;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class TestFunc {
    @Test
    public void test() {
        LocalDateTime pickTime = LocalDateTime.of(2025, 2, 12, 12, 30);
        LocalDateTime returnTime = LocalDateTime.of(2025, 2, 13, 11, 20);

        // 计算时间差
        Duration duration = Duration.between(pickTime, returnTime);

        // 获取总小时数
        long totalHours = duration.toHours();

        // 计算天数和剩余小时
        long days = totalHours / 24;
        long hours = totalHours % 24;

        System.out.println("相差时间：" + days + " 天 " + hours + " 小时");
    }
}
