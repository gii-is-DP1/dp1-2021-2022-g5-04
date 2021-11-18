package org.springframework.samples.IdusMartii.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}
