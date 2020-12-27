package net.codejava;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;
 
@RestController
public class templateController {
@Autowired 
private templateService service;


@GetMapping("/templates")
public List<template> list() {
	return service.listAll();
}

@GetMapping("/templates/{id}")
public ResponseEntity<template> get(@PathVariable Integer id) {
    try {
    	template template=service.get(id);
    	 return new ResponseEntity<template>(template, HttpStatus.OK);
    }
    catch (Exception e) {
    	return new ResponseEntity<template>(HttpStatus.NOT_FOUND);
	}
       
       
}

@PostMapping("/addtemplate")
public void add(@RequestBody template template) {
    service.save(template);
}
@PutMapping("/templates/{id}")
public ResponseEntity<?> update(@RequestBody template template, @PathVariable Integer id) {
    try {
        template existtemplate = service.get(id);
        service.save(template);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }      
}
@DeleteMapping("/templates/{id}")
public void delete(@PathVariable Integer id) {
    service.delete(id);
}

}
