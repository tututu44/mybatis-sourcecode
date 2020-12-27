package com.mybatis.source.past;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

interface MyUserMapper {
    @Select("select * from user where id=#{id} and name=#{name}")
    void getUserById(Integer id, String name);
}

public class SimpleMybatis {
    public static void main(String[] args) {
        MyUserMapper userMapper = (MyUserMapper) Proxy.newProxyInstance(Application.class.getClassLoader(),
                new Class<?>[]{MyUserMapper.class}, (proxy, method, args1) -> {
                    String sql = method.getAnnotation(Select.class).value()[0];
                    Parameter[] parameters = method.getParameters();
                    Map<String, Object> map = mapArgsAndKey(args1, parameters);
                    String parseSql = parseSql(sql, map);
                    System.out.println(parseSql);
                    return null;
                });
        userMapper.getUserById(1, "name");
    }

    private static String parseSql(String sql, Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '#') {
                int nextIndex = i + 1;
                if (sql.charAt(nextIndex) != '{') {
                    throw new RuntimeException(stringBuilder.toString() + "缺少{");
                }
                StringBuilder sb = new StringBuilder();
                i = getFieldOfSql(sb, sql, nextIndex+1);
                String value = String.valueOf(map.get(sb.toString()));
                if (value == null) {
                    throw new RuntimeException(stringBuilder.toString() + "缺少}");
                }
                stringBuilder.append(value);
                continue;
            }
            stringBuilder.append(sql.charAt(i));
        }
        return  stringBuilder.toString();
    }

    private static int getFieldOfSql(StringBuilder sb, String sql, int nextIndex) {
        for (; nextIndex  < sql.length(); nextIndex++) {
            if (sql.charAt(nextIndex) != '}') {
                sb.append(sql.charAt(nextIndex));
                continue;
            }
            break;
        }
        return nextIndex;
    }

    private static Map<String, Object> mapArgsAndKey(Object[] args1, Parameter[] parameters) {
        Map<String, Object> map = new HashMap<>(parameters.length);
        int index = 0;
        for (Parameter parameter : parameters) {
            map.put(parameter.getName(), args1[index++]);
        }
        return map;
    }
}
