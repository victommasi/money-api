package com.money.api.service;

import com.money.api.model.Person;
import com.money.api.repository.PersonRepository;
import com.money.api.resource.PersonResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public void remove(Long id) {
        personRepository.delete(id);
    }

    public Person update(Long id, Person person) {
        Person savedPerson = findPersonById(id);
        BeanUtils.copyProperties(person, savedPerson, "id");
        return personRepository.save(savedPerson);
    }

    public void updateActive(Long id, Boolean active) {
        Person savedPerson = findPersonById(id);
        savedPerson.setActive(active);
        personRepository.save(savedPerson);
    }

    public Person findPersonById(Long id) {
        Person savedPerson = personRepository.findOne(id);
        if (savedPerson == null){
            throw new EmptyResultDataAccessException(1);
        }
        return savedPerson;
    }
}
