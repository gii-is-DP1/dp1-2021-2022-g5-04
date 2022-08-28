package org.springframework.samples.idusmartii.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idusmartii.model.Chat;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.samples.idusmartii.model.User;



public interface ChatRepository extends CrudRepository<Chat, Integer>{
	
    @Query("SELECT c FROM Chat c WHERE c.match = :match")
    public List<Chat> findByMatch(@Param("match") Match match);  
    
    @Query("SELECT c FROM Chat c WHERE c.user = :user")
    public List<Chat> findByUser(@Param("user") User user);
    
    @Query("SELECT c FROM Chat c WHERE c.match = :match")
	public Page<Chat> findChatWithPagination(Match match, Pageable pageable);
    
}
