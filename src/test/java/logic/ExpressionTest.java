package logic;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExpressionTest extends Assert {

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][] {
                {"V + III", "VIII"},
                {"X - III", "VII"},
                {"X / V", "II"},
                {"III * II", "VI"},
                {"X * X", "C"},
                {"3 + 7", "10"},
                {"10 - 2", "8"},
                {"9 / 3", "3"},
                {"2 * 4", "8"},
                {"10 * 6", "60"},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][] {
                {"dgasdg"},
                {"19 / 3"},
                {"XTT - V"},
                {"TTX * V"},
                {"5 * V"},
                {"0 + 7"},
                {"5 / 0"},
        };
    }

    @Test(dataProvider = "positiveData")
    public void ExpPositiveTest(String exp, String result) {
        assertEquals(new Expression(exp).calculate(), result);
    }

    @Test(dataProvider = "negativeData", expectedExceptions = Exception.class)
    public void ExpNegativeTest(String exp) {
        new Expression(exp).calculate();
    }
}
