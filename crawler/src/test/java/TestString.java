import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akuo on 2017/4/28.
 */
public class TestString {
    private static String test ;
    private static Map<String, String> map;
    public static void main(String[] args) {
        test = "llll";
        initString(test);
        System.out.println(test);
        map = new HashMap<String, String>();
        map.put("ss","dd");
        initMap(map);
        System.out.println(map);
    }

    private static void initString(String s){
        s = "hahha";
    }
    private static void initMap(Map map){
        map.put("test", "jjjj");
    }
}
