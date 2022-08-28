package org.springframework.samples.idusmartii.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
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
