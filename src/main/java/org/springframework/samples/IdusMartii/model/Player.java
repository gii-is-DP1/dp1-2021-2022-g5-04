package org.springframework.samples.IdusMartii.model;

import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

import org.springframework.samples.IdusMartii.enumerates.*;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends NamedEntity{
		
	
		private Faction card1;
		private Faction card2;
		private Vote vote;
		private Role role;
		private boolean asigned;
		
		
		@OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "username", referencedColumnName = "username")
		User user;
		@ManyToOne
		@JoinColumn(name = "match_id")
		private Match match;
		
		


		// @ManyToOne
		// @JoinColumn(name = "match_id")
		// private Match match;
		
}
