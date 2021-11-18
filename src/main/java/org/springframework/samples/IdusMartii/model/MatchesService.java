package org.springframework.samples.IdusMartii.model;
import java.util.Optional;

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

	
	
	
}
