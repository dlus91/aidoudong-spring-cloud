import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: dlus91
 * @Date: 2020/10/29 22:39
 */
public class Test01 {

    @Test
    public void test01(){
        System.out.println(new BCryptPasswordEncoder().encode("123456"));

    }
}
