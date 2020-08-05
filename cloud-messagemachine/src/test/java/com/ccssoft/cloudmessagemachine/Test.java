package com.ccssoft.cloudmessagemachine;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put(1,1);
        System.out.println(map.toString());
        map.remove(1);
        System.out.println(map.toString());
        map.put(1,(Integer)map.get(1)+1);
    }
}
