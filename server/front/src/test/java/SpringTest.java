import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by renwp on 2016/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class SpringTest {
    @Test
    public void te1(){
        System.out.println("1111111111");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar createTimeCalendar = Calendar.getInstance();
        createTimeCalendar.setTime(new Date());
        createTimeCalendar.add(Calendar.DAY_OF_MONTH,30);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,29);

        System.out.printf("00000000000000000000"+sdf.format(createTimeCalendar.getTime())+"00000000000000000000");
        System.out.printf("11111111111111111111111"+sdf.format(calendar.getTime())+"11111111111111111111");
    }
}
