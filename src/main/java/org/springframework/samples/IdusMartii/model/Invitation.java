package org.springframework.samples.IdusMartii.model;

import javax.persistence.CascadeType;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

import org.springframework.samples.IdusMartii.enumerates.Plays;

import java.util.Date;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invitations")
public class Invitation extends BaseEntity{
 
		private Date fecha;	
        @ManyToOne()
	    @JoinColumn(name = "match_id")
		private Match match;
        @ManyToOne()
		@JoinColumn(name = "username")
		private User user;
}