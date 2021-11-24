package org.springframework.samples.IdusMartii.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Player;


public interface PlayerRepository extends CrudRepository<Player, Integer>{

}
