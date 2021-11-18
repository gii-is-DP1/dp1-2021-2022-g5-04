package org.springframework.samples.IdusMartii.player;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.samples.IdusMartii.enumerates.*;
import org.springframework.samples.IdusMartii.model.NamedEntity;
import org.springframework.samples.IdusMartii.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player extends NamedEntity{
	
		private Faction card1;
		private Faction card2;
		private Vote vote;
		private Role role;
		
		@OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "username", referencedColumnName = "username")
		private User user;
	
}
