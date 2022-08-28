package org.springframework.samples.IdusMartii.model;

import javax.persistence.Entity;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username")
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	
	
}
