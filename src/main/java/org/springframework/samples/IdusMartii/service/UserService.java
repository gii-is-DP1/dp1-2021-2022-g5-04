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
import org.springframework.samples.IdusMartii.service.exceptions.DuplicatedUsername;

import org.springframework.samples.IdusMartii.model.Person;
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
	private AchievementService achievementService;
	@Autowired
	private FriendInvitationService friendInvitationService;
	@Autowired
	private ChatService chatService;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	@Transactional
    public void saveUser(User user) throws DataAccessException, DuplicatedUsername {
        log.debug("usando metodo saveUser()");
		String username = user.getUsername();
        List<String> usernameList = new ArrayList<>();
        for(User u:userRepository.findAll()){
            usernameList.add(u.getUsername());
        }
        if (usernameList.contains(username)){
			throw new DuplicatedUsername();
        }
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
	public Page<User> findUsersWithPagination(Pageable pageable, String text){
		return userRepository.findUsersWithPagination(pageable, text);
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
		userRepository.save(user);
		userRepository.save(friendToBeDeleted);
	}
	
	@Transactional
	public void delete(User user){
	log.info("Borrando usuario...");
	log.debug("Usuario: " + user);
		if (user.getAchievements().size() > 0) {
			achievementService.deleteAllAchievementsFromUser(user);
		}
		invitationService.deleteAllInvitationsFromUser(user);
		if (user.getFriends().size() > 0) {
			friendInvitationService.deleteAllFriendsFromUser(user);
		}
		friendInvitationService.deleteFriendInvitationsFromUser(user);
		playerService.deleteAllPlayersFromUser(user);
		chatService.deleteChatsFromUser(user);
		userRepository.delete(user);
	}
	@Transactional
	public Integer matchesPlayedForUser(User user) throws DataAccessException {
		log.info("Buscando numero de partidas jugadas...");
		return playerService.findbyUsernameMatchFinished(user.getUsername()).size();

	}
		
	// public List<User> findUsers() {
		
	// 	return userRepository.findUsers();}

  
	@Transactional
	public boolean isAdmin(User user) throws DataAccessException {
		if (authoritiesService.getAuthorities(user.getUsername())) {
			log.info("Eres administrador. Estás haciendo un buen trabajo, sigue así.");
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public List<Person> crearAlumnos(){
		List<Person> people = new ArrayList<Person>();
        Person person_1 = new Person();
        person_1.setFirstName("Pablo ");
        person_1.setLastName("Santos");
        people.add(person_1);
        Person person_2 = new Person();
        person_2.setFirstName("Antonio Roberto ");
        person_2.setLastName("Serrano");
        people.add(person_2);
        Person person_3 = new Person();
        person_3.setFirstName("David ");
        person_3.setLastName("Sabugueiro");
        people.add(person_3);
        Person person_4 = new Person();
        person_4.setFirstName("José Ramón ");
        person_4.setLastName("Arias");
        people.add(person_4);
        Person person_5 = new Person();
        person_5.setFirstName("Juan Carlos ");
        person_5.setLastName("Moreno");
        people.add(person_5);
        Person person_6 = new Person();
        person_6.setFirstName("Manuel ");
        person_6.setLastName("Carnero");
        people.add(person_6);
        return people;
	}
}
