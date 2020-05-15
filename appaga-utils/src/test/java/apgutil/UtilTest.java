package apgutil;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
    @Test
    public void test1() {
        Assert.assertEquals("2020.05.09", UtilString.getDoubleString("2020.05.09"));
        Assert.assertEquals("20200509", UtilString.getIntString("2020.05.09"));
        Assert.assertEquals("123456789", UtilString.getIntString("123,456,789원"));
    }
}