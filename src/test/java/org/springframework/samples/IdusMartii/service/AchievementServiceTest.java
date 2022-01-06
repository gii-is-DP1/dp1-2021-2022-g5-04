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
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {

    @Autowired
    private AchievementService achievementService;

    @Autowired
	private UserService userService;

    @Test
    public void testAchievementCount(){
        int contar= achievementService.achievementCount();
        
        assertEquals(contar, 4);;
    }

    @Test
    public void testFindAll(){
        Iterable<Achievement> logrosT= achievementService.findAll();
        List <String> logros = new ArrayList<>();
		logrosT.forEach(l -> logros.add(l.getName()));

        assertEquals(logros.get(0), "Primera Partida");
        assertEquals(logros.get(1), "El Edil");
        assertEquals(logros.get(2), "El Cónsul");
        assertEquals(logros.get(3), "El Pretor");

    }

    @Test
    public void testFindByUser(){
        Optional<User> usuario= userService.findUser("admin1");
        User nomUser = usuario.get();
        List<Achievement> listaLogros=achievementService.findByUser(usuario.get());

        assertThat(listaLogros.size()).isEqualTo(2);
    }
    
}
