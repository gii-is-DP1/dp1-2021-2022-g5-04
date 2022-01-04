package org.springframework.samples.IdusMartii.model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements Serializable {
	@CreatedBy private String creator;
	@CreatedDate private LocalDateTime createdDate;
	@LastModifiedBy private String modifier;
	@LastModifiedDate private LocalDateTime lastModifiedDate; 
	
	@Id
	String username;
	
	String password;
	@Email
	String email;

	// List<String> friends;
	
	boolean enabled;

	public boolean isNew() {
		return this.username == null;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
	
	@ManyToMany
	@JoinTable(name = "achievement_user")
	private List<Achievement> achievements;
	
	
}