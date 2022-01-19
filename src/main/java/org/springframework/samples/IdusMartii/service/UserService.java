/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.IdusMartii.service;


import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.IdusMartii.repository.UserRepository;
import org.springframework.samples.IdusMartii.web.MatchController;
import org.springframework.samples.IdusMartii.model.User;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Slf4j
@Service
public class UserService {

	private UserRepository userRepository;
	@Autowired
	private PlayerService playerService;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	@Transactional
	public void saveUser(User user) throws DataAccessException {
		log.debug("usando metodo saveUser()");
		user.setEnabled(true);
		userRepository.save(user);
	}
	@Transactional
	public Optional<User> findUser(String username) {
		log.debug("usando metodo findUser()");
		log.info("atributo:" + username);
		return userRepository.findById(username);
	}
	@Transactional
	public Iterable<User> findAll(){
		log.info("Buscando lista de usuarios");
		return userRepository.findAll();
	}
	
	@Transactional
	public User findbyUsername(String username){
		log.debug("Usando metodo findbyUsername()");
		log.info("Atributo:" + username);
		return userRepository.findByUsername(username);
	}
	@Transactional
	public List<User> findFriends(String user){
		log.debug("Usando metodo findFriends()");
		log.info("Atributo:" + user);
		return userRepository.findByUsername(user).getFriends();
	}
	
	@Transactional

	public void deleteFriend(User user, String username) throws DataAccessException {
		log.debug("Usando metodo deleteFriend()");
		List<User> friends = user.getFriends();
		User friendToBeDeleted = this.findbyUsername(username);
		friends.remove(friendToBeDeleted);
		user.setFriends(friends);
		saveUser(user);

	}
	
	@Transactional
	public void deleteById(String id){
		 userRepository.deleteById(id);;
	}
	@Transactional
	public Integer matchesPlayedForUser(User user) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() ;
		
	}
}
