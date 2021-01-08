package net.codejava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;
 
@RestController
public class Controller {
@Autowired 
private templateService service;
@Autowired
private notificationService n_service;


//read
@GetMapping("/templates") // localhost/templates
public List<template> template_list() {
	return service.listAll();
}
//read
@GetMapping("/notifications") // localhost/templates
public List<notification> notification_list() {
	return n_service.listAll();
}


//sms  table     gmail table 

// template
// dear username you have booked ??? 
// dear username your account ??? has been registered

@GetMapping("/templates/{id}")
public ResponseEntity<template> template_get(@PathVariable Integer id) {
    try {
    	System.out.println("checking the function");
    	template template=service.get(id);
    //String s=template.blueprint;
    	//template exist= template;
    //	int y=template.template.indexOf("username",5,13);
    	//template.template.replace(str, "rawan");
        //System.out.println(template.template);
   	//System.out.println(template.template);
    	//name1="hello";
    	///template.template;
    	//Mailer.send("amrassal707@gmail.com", "23635587", template.getChannel(), "hello", template.gettemplate());
    	 return new ResponseEntity<template>(template, HttpStatus.OK);
    }
    catch (Exception e) {
    	return new ResponseEntity<template>(HttpStatus.NOT_FOUND);
	}
       
       
}

@GetMapping("/notifications/{id}")
public ResponseEntity<notification> notification_get(@PathVariable Integer id) {
    try {
    	System.out.println("checking the function");
    	notification notification=n_service.get(id);
    //String s=template.blueprint;
    	//template exist= template;
    //	int y=template.template.indexOf("username",5,13);
    	//template.template.replace(str, "rawan");
        //System.out.println(template.template);
   	//System.out.println(template.template);
    	//name1="hello";
    	///template.template;
    	//Mailer.send("amrassal707@gmail.com", "23635587", template.getChannel(), "hello", template.gettemplate());
    	 return new ResponseEntity<notification>(notification, HttpStatus.OK);
    }
    catch (Exception e) {
    	return new ResponseEntity<notification>(HttpStatus.NOT_FOUND);
	}
       
       
}



//create
@PostMapping("/addtemplate")
public void template_addSign(@RequestBody template template) {
	System.out.println("checking the function");
//	 template existtemplate = new template();
//	 existtemplate.channel=template.channel;
//	 existtemplate.template=template.template;
    service.save(template);
}

//create
@PostMapping("/addnotification")
public void notification_addSign(@RequestBody notification notification) {
	System.out.println("checking the function");
//	 template existtemplate = new template();
//	 existtemplate.channel=template.channel;
//	 existtemplate.template=template.template;
  n_service.save(notification);
}



//update
@PutMapping("/templates/{id}")
public ResponseEntity<?> template_update(@RequestBody template template, @PathVariable Integer id) {
    try {
    	System.out.println("checking the function");
        template existtemplate = service.get(id);
        service.save(template);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }      
}

//update
@PutMapping("/notifications/{id}")
public ResponseEntity<?> notification_update(@RequestBody notification notification, @PathVariable Integer id) {
  try {
  	System.out.println("checking the function");
  	notification existnotification = n_service.get(id);
      n_service.save(notification);
      return new ResponseEntity<>(HttpStatus.OK);
  } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }      
}
@GetMapping("/templates/{id}/{username}/{item}/{channel}/{sendTO}")
public void template_sendQueue(@PathVariable Integer id,@PathVariable String username,@PathVariable String item,@PathVariable String channel,@PathVariable String sendTO) throws SQLException, ClassNotFoundException {
	template template=service.get(id);
    String s=template.blueprint;
    String w=s.replace("username", username);
    String y=w.replace("item", item);
    template.blueprint=y;
    
    String dbDriver = "com.mysql.jdbc.Driver"; 
	 String dbURL = "jdbc:mysql://127.0.0.1:3306/"; 
		// Database name to access 
		String dbName = "mydb"; 
		String dbUsername = "root"; 
		String dbPassword = "14719"; 
		Class.forName(dbDriver);
		Connection c = DriverManager.getConnection(dbURL + dbName, dbUsername,dbPassword);
    
		Statement s1=c.createStatement();
    	String sql="INSERT INTO notification(content,subject,channel,sendTO) values('"+template.blueprint+"','"+template.subject+"','"+channel+"','"+sendTO+"')";
    	int rs1 = s1.executeUpdate(sql);
    
    }

@GetMapping("/QueueNotifications")
public void list_and_queuing() throws SQLException, ClassNotFoundException {
	List<notification>list_of_notification=n_service.listAll();
	System.out.println("checking the function");
    String dbDriver = "com.mysql.jdbc.Driver"; 
	 String dbURL = "jdbc:mysql://127.0.0.1:3306/"; 
		// Database name to access 
		String dbName = "mydb"; 
		String dbUsername = "root"; 
		String dbPassword = "14719"; 
		Class.forName(dbDriver);
		Connection c = DriverManager.getConnection(dbURL + dbName, dbUsername,dbPassword);
    for(notification n : list_of_notification)
    {
    	if(n.channel.equals("sms"))
        {
    		Statement s1=c.createStatement();
        	String sql="INSERT INTO sms(content,sendTO) values('"+n.content+"','"+n.sendTO+"')";
        	int rs1 = s1.executeUpdate(sql);
        }
        else if(n.channel.equals("gmail"))
        {
        	Statement s1=c.createStatement();
        	String sql="INSERT INTO gmail(content,sendTO) values('"+n.content+"','"+n.sendTO+"')";
        	int rs1 = s1.executeUpdate(sql);
        }
    }
    //return service.listAll();	
    }

//delete
@DeleteMapping("/templates/{id}")
public void template_delete(@PathVariable Integer id) {
    service.delete(id);
    //deleted
}
//delete
@DeleteMapping("/notifications/{id}")
public void notification_delete(@PathVariable Integer id) {
  n_service.delete(id);
  //deleted
}



}