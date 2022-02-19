package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author xiaofei_dev
 * @date 2022/2/19
 */
public class MapDemo {
    //测试代码
    public static void main(String[] args) {
        LinkedHashMap<String, ArrayList<String>> map =
                new LinkedHashMap<>();

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("卧室1-1");
        list1.add("卧室1-2");
        list1.add("厨房-1");
        list1.add("客厅-3");
        map.put("TV1", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("卧室1-1");
        list2.add("厨房-2");
        list2.add("客厅-3");
        list2.add("客厅-2");
        map.put("TV2", list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("厨房-1");
        list3.add("客厅-3");
        list3.add("走廊-1");
        list3.add("走廊-2");
        map.put("TV3", list3);

        LinkedHashMap<String, ArrayList<String>> result =
                mapUtils(map);

        //正确结果应为：{卧室1-1=[TV2, TV1], 卧室1-2=[TV1], 厨房-1=[TV3, TV1], 客厅-3=[TV3, TV2, TV1], 厨房-2=[TV2], 客厅-2=[TV2], 走廊-1=[TV3], 走廊-2=[TV3]}
        System.out.println(result);
    }

    /**
     * 使用时只要把【源 map】传给此方法就行
     * @param mapA：源 map
     * @return 反向映射的 map
     */
    public static LinkedHashMap<String, ArrayList<String>> mapUtils(LinkedHashMap<String, ArrayList<String>> mapA){
        //待返回的结果
        LinkedHashMap<String, ArrayList<String>> resultMap = new LinkedHashMap<>();

        //反向映射操作
        //resultMap 以 mapA 中 value 列表中的字符串为 key，mapA 中原来的 key 为 value
        ArrayList<String> valueList = new ArrayList<>();
        for (ArrayList<String> values : mapA.values()) {
            valueList.addAll(values);
        }
        /*HashSet<String> set = new HashSet<>(valueList);
        valueList.clear();
        valueList.addAll(set);*/
        Collections.sort(valueList);
        for (String str : valueList) {
            if (!resultMap.containsKey(str)){
                ArrayList<String> list = new ArrayList<>();
                resultMap.put(str, list);
            }
        }

        for (String key : mapA.keySet()) {
            for (String value : Objects.requireNonNull(mapA.get(key))) {
                if (resultMap.containsKey(value)){
                    Objects.requireNonNull(resultMap.get(value)).add(key);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(key);
                    resultMap.put(value, list);
                }
            }
        }

        //去重操作
        for (String key : resultMap.keySet()) {
            ArrayList<String> list = resultMap.get(key);
            if (list == null) continue;
            HashSet<String> setValues = new HashSet<>(list);
            list.clear();
            list.addAll(setValues);
        }
        return resultMap;
    }
}
