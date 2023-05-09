package com.test;

import com.imooc.ESApplication;
import com.imooc.es.pojo.Stu;
import com.imooc.es.pojo.StuBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用 ElasticsearchTemplate 对索引进行管理（创建索引，更新映射，删除索引）
     * 索引就像是数据库或者数据库中的表，我们平时是不会是通过java代码频繁的去创建修改删除数据库或者表的
     * 我们只会针对数据做CRUD的操作
     * 在es中也是同理，我们尽量使用 ElasticsearchTemplate 对文档数据做CRUD的操作
     * 1. 属性（FieldType）类型不灵活
     * 2. 主分片与副本分片数无法设置
     */

    @Test
    public void creat(){
        Stu stu = new StuBuilder().withStuId(1003L)
                .withAge(55)
                .withMoney(45.8f)
                .withName("three man")
                .withDescription("I have a three army")
                .withSign("I am three man").build();

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        String index = esTemplate.index(indexQuery);
        System.out.println(index);
    }

    @Test
    public void delete(){
        esTemplate.deleteIndex(Stu.class);
    }

    //-------------------------------------------------------------------

    /**
     * 更新
     */
    @Test
    public void updateStuDoc() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "I am not super man");
        sourceMap.put("money", 99.8f);
        sourceMap.put("age", 33);

        /**
         * 修改数据的核心对象IndexRequest和UpdateQuery
         */
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);

        /**
         * id不能变，这个id是es内部的id，不指定为随机字符串的id
         */
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1005")
                .withIndexRequest(indexRequest)
                .build();

//        update stu set sign='abc',age=33,money=88.6 where docId='1002'

        esTemplate.update(updateQuery);
    }

    /**
     * 简单查询
     */
    @Test
    public void getStuDoc() {

        GetQuery query = new GetQuery();
        query.setId("1005");
        Stu stu = esTemplate.queryForObject(query, Stu.class);

        System.out.println(stu);
    }

    /**
     * 删除
     */
    @Test
    public void deleteStuDoc() {
        esTemplate.delete(Stu.class, "1005");
    }

    @Test
    public void deleteIndex() {
        esTemplate.deleteIndex("foodie-items-ik");

    }

    //------------------------------------------------------------

    /**
     * 分页查询
     */
    @Test
    public void queryPage() {

        Pageable pageable = PageRequest.of(1, 2);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "I have a three army"))
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> stus = esTemplate.queryForPage(searchQuery, Stu.class);
        System.out.println(stus.getTotalPages());
        for (Stu stu : stus.getContent()) {
            System.out.println(stu);
        }
    }

    @Test
    public void highlightStuDoc() {
        //高亮的前后缀
        String preTag = "<font color='red'>";
        String postTag = "</font>";

        SortBuilder sortBuilder = new FieldSortBuilder("money")
                .order(SortOrder.DESC);
        SortBuilder sortBuilderAge = new FieldSortBuilder("age")
                .order(SortOrder.ASC);

        SearchResultMapper mapper = new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Stu> stuListHighlight = new ArrayList<>();

                for (SearchHit hit : response.getHits()) {
                    //根据断点数据得来
                    HighlightField description = hit.getHighlightFields().get("description");
                    String fragment = description.getFragments()[0].toString();

                    Object stuId = (Object)hit.getSourceAsMap().get("stuId");
                    String name = (String)hit.getSourceAsMap().get("name");
                    Integer age = (Integer)hit.getSourceAsMap().get("age");
                    String sign = (String)hit.getSourceAsMap().get("sign");
                    Object money = (Object)hit.getSourceAsMap().get("money");

                    Stu stuHL = new Stu();
                    stuHL.setDescription(fragment);
                    stuHL.setStuId(Long.valueOf(stuId.toString()));
                    stuHL.setName(name);
                    stuHL.setAge(age);
                    stuHL.setSign(sign);
                    stuHL.setMoney(Float.valueOf(money.toString()));

                    stuListHighlight.add(stuHL);
                }


                if (stuListHighlight.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>)stuListHighlight);
                }else {
                    return null;
                }
            }
        };

        Pageable pageable = PageRequest.of(1, 2);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                /**   实现分页  */
                .withQuery(QueryBuilders.matchQuery("description", "I have a three army"))
                /**   实现高亮  */
                .withHighlightFields(new HighlightBuilder.Field("description").preTags(preTag).postTags(postTag))
                .withSort(sortBuilder)
                .withSort(sortBuilderAge)
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> stus = esTemplate.queryForPage(searchQuery, Stu.class, mapper);

        System.out.println(stus.getTotalPages());
        for (Stu stu : stus.getContent()) {
            System.out.println(stu);
        }
    }


}
