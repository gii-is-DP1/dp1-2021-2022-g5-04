package org.springframework.samples.IdusMartii.model;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class AchievementTypeFormatter implements Formatter<AchievementType>{
	private final AchievementService achievementService;
	
	@Autowired
	public AchievementTypeFormatter(AchievementService achievementService) {
		this.achievementService = achievementService;
	}
	
	@Override
	 public String print(AchievementType achievement, Locale locale) {
       return achievement.getName();
	 }
      
	@Override
     public AchievementType parse(String name, Locale locale) throws ParseException {
	     // TODO Auto-generated method stub
	 	if(achievementService.getAchievementTypeByName(name) != null) {
	 		return achievementService.getAchievementTypeByName(name);
	 	}
		throw new ParseException("type not found:", 0);
     }

}
