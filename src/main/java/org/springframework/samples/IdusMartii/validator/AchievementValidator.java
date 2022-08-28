package org.springframework.samples.idusmartii.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.samples.idusmartii.model.Achievement;
import org.springframework.samples.idusmartii.model.AchievementType;
import org.springframework.util.StringUtils;

public class AchievementValidator implements Validator{
    
    @Override
    public void validate(Object obj, Errors errors){
        Achievement achv = (Achievement) obj;
        String name = achv.getName();
        AchievementType type = achv.getAchievementType();

        if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3 ) {
			errors.rejectValue("name"," entre_3_y_50_caracteres"," El nombre tiene que tener entre 3 y 50 caracteres");
		}
        
        if (achv.getValor()==null || achv.getValor()<1 ) {
			errors.rejectValue("valor","valor_incorrecto","Tienes que introducir un valor correcto mayor que 1");
		}
        if(type==null){
            errors.rejectValue("achievementType","null","Tienes que elegir un achievementType");
	
        }
        
        
    }

    @Override
	public boolean supports(Class<?> clazz) {
		return Achievement.class.isAssignableFrom(clazz);
	}
    
}
