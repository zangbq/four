package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jk.model.VideoBean;
import com.jk.repostry.CurriculumRepostory;
import com.jk.service.ConsumerService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequestMapping("findEs")
@Controller
public class EsController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private CurriculumRepostory curriculumRepostory;

    @RequestMapping("queryVideo")
    @ResponseBody
    public JSONObject queryVideo(Integer page, Integer rows, VideoBean video){
        System.out.println("==================================="+video.getCourseName());
        JSONObject result = new JSONObject();
        Client client = elasticsearchTemplate.getClient();
      //  Integer startIndex = rows*(page-1);

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("ordeo").setTypes("orvideo");
        if(video.getCourseName() !=null && video.getCourseName() != "" ){
            searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("courseName", video.getCourseName())));
        }
      //  searchRequestBuilder.setFrom(startIndex).setSize(rows);
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("courseName");
        highlightBuilder.preTags("<font color='red' >");
        highlightBuilder.postTags("</font>");
        searchRequestBuilder.highlighter(highlightBuilder);

        SearchResponse searchResponse = searchRequestBuilder.get();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();

        Iterator<SearchHit> iterator = hits.iterator();

        List<VideoBean> list = new ArrayList<VideoBean>();

        while (iterator.hasNext()){
            SearchHit next = iterator.next();
            Map<String, HighlightField> highlightFields = next.getHighlightFields();

            String sourceAsString = next.getSourceAsString();
            HighlightField videoname = highlightFields.get("courseName");
            VideoBean videoBean = JSON.parseObject(sourceAsString, VideoBean.class);
            //取得定义的高亮标签
            if(videoname !=null) {
                Text[] fragments = videoname.fragments();
                //为thinkName（相应字段）增加自定义的高亮标签
                String title = "";
                for (Text text1 : fragments) {
                    title += text1;
                }
                videoBean.setCourseName(title);
            }
            list.add(videoBean);
        }
        result.put("total",total);
        result.put("rows",list);
        return result;
    }

    @Scheduled(fixedRate = 2000)//定时器注解
    @ResponseBody
    public void testTasks(){
        List<VideoBean> list = consumerService.findVideo();
       // System.out.println("==========================定时器执行任务===========================");
        for (VideoBean user:list){
            curriculumRepostory.save(user);
        }
    }


}
