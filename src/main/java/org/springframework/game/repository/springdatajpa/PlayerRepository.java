package org.springframework.game.repository.springdatajpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Player;


public interface PlayerRepository extends CrudRepository<Player, Integer>{}
