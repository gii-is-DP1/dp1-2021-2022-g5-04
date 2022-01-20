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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private AchievementService achievementService;
	@Autowired
	private FriendInvitationService friendInvitationService;

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
	public Page<User> findAllUsersWithPagination(Pageable pageable){
		return userRepository.findAllUsersWithPagination(pageable);
	}
	@Transactional
	public List<Integer> createNumberOfPagesList(Page<User> userPage, int pageNumber){
		int numberOfPages = (userPage.getNumberOfElements()/5) + 1;
		List<Integer> numberOfPagesList = new ArrayList<Integer>();
		int i = 1;
		while(i != numberOfPages + 1) {
			numberOfPagesList.add(i);
			i++;
		}
		numberOfPagesList.remove(numberOfPagesList.indexOf(pageNumber));
		return numberOfPagesList;
	}
	@Transactional
	public List<User> findUsersByText(String text){
		return userRepository.findUsersByText(text);
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
		User friendToBeDeleted = userRepository.findByUsername(username);
		List<User> friendsFromSecondUser = friendToBeDeleted.getFriends();
		friends.remove(friendToBeDeleted);
		friendsFromSecondUser.remove(user);
		user.setFriends(friends);
		friendToBeDeleted.setFriends(friendsFromSecondUser);
		saveUser(user);
		saveUser(friendToBeDeleted);
	}
	
	@Transactional
	public void delete(User user){
		if (user.getAchievements().size() > 0) {
			achievementService.deleteAllAchievementsFromUser(user);
		}
		invitationService.deleteAllInvitationsFromUser(user);
		if (user.getFriends().size() > 0) {
			friendsService.deleteAllFriendsFromUser(user);
		}
		friendInvitationService.deleteFriendInvitationsFromUser(user);
		playerService.deleteAllPlayersFromUser(user);
		userRepository.delete(user);
	}
	@Transactional
	public Integer matchesPlayedForUser(User user) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size();
		
	}
	@Transactional
	public boolean isAdmin(User user) throws DataAccessException {
		if (authoritiesService.getAuthorities(user.getUsername())) {
			return true;
		} else {
			return false;
		}
	}
}
