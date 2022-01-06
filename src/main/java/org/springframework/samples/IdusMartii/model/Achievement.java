package org.springframework.samples.IdusMartii.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends AuditableEntity {
	
	private String description;
	
	private Integer valor;


}