package com.wyc.api;

import com.wyc.common.domain.Person;
import com.wyc.common.dto.PersonDto;
import com.wyc.common.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestApi {

    @Autowired
    private PersonService personService;

    @RequestMapping(value="/test")
    public Object test2(HttpServletRequest httpServletRequest){
        PersonDto personDto = new PersonDto();
        personDto.setNameCondition("wyc2");
        List<Person> personList = personService.findAll(personDto);
        return personList;
    }

    @RequestMapping("/test2")
    public Object test(HttpServletRequest httpServletRequest){
        Person person = new Person();
        person.setName2("wyc");
        personService.add(person);
        Map<String,String> map = new HashMap<>();
        map.put("name","王玉川");
        map.put("age","21");
        return map;
    }
}
