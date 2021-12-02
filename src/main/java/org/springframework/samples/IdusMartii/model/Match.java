package org.springframework.samples.IdusMartii.model;

import javax.persistence.CascadeType;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class Match extends NamedEntity{
 
		private int round;
		private int turn;
		private int votoaFavor;
		private int votoenContra;
		private int c;

		
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "match")
	    private List<Player> players;
    
}


