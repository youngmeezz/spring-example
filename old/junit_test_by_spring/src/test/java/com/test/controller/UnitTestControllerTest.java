package com.test.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.service.UnitTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/persistence-context.xml"
        })
public class UnitTestControllerTest {
    @Mock
    UnitTestService unitTestService;    
    @InjectMocks
    private UnitTestController unitTestController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(unitTestController).build();
    }
    
    @Test
    public void basicTest() throws Exception {
        when(unitTestService.testMethod()).thenReturn(10);
        assertTrue(unitTestService.testMethod() == 10);
        
        mockMvc.perform(get("/unit-test/test")).andExpect(status().isOk());
        
        /*MockHttpServletRequestBuilder request = get("/unit-test/test");
        request.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                 request.setAttribute("param", "param!!!");
                 return request;
            }
        });*/
    }
    
    @Test
    public void httpTest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/unit-test/test-http")
                                        .header("testHeader","headerValue")
                                        .content("test body");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());   
    }
    
    
}
