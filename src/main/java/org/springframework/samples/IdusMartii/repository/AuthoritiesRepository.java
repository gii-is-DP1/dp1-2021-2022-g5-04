package org.springframework.samples.idusmartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idusmartii.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	  @Query("SELECT p FROM Authorities p where p.user.username = :username")
	    public List<Authorities> findByUsername(@Param("username") String username);
	
	
}
