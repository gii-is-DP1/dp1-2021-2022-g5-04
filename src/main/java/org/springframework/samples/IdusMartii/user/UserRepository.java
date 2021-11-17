package org.springframework.samples.IdusMartii.user;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
