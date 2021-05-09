package wintersteve25.oniutils.common.lib.helper;

public class TextHelper {
    public static String langToReg(String lang) {
        String reg = lang.toLowerCase().replace(' ', '_');
        return reg;
    }
}
