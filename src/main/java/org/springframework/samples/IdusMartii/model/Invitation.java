package org.springframework.samples.IdusMartii.model;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;
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