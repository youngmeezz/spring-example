package org.demo.web;

import org.apache.ibatis.annotations.Delete;
import org.demo.model.Person;
import org.demo.exception.DuplicateValueException;
import org.demo.exception.NotFoundException;
import org.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
@RestController
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable(name = "id", required = false) String id) {
        try {
            logger.info("request get person. id : {}", id);
            return ResponseEntity.ok(personService.getPersonById(id));
        } catch (NotFoundException e) {
            logger.warn("NotFoundException. person id : {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.warn("Exception occur while find person", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> registerPerson(@RequestBody Person person) {
        try {
            logger.info("request register person : {}", person);
            if (personService.regist(person) < 1) {
                throw new Exception();
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateValueException e) {
            logger.warn("Failed to register person. duplicate id : {}", person.getId());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.warn("Exception occur while registering person", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/person")
    public ResponseEntity<Void> modifyPerson(@RequestBody Person person) {
        try {
            if (personService.modify(person) < 1) {
                throw new Exception();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Delete(value = "/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) {
        System.out.println("delete person is called..");
        try {
            if (personService.remove(id) < 1) {
                throw new Exception();
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}