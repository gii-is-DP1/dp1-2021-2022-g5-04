package org.springframework.samples.idusmartii.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends AuditableEntity {
	
	private String description;
	
	@Min(1)
	private Integer valor;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "achievement_type")
    AchievementType achievementType;
}