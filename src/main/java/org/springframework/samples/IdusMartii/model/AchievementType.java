package org.springframework.samples.idusmartii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AchievementType extends BaseEntity{
	
	@NotNull
	@Size(min=3, max=50)
	@Column(name = "name", unique = true)
    String name;

}
