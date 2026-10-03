package com.zyh.commoncore.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zyh.commoncore.domain.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonUtilTest {

   @Test
    void obj2String() {
        Student student = new Student("123", "zhangsan", 18);
        System.out.println(JsonUtil.obj2String(student));
    }

    @Test
    void obj2StringPretty() {
        Student student = new Student("123", "zhangsan", 18);
        System.out.println(JsonUtil.obj2StringPretty(student));
    }

    @Test
    void string2Obj() {
        System.out.println(JsonUtil.string2Obj("{\"stuNum\":\"123\",\"name\":\"zhangsan\",\"age\":18}", Student.class));
    }

    @Test
    void List2String() {
        Student student = new Student("123", "zhangsan", 18);
        List<Student> students = new ArrayList<>();
        students.add(student);
        System.out.println(JsonUtil.obj2String(students));
    }

    @Test
    void String2List() {
        System.out.println(JsonUtil.string2List(
                "[{\"stuNum\":\"123\",\"name\":\"zhangsan\",\"age\":18}]", Student.class));
    }

    @Test
    void multi() {
        List<Map<String, Student>> mapList = new ArrayList<>();
        Map<String, Student> map = new HashMap<>();
        map.put("1", new Student("123", "zhangsan", 18));
        mapList.add(map);
        System.out.println(JsonUtil.obj2String(mapList));
    }

    @Test
    void multi2() {
        System.out.println(JsonUtil.string2Obj("[{\"1\":{\"stuNum\":\"123\",\"name\":\"zhangsan\",\"age\":18}}]",
                new TypeReference<List<Map<String, Student>>>() {
                }));
    }
}