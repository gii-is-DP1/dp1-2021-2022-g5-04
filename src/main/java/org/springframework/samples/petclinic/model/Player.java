package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import org.springframework.z.game.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player extends NamedEntity{
	
		private String card1;
		private String card2;
		private Role role;
		
	
}
