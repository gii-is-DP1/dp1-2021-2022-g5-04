package org.springframework.samples.IdusMartii.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Match;


public interface MatchRepository extends CrudRepository<Match, Integer>{

}
