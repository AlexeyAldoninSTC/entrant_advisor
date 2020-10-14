package ru.innopolis.project.InMemoryDB;

import java.util.HashMap;
import java.util.Map;

public class MainT {
    public static void main(String[] args) {
        InMemoryDataBase<Object,Object> dataBase = new InMemoryDataBase<>();



        Map<String, Integer> map1 = new HashMap<String, Integer>() {{
            put("Str1", 1);
            put("Str2", 2);
            put("Str3", 3);
        }};
        Map<String, Object> map2 = new HashMap<String, Object>() {{
            put("Str4", 6);
            put("Str5", "StrNoName");
            put("Str6", new Object());
        }};
//        Map<String, Integer> map3 = new HashMap<String, Integer>() {{
//            put("Str111", 111);
//            put("Str222", 222);
//            put("Str333", 333);
//        }};
        Map<String,Map<String,Object>> map3 = new HashMap<>();


        Map<String,Object> map1InMap3 = new HashMap<String, Object>(){{
            put("Str10", 10);
            put("Str20", 20);
            put("Str33", 30);
        }};

        Map<String,Object> map2InMap3 = new HashMap<String, Object>(){{
            put("Str100", 100);
            put("Str200", 200);
            put("Str330", 300);
        }};

        map3.put("M1",map1InMap3);
        map3.put("M2",map2InMap3);

        dataBase.save("Att1", map1);
        dataBase.save("Att2", map2);
        dataBase.save("Att3", map3);

        System.out.println(dataBase.getMap("Att1"));
        System.out.println(dataBase.getMap("Att2"));
        System.out.println(dataBase.getMap("Att3"));

        System.out.println(dataBase.delete("Att3"));
        System.out.println(dataBase.getMap("Att1"));
        System.out.println(dataBase.update("Att1",new HashMap()));
        System.out.println(dataBase.getMap("Att1"));

    }
}
