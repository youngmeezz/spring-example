package web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.demo.exception.DuplicateValueException;
import org.demo.exception.NotFoundException;
import org.demo.model.Person;
import org.demo.service.PersonService;
import org.demo.util.GsonUtil;
import org.demo.web.PersonController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author zacconding
 * @Date 2018-09-13
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/root-context.xml", "classpath:spring/persistence-context.xml"})
public class PersonControllerTest {

    @Mock
    PersonService personService;
    @InjectMocks
    PersonController personController;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // @InjectMocks anno
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build(); // runnig standalone
    }

    @Test
    public void getPersonAndThenOk() throws Exception {
        String id = "1";
        when(personService.getPersonById(id)).thenReturn(new Person(id, "hivava", 10));

        this.mockMvc.perform(get("/person/" + id))
                    .andExpect(status().isOk());
    }

    @Test
    public void getPersonAndThenNotFound() throws Exception {
        String id = "1";
        when(personService.getPersonById(id)).thenThrow(new NotFoundException());

        this.mockMvc.perform(get("/person/" + id))
                    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void registerAndThenCreated() throws Exception {
        Person p1 = new Person("1", "hivava", 10);
        when(personService.regist(p1)).thenReturn(1);

        this.mockMvc.perform(post("/person").content(GsonUtil.toString(p1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    public void registerAndThenFail() throws Exception {
        Person p1 = new Person("1", "hivava", 10);
        when(personService.regist(p1)).thenReturn(0);

        this.mockMvc.perform(post("/person").content(GsonUtil.toString(p1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    public void registerAndThenBadRequest() throws Exception {
        Person p1 = new Person("1", "hivava", 10);
        when(personService.regist(p1)).thenThrow(new DuplicateValueException());

        this.mockMvc.perform(post("/person").content(GsonUtil.toString(p1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void modifyAndThenCorrect() throws Exception {
        Person p1 = new Person("1", "hivava", 10);
        when(personService.modify(p1)).thenReturn(1);

        this.mockMvc.perform(put("/person").content(GsonUtil.toString(p1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void modifyAndThenException() throws Exception {
        Person p1 = new Person("1", "hivava", 10);
        when(personService.modify(p1)).thenReturn(0);

        this.mockMvc.perform(put("/person").content(GsonUtil.toString(p1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test // DELETE NOT SUPPORT
    public void deleteAndThenCorrect() throws Exception {
        String id = "123";
        when(personService.remove("123")).thenReturn(1);

        this.mockMvc.perform(delete("/person/123"))
                    .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test // DELETE NOT SUPPORT
    public void deleteAndThenFail() throws Exception {
        String id = "123";
        when(personService.remove(id)).thenReturn(0);

        this.mockMvc.perform(delete("/person/{}", id))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    /*
    @Delete(value = "/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) {
        try {
            if (personService.remove(id) > 0) {
                throw new Exception();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
     */


}
