package org.springframework.samples.IdusMartii.model;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat extends BaseEntity {


        @CreatedDate
        private LocalDateTime date;
        @NonNull
        String text;

        @OneToOne
        @JoinColumn(name="username")
        private User user;
        @OneToOne
        @JoinColumn(name="match_id")
        private Match match;

    
        
    
}
