package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.MatchRepository;
import org.springframework.samples.IdusMartii.model.Match;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

@Service
public class MatchService {
    @Autowired 
	MatchRepository matchRepo;

 
    
	@Transactional(readOnly = true)
	public Match findById(Integer id) throws DataAccessException{
		return matchRepo.findById(id).get();
	}
    
}
