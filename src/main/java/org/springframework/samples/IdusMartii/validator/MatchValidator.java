package org.springframework.samples.idusmartii.validator;


import org.springframework.validation.Validator;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;




public class MatchValidator implements Validator{

    @Override
    public void validate(Object obj,Errors errors){

        Match match = (Match) obj;
        String name = match.getName();

        if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3 ) {
			errors.rejectValue("name"," entre_3_y_50_caracteres"," El nombre tiene que tener entre 3 y 50 caracteres");
		}

    }

    @Override
	public boolean supports(Class<?> clazz) {
		return Match.class.isAssignableFrom(clazz);
	}
    
}
