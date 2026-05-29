import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.crypto.SecureUtil;
import com.sunl19ht.client.pojo.entity.CarTypePrice;
import com.sunl19ht.utils.JWTUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;

public class TestFunction {
    @Test
    public void test(){
        // JWTUtils is now a Spring bean; in production use @Autowired.
        // For testing, create instance manually with test config values.
        System.out.println("JWT test — use Spring context in integration tests");
    }

    @Test
    public void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        System.out.println(time);
        System.out.println(timestamp);
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void md5() {
        String password = "test_password";
        System.out.println(SecureUtil.md5(password));
        System.out.println("JWT creation — use Spring context in integration tests");
    }

    @Test
    public void date() {
        LocalDateTime pickupTime = LocalDateTime.now();
        // 将 LocalDateTime 转换为 Date
        Date date = Date.from(pickupTime.atZone(ZoneId.systemDefault()).toInstant());
        Week week = DateUtil.dayOfWeekEnum(date); // 获取星期枚举
        if (week == Week.SATURDAY || week == Week.SUNDAY) {
            System.out.println("今天是周末！");
        } else {
            System.out.println("今天是工作日！");
        }
    }

    @Test
    public void hashSetTest() {
        LinkedHashSet<CarTypePrice> carTypePrices = new LinkedHashSet<>();
        carTypePrices.add(new CarTypePrice("A", new BigDecimal("100")));
        carTypePrices.add(new CarTypePrice("B", new BigDecimal("200")));
        carTypePrices.add(new CarTypePrice("C", new BigDecimal("300")));
        carTypePrices.add(new CarTypePrice("D", new BigDecimal("400")));
        carTypePrices.add(new CarTypePrice("D", new BigDecimal("100")));
        System.out.println(carTypePrices);
    }
}
