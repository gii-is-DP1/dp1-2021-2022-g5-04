package org.springframework.samples.IdusMartii.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups", "/welcome").permitAll()
				.antMatchers("/auditories/**").hasAnyAuthority("admin")
				.antMatchers("/users").hasAnyAuthority("admin")
				.antMatchers("/users/own").authenticated()
				.antMatchers("/users/friends").authenticated()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/users/find").authenticated()
				.antMatchers("/users/**/edit").hasAnyAuthority("admin")
				.antMatchers("/users/**/edit/own").authenticated()
				.antMatchers("/users/save").permitAll()
				.antMatchers("/users/**/save").hasAnyAuthority("admin")
				.antMatchers("/users/**/save/own").authenticated()
				.antMatchers("/users/delete/**").authenticated()
				.antMatchers("/users/**/delete").hasAnyAuthority("admin")
				.antMatchers("/players/**/**/revisar").authenticated()
				.antMatchers("/players/**/**/expulsar").authenticated()
				.antMatchers("/players/**/**/**").authenticated()
				.antMatchers("/players/**/**/**/ElegirCartaFaccion1").authenticated()
				.antMatchers("/players/**/**/**/ElegirCartaFaccion2").authenticated()
				.antMatchers("/players/**/NuevoTurno").authenticated()
				.antMatchers("/players/**/**/cambiarVoto").authenticated()
				.antMatchers("/players/**/**/revisarVotoYellow").authenticated()
				.antMatchers("/players/**/**/noCambiarVoto").authenticated()
				.antMatchers("/players/**/**/asignarEdil").authenticated()
				.antMatchers("/players/**/**/asignarPretor").authenticated()
				.antMatchers("/invitations").authenticated()
				.antMatchers("/invitations/**").authenticated()
				.antMatchers("/friendInvitations").authenticated()
				.antMatchers("/friendInvitations/**").authenticated()
				.antMatchers("/matches").authenticated()
				.antMatchers("/matches/**").authenticated()
				.antMatchers("/chats").authenticated()
				.antMatchers("/chats/**").authenticated()
				.antMatchers("/achievements").authenticated()
				.antMatchers("/achievements/**").authenticated()
				.antMatchers("/auditories").hasAnyAuthority("admin")
				.antMatchers("/auditories/**").hasAnyAuthority("admin")
				.antMatchers("/exception").permitAll()
				.antMatchers("/achievements").authenticated()
				.antMatchers("/achievements/**/edit").hasAnyAuthority("admin")
				.antMatchers("/achievements/new").hasAnyAuthority("admin")
				.antMatchers("/achievements/**/save").hasAnyAuthority("admin")
				.antMatchers("/achievements/**").authenticated()
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


