package com.imooc.service.Impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example carousel = new Example(Carousel.class);
        carousel.orderBy("sort").desc();

        Example.Criteria carouselCriteria = carousel.createCriteria();
        carouselCriteria.andEqualTo("isShow",isShow);

        List<Carousel> result = carouselMapper.selectByExample(carousel);
        return result;
    }
}
