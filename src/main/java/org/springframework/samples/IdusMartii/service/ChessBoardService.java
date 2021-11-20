package org.springframework.samples.IdusMartii.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.samples.IdusMartii.repository.ChessBoardRepository;
import org.springframework.samples.IdusMartii.model.ChessBoard;

@Service
public class ChessBoardService {

	@Autowired 
	ChessBoardRepository boardRepo;
	
	public Optional<ChessBoard> findById(Integer id){
		return boardRepo.findById(id);
	}
}
