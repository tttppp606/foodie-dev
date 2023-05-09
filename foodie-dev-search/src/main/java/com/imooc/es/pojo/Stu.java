package com.imooc.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 测试使用
 */
@Data
@Document(indexName = "stu", type = "_doc", shards = 3, replicas = 0)
public class Stu {
    @Id/*如果不加@Id，es会自动生成一个随机字符串作为Id*/
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

    //用于Builder的构造函数
    public Stu(Long stuId,String name,Integer age,Float money,String sign,String description){
        this.age = age;
        this.description = description;
        this.money = money;
        this.sign = sign;
        this.stuId = stuId;
        this.name = name;
    }

    public Stu() {

    }
}
