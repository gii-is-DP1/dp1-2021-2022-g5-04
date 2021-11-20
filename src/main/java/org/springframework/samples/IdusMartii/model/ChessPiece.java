package org.springframework.samples.IdusMartii.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
// import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChessPiece extends BaseEntity {
    String type;
    String color;
    @Range(min=0,max=7)
    int xPosition;
    @Range(min=0,max=7)
    int yPosition;
    
    @ManyToOne
    ChessBoard board;
    
    
    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
    
}