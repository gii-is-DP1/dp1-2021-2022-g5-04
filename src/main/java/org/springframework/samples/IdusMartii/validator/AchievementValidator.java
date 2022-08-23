package org.springframework.samples.IdusMartii.validator;

import org.springframework.validation.Validator;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.validation.constraints.Null;

import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.AchievementType;
import org.springframework.validation.Errors;
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
        if (type==null) {
			errors.rejectValue("achievementType","achievement_type","Tienes que seleccionar un achievment type");
		}
        if (achv.getValor()==null || achv.getValor()<1) {
			errors.rejectValue("valor","achievement_type","Tienes que introducir un valor correcto mayor que 1");
		}
       
        
    }

    @Override
	public boolean supports(Class<?> clazz) {
		return Achievement.class.isAssignableFrom(clazz);
	}
    
}
