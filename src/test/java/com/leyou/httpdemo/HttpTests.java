package com.leyou.httpdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.httpdemo.pojo.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 
 *
 **/
public class HttpTests {
    private Logger logger = LoggerFactory.getLogger(HttpTests.class);

    private CloseableHttpClient httpClient;
    // json处理工具
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void testGet() throws IOException {
        HttpGet request = new HttpGet("http://www.baidu.com");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }

    @Test
    public void testPost() throws IOException {
        HttpGet request = new HttpGet("http://www.oschina.net/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }

    //对象转json
    @Test
    public void testJson() throws JsonProcessingException {
        User user = new User();
        user.setId(8L);
        user.setAge(21);
        user.setName("柳岩");
        user.setUserName("liuyan");
        // 序列化
        String json = mapper.writeValueAsString(user);
        System.out.println("json = " + json);
    }

    @Test
    public void testGetPojo() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/hello/1");
        //此时结果是一个json字符串
        String userJson = this.httpClient.execute(request, new BasicResponseHandler());
        logger.info(userJson);
        //反序列化
        User user = mapper.readValue(userJson, User.class);
        System.out.println(user);
    }

    @Test
    public void testGetPojoAll() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/users");
        //此时结果是一个json字符串
        String usersJson = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(usersJson);
        //反序列化
        // 反序列化，接收两个参数：json数据，反序列化的目标类字节码
        List<User> users = mapper.readValue(usersJson, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        for (User u : users) {
            System.out.println("u = " + u);
        }
    }


}
