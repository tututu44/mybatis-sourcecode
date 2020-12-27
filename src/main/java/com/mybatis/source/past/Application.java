package com.mybatis.source.past;


import org.apache.ibatis.annotations.Select;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

interface UserMapper{
    @Select("select * from user where id=#{id}")
    void getUserById(Integer id);
}
public class Application {
    public static void main(String[] args) {
        UserMapper userMapper =(UserMapper) Proxy.newProxyInstance(Application.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Map<String, Object> handle = handle(method, args);
                System.out.println(handle);
                System.out.println(method.getName());
                System.out.println(method.getAnnotation(Select.class).value()[0]);
                Set<String> keySet = handle.keySet();
                keySet.forEach(key->{
                    System.out.println(key);
                    System.out.println(handle.get(key));
                    System.out.println(method.getAnnotation(Select.class).value()[0].
                            replace("#{id}",String.valueOf(handle.get(key))));
                });
                return null;
            }
        });
        userMapper.getUserById(1);
    }
    public static Map<String,Object> handle(Method method, Object[] args){
        Map<String,Object> map = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        int[] index = {0};
        Arrays.asList(parameters).forEach(parameter -> {
            System.out.println(parameter.getType());
            map.put(parameter.getName(),args[index[0]]);
           index[0]++;
        });
        return map;
    }
}
