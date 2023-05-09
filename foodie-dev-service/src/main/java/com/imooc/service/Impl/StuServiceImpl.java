package com.imooc.service.Impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuMapper stuMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu findStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public String saveStuInfo(String name, int age) {
        Stu stu = new Stu();
        stu.setAge(age);
        stu.setName(name);
        int insert = stuMapper.insert(stu);
        if(insert != 0){
            return "200";
        }
        return "500";
    }

    @Override
    public String updateStu(int id) {
        Stu stu = new Stu();
        stu.setAge(1);
        stu.setName("doubao");
        stu.setId(id);
        stuMapper.updateByPrimaryKey(stu);
        return "200";
    }

    @Override
    public void deleteStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stuMapper.deleteByPrimaryKey(stu);
    }
}
