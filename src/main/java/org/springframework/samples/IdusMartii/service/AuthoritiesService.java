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

import org.springframework.samples.IdusMartii.repository.AuthoritiesRepository;
import org.springframework.samples.IdusMartii.model.Authorities;

import org.springframework.samples.IdusMartii.model.User;


/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Slf4j
@Service
public class AuthoritiesService {


    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private  UserService userService;



    @Transactional
    public void saveAuthorities(Authorities authorities) throws DataAccessException {
        authoritiesRepository.save(authorities);
    }
    
    @Transactional
    public void saveAuthorities(String username, String role) throws DataAccessException {
        log.info("Creando authorities...");
        Authorities authority = new Authorities();
        log.info("Buscando User...");
        Optional<User> user = Optional.of(userService.findUser(username).get());
        log.info("Encontrado");
        if(user.isPresent()) {
            authority.setUser(user.get());
            authority.setAuthority(role);
            //user.get().getAuthorities().add(authority);
            authoritiesRepository.save(authority);
        }else
            throw new DataAccessException("User '"+userService.findUser(username).get().getUsername()+"' not found!") {};
    }


    
    @Transactional
    public Boolean getAuthorities(String username) throws DataAccessException {
        log.info("Obteniendo los authorities del usuario...");
        log.debug("atributo: "+ username);
        String ret = "";
        Boolean re  = false;
        List<Authorities> user = authoritiesRepository.findByUsername(username);
             ret = user.get(0).getAuthority();
             if(ret.equals("admin")) {
                 re = true;
             }
        return re;
    }

}