package com.money.api.resource;

import com.money.api.event.CreatedResourceEvent;
import com.money.api.model.Person;
import com.money.api.repository.PersonRepository;
import com.money.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonResource {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Person> listAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id){
        Person person = personRepository.findOne(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person,
                                         HttpServletResponse response){
        Person savedPerson = personService.save(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        personService.remove(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person){
        Person savedPerson = personService.update(id, person);
        return savedPerson != null ? ResponseEntity.ok(savedPerson) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateActive(@PathVariable Long id, @RequestBody Boolean active){
        personService.updateActive(id, active);
    }
}
