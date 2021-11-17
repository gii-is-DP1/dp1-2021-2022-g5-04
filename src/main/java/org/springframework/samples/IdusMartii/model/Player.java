package org.springframework.samples.IdusMartii.model;

import javax.persistence.Entity;

import org.springframework.samples.IdusMartii.game.Role;

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
