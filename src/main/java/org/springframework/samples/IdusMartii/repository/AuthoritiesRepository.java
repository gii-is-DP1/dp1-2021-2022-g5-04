package org.springframework.samples.IdusMartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Authorities;
import org.springframework.samples.IdusMartii.model.User;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	  @Query("SELECT p FROM Authorities p where p.user.username = :username")
	    public List<Authorities> findByUsername(@Param("username") String username);
	
	
}
