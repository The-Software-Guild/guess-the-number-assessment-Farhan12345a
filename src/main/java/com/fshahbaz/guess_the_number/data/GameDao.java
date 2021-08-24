/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fshahbaz.guess_the_number.data;

import com.fshahbaz.guess_the_number.models.Game;
import com.fshahbaz.guess_the_number.models.Round;
import java.util.List;

/**
 *
 * @author farhanshahbaz
 */
public interface GameDao {
    //Defining CRUD operations
    Game add(Game game);

    List<Game> getAll();

    Game findById(int id);

    // true if item exists and is updated
    void update(Game game);

    // true if item exists and is deleted
    void deleteGameById(int id);
    
    //Defining CRUD operations
    Round addRound(Round round);

    List<Round> getAllRounds();

    Round getRoundByID(int id);

    // true if item exists and is updated
    void updateRound(Round round);

    // true if item exists and is deleted
    void deleteRoundById(int id);
    
    List<Round> getGameForRound(int id);
}
