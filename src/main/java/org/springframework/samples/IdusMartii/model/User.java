package org.springframework.samples.IdusMartii.model;


import java.io.Serializable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull
	@Size(min=5, max=25)
	@Column(name = "username", unique=true)
	String username;
	@NotNull
	String password;
	@Email
	@NotNull
	String email;
	Integer victorias;
	boolean enabled;

	public boolean isNew() {
		return this.username == null;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "achievement_user")
	private List<Achievement> achievements;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "friends")
	private List<User> friends;	
}