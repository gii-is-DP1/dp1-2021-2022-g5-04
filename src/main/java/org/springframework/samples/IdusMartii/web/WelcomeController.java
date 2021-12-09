package org.springframework.samples.IdusMartii.web;

import java.util.Date;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.samples.IdusMartii.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WelcomeController {


      @GetMapping({"/","/welcome"})
      public String welcome(Map<String, Object> model, HttpServletResponse response) {
        response.addHeader("Refresh","30"); 
        model.put("now", new Date());
        List<Person> persons = new ArrayList<Person>();
        Person person_1 = new Person();
        person_1.setFirstName("Pablo ");
        person_1.setLastName("Santos");
        persons.add(person_1);
        Person person_2 = new Person();
        person_2.setFirstName("Antonio Roberto ");
        person_2.setLastName("Serrano");
        persons.add(person_2);
        Person person_3 = new Person();
        person_3.setFirstName("David ");
        person_3.setLastName("Sabugueiro");
        persons.add(person_3);
        Person person_4 = new Person();
        person_4.setFirstName("José Ramón ");
        person_4.setLastName("Arias");
        persons.add(person_4);
        Person person_5 = new Person();
        person_5.setFirstName("Juan Carlos ");
        person_5.setLastName("Moreno");
        persons.add(person_5);
        Person person_6 = new Person();
        person_6.setFirstName("Manuel ");
        person_6.setLastName("Carnero");
        persons.add(person_6);

        model.put("persons", persons);
        model.put("title", "Idus Martii"); 
        model.put("group", "L5-4");
        

       
        return "welcome";
      }
}