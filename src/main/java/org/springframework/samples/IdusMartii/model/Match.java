package org.springframework.samples.IdusMartii.model;

import javax.persistence.CascadeType;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.samples.IdusMartii.enumerates.Plays;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class Match extends AuditableEntity {
 
		private int round;
		private int turn;
		private int votoaFavor;
		private int votoenContra;
		private Plays plays;

		
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "match")
	    private List<Player> players;
    
}


