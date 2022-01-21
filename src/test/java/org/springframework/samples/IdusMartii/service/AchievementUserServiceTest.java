package org.springframework.samples.IdusMartii.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementUserServiceTest {

    @Autowired
    private AchievementService achievementService;
    
    @Autowired
    private AchievementUserService achievementUserService;

    @Autowired
	private UserService userService;

    @Test
    public void testAchievementCount(){
        int contar= achievementService.achievementCount();
        
        assertEquals(contar, 2);;
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
    public void testFindByUser(){
        Optional<User> usuario= userService.findUser("admin1");
        User nombreUser = usuario.get();
        List<Achievement> listaLogros=achievementService.findByUser(nombreUser);

        assertThat(listaLogros.size()).isEqualTo(2);

    }

    @Test 
    public void testSaveAchievement(){
        Achievement logro = new Achievement();
        logro.setName("Nuevo Logro");
        this.achievementService.saveAchievement(logro);
        Iterable <Achievement> logros = achievementService.findAll();
		List <String> nomLogros = new ArrayList<>();
		logros.forEach(p -> nomLogros.add(p.getName()));

        assertThat(nomLogros.size()).isEqualTo(3);
    }

    @Test
    public void testFindById(){
        Achievement logroId=achievementService.findById(1);

        assertThat(logroId.getName()).isEqualTo("Primera Partida");
    }

    @Test
    public void testNextId(){
        Achievement logro = new Achievement();
        logro.setName("Nuevo Logro");
        this.achievementService.saveAchievement(logro);

        Integer listLogro= achievementService.nextId();

        assertThat(listLogro).isEqualTo(3);
    }
    
}
