package org.springframework.samples.IdusMartii.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Match;


public interface MatchRepository extends CrudRepository<Match, Integer>{

}
