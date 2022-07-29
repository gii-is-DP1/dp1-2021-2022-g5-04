package org.springframework.samples.IdusMartii.service;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.AchievementType;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {

    @Autowired
    private AchievementService achievementService;


    @Test
    public void testAchievementCount(){
        int contar= achievementService.achievementCount();
        
        assertTrue(contar>0);;
    }

    @Test
    public void testFindAll(){
        Iterable<Achievement> logrosT= achievementService.findAll();
        List <String> logros = new ArrayList<>();
		logrosT.forEach(l -> logros.add(l.getName()));

        assertEquals(logros.get(0), "Primera Partida");
        assertEquals(logros.get(1), "5 Partidas");

    }

    

   
    @Test
    public void testFindById(){
        Achievement logroId=achievementService.findById(1);

        assertThat(logroId.getName()).isEqualTo("Primera Partida");
    }

    @Test
    public void getAllAchievementsTypes(){
       
    	List<AchievementType> a = achievementService.getAllAchievementsTypes();

        

        assertTrue(a.size()>0);
    }
    
    
    
    
    
}
