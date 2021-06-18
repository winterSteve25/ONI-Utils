package wintersteve25.oniutils.common.lib.helper;

public class MiscHelper {
    public static final int INT_MAX = 2147483647;

    public static String langToReg(String lang) {
        String reg = lang.toLowerCase().replace(' ', '_').replace('-', '_');
        return reg;
    }
}
