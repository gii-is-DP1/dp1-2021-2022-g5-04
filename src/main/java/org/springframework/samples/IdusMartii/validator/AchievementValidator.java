package org.springframework.samples.IdusMartii.validator;

import org.springframework.validation.Validator;

import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.validation.Errors;
import org.springframework.util.StringUtils;

public class AchievementValidator implements Validator{

    @Override
    public void validate(Object obj, Errors errors){
        Achievement achv = (Achievement) obj;
        String name = achv.getName();

        if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3) {
			errors.rejectValue("name"," entre_3_y_50_caracteres"," El nombre tiene que tener entre 3 y 50 caracteres");
		}
    }

    @Override
	public boolean supports(Class<?> clazz) {
		return Achievement.class.isAssignableFrom(clazz);
	}
    
}
