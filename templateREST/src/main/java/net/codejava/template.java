package net.codejava;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class template {
private Integer id;
private String content;
private String channel;  
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Integer getId() {
	return id;
}
public template() {
}

public void setId(Integer id) {
	this.id = id;
}
public String getContent() {
	return content;
//	System.out.println("content returned successfully");
}
public void setContent(String content) {
	this.content = content;
}
public String getChannel() {
	return channel;
}
public void setChannel(String channel) {
	this.channel = channel;
}

}
 