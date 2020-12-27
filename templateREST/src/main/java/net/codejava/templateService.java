package net.codejava;
import java.util.List;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
@Transactional
// where all functions implemented from the repository in here
public class templateService {

    @Autowired
    private templateRepository repo;
    public List<template> listAll() {
        return repo.findAll();
    }
     
    public void save(template template) {
        repo.save(template);
    }
     
    public template get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
