package com.mybatis.source.now.xml;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;
    private String name;
    private Date createTime;
    private String password;
    private String phone;
    private String nickName;
}
