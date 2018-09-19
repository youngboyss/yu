package com.example.toutiao.service;

import com.example.toutiao.dao.NewsDAO;
import com.example.toutiao.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId,int offset,int limit) {
           return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }


}
