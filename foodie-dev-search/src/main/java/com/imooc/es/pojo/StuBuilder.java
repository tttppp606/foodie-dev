package com.imooc.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 测试使用
 */
public class StuBuilder {
    @Id
    private Long stuId;

    @Field(store = true)
    private String name;

    @Field(store = true)
    private Integer age;

    @Field(store = true)
    private Float money;

    @Field(store = true, type = FieldType.Keyword)
    private String sign;

    @Field(store = true)
    private String description;

    public StuBuilder withStuId(Long stuId){
        this.stuId = stuId;
        return this;
    }

    public StuBuilder withName(String name){
        this.name = name;
        return this;
    }

    public StuBuilder withAge(Integer age){
        this.age = age;
        return this;
    }

    public StuBuilder withMoney(Float money){
        this.money = money;
        return this;
    }

    public StuBuilder withSign(String sign){
        this.sign = sign;
        return this;
    }

    public StuBuilder withDescription(String description){
        this.description = description;
        return this;
    }


    public StuBuilder withObject(Stu stu){
        this.withStuId(stu.getStuId())
                .withName(stu.getName())
                .withAge(stu.getAge())
                .withMoney(stu.getMoney())
                .withSign(stu.getSign())
                .withDescription(stu.getDescription());
        return this;
    }

    public Stu build(){
        Stu stu = new Stu(stuId, name, age, money, sign, description);
        return stu;
    }
}
