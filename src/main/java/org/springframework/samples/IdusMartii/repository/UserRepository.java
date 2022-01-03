package org.springframework.samples.IdusMartii.repository;
import org.springframework.samples.IdusMartii.model.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, String>{
    
	
}
