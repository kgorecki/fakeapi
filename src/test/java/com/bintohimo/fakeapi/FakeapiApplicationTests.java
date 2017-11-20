package com.bintohimo.fakeapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FakeapiApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Value("${url.path}")
    private String urlPath;

    @Test
    public void get200() throws Exception {
        mockMvc.perform(get(urlPath + "/get200")).andExpect(status().isOk());
    }

    @Test
    public void get404() throws Exception {
        mockMvc.perform(get(urlPath + "/get404")).andExpect(status().isNotFound());
    }

    @Test
    public void echo() throws Exception {
        RequestMethod[] methodList = {RequestMethod.POST, RequestMethod.PUT};
        for (int i = 0; i < methodList.length; i++)
            testEcho(methodList[i], "random string");
    }

    @Test
    public void echoUrl() throws Exception {
        RequestMethod[] methodList = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET};
        for (int i = 0; i < methodList.length; i++)
            testEchoUrl(methodList[i], "random_string");
    }

    private void testEcho(RequestMethod rm, String content) throws Exception {
        RequestBuilder rb = null;
        if (rm == RequestMethod.PUT)
            rb = put("/v1/echo").content(content);
        else if (rm == RequestMethod.POST)
            rb = post("/v1/echo").content(content);
        echo(rb, content);
    }

    private void testEchoUrl(RequestMethod rm, String content) throws Exception {
        RequestBuilder rb = null;
        if (rm == RequestMethod.GET)
            rb = get("/v1/echo/" + content);
        else if (rm == RequestMethod.PUT)
            rb = put("/v1/echo/" + content);
        else if (rm == RequestMethod.POST)
            rb = post("/v1/echo/" + content);
        echo(rb, content);
    }

    private void echo(RequestBuilder rb, String content) throws Exception {
        MvcResult result = mockMvc.perform(rb).andExpect(status().isOk()).andReturn();
        if (!result.getResponse().getContentAsString().equals(content))
            throw new Exception("Wrong response!");
    }
}
