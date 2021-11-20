package org.springframework.samples.IdusMartii.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.IdusMartii.model.ChessBoard;
// import org.springframework.samples.IdusMartii.model.User;

public interface ChessBoardRepository extends  CrudRepository<ChessBoard, Integer>{

}
