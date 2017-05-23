package example.com.powerinterview;

/**
 * Created by Игорь on 12.04.2017.
 */
import org.junit.Test;

import example.com.powerinterview.utils.Encrypt;

import static org.junit.Assert.assertEquals;

public class EncryptUtilTest {



    @Test
    public void encryptByMD5() throws Exception {

        String value = "test";
        String encrypted = Encrypt.encryptMD5(value);
        assertEquals("098f6bcd4621d373cade4e832627b4f6", encrypted);
    }

}
