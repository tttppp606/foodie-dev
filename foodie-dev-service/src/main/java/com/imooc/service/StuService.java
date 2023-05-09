package com.imooc.service;

import com.imooc.pojo.Stu;
import org.springframework.stereotype.Service;

@Service
public interface StuService {

    public Stu findStuInfo(int id);

    public String saveStuInfo(String name,int age);

    public String updateStu(int id);

    public void deleteStu(int id);


}
