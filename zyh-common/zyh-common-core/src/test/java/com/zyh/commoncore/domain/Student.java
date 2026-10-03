package com.zyh.commoncore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyuheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String stuNum;

    private String name;

    private int age;
}
