package com.bintohimo.fakeapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Test
    public void getStatus() throws Exception {
        mockMvc.perform(get("/v1")).andExpect(status().isOk());
    }

    @Test
    public void postEcho() throws Exception {
        testEcho(RequestMethod.POST, "random string");
    }

    @Test
    public void putEcho() throws Exception {
        testEcho(RequestMethod.PUT, "random string");
    }

    @Test
    public void postEchoUrl() throws Exception {
        testEchoUrl(RequestMethod.POST, "random_string");
    }

    @Test
    public void putEchoUrl() throws Exception {
        testEchoUrl(RequestMethod.PUT, "random_string");
    }

    @Test
    public void getEchoUrl() throws Exception {
        testEchoUrl(RequestMethod.GET, "random_string");
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
