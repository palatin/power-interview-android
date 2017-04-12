package example.com.powerinterview;

/**
 * Created by Игорь on 12.04.2017.
 */
import org.junit.Test;

import example.com.powerinterview.utils.Encrypt;

import static org.junit.Assert.assertEquals;

public class EncryptUtilTest {

    @Test
    public void encryptByRSA() throws Exception {

        String key = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgHOoX09T5v73EA1LxaVrDX0KniW7\n" +
                "sCqaP8MSkGMwtXXgvbAUjnBJFfKbnW/UDw3xuE7wSPYp47TrPLHpMfaFF9pQJ3O9\n" +
                "nnuEjhj3cKBDawLsMX8BYusCHqnVg8zU7EEiH2Cxj7aaex/qSAZJCXw5okVox9fY\n" +
                "mjeTU55e5LDUATINAgMBAAE=";
        String value = "test";
        String encrypted = Encrypt.encryptByRSA(key, value);
        //assertEquals("R0MBuNDlfAZC9wqUrh6s4En9yeGcS0lE2dGNBlNDV4KsjjFaCdW92rA4Zm2VcNbmdl9emyBNSUTI5INp1ZXKk/BW66mVJSRCHCR3YE1QZHg" +
        //        "/WNMDLEXAyGU45ceOb+Rkoee+4hRepnJm0O3T5+IQbLfFcMATLdQUxySTKPjLArQ=", encrypted);
    }

    @Test
    public void encryptByMD6() throws Exception {

        String value = "test";
        String encrypted = Encrypt.encryptMD5(value);
        assertEquals("098f6bcd4621d373cade4e832627b4f6", encrypted);
    }

}
