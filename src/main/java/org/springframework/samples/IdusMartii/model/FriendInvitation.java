package org.springframework.samples.idusmartii.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "friends_invitations")
public class FriendInvitation extends BaseEntity{
	
	private Date fecha;
	@OneToOne()
	@JoinColumn(name = "username")
	private User user_requester;
	@OneToOne()
	@JoinColumn(name = "username2")
	private User user_requested;

}
