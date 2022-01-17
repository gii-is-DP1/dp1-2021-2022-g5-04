package org.springframework.samples.IdusMartii.repository;
import org.springframework.samples.IdusMartii.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends  CrudRepository<User, String>{
    
	
	  @Query("SELECT u FROM User u where u.username = :username")
	    public User findByUsername(@Param("username") String username);
	  
	  
}
