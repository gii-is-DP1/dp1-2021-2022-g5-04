package org.springframework.samples.IdusMartii.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchesService {
	@Autowired
	private MatchRepository matchRepository;
	
	@Transactional
	public Iterable<Match> findAll(){
		return matchRepository.findAll();
	}

	
	@Transactional
	public String get(Integer id) throws DataAccessException{
		
			return matchRepository.findById(String.valueOf(id)).get().getContador();
		
}
	
	@Transactional
	public Match getM(String id) throws DataAccessException {
		return matchRepository.findById(String.valueOf(id)).get();
	}
	
	@Transactional
	public void saveMatch(Match visit) throws DataAccessException {
		matchRepository.save(visit);
	}
	
	@Transactional
	public void uM(Match visit) throws DataAccessException {
		matchRepository.delete(visit);
	
}



	


	
	
	
}

