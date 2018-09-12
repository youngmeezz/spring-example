package org.demo.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:config/root-context.xml"})
public class ReceiveControllerTest {

    @InjectMocks
    private GuideController receiveController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // @InjectMocks anno
        mockMvc = MockMvcBuilders.standaloneSetup(receiveController).build(); // runnig standalone
    }

    @Test
    public void receive() throws Exception {
        // request "/guide/first GET" ==> expected "SUCCESS"
        this.mockMvc.perform(get("/guide/first"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("SUCCESS"));
    }
}