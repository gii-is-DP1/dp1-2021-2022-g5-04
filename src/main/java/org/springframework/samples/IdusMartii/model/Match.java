package org.springframework.samples.IdusMartii.model;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.IdusMartii.pet.PetType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "match")

public class Match extends BaseEntity{

	private String contador;
	


	public void setContador(String contador) {
		this.contador = contador;
	}
	

}
