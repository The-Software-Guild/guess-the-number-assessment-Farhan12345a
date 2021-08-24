/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fshahbaz.guess_the_number.data;

import com.fshahbaz.guess_the_number.models.Game;
import com.fshahbaz.guess_the_number.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


//@Repository: a class-level annotation that tells Spring this is an injectable
//bean
@Repository
public class GameDaoDB implements GameDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    //asks Spring DI for a JdbcTemplate.
    //Need Addition***
    @Autowired
    public GameDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
     
    @Override
    @Transactional
    public Game add(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer, status) VALUES(?,?)";
        //A KeyHolder is an interface that defines something that holds keys
        //GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(INSERT_GAME, 
                //Might have to get changed
                game.getAnswer(), 
                game.getStatus());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameID(newId);
        return game;
    }

    @Override
    public List<Game> getAll() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SELECT_ALL_GAMES, new RoomMapper());
    }

    @Override
    public Game findById(int id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE game_id = ?";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new RoomMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    
    @Override
    public void update(Game game) {
        final String UPDATE_GAME = "UPDATE game SET status = ?, answer = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME,
                //CHANGE AROUND***
                game.getGameID(),
                game.getAnswer(),
                game.getStatus());  
    }

    @Override
    public void deleteGameById(int id) {
        
        final String DELETE_MEETING_BY_ROOM = "DELETE FROM game WHERE game_id = ?";
        jdbc.update(DELETE_MEETING_BY_ROOM, id);
    }
    
    //We use RowMapper to turn a row of the ResultSet into an object
    public static final class RoomMapper implements RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameID(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getBoolean("status"));
            return game;
        }
    }
    
    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        //MIGHT HAVE TO CHANGE THIS UP*******
        jdbc.update(INSERT_ROUND, 
                round.getGameID(), 
                round.getGuess(),
                round.getResult());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundID(newId);
        return getRoundByID(newId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROOMS = "SELECT * FROM round";
        return jdbc.query(SELECT_ALL_ROOMS, new RoundMapper());
    }

    @Override
    public Round getRoundByID(int id) {
        try {
            final String SELECT_ROOM_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROOM_BY_ID, new RoundMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateRound(Round round) {
        final String UPDATE_ROOM = "UPDATE round SET guess = ?, result = ? WHERE id = ?";
        jdbc.update(UPDATE_ROOM,
                round.getGuess(),
                round.getResult(),
                round.getRoundID());
    }

    public List<Round> getGameForRound(int id) {
        final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                + "WHERE game_id = ? ORDER BY guess_time";
        List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), id);
        return rounds;
    }
    
    @Override
    public void deleteRoundById(int id) {
        final String DELETE_ROUND_BY_ROOM = "DELETE FROM round WHERE round_id = ?";
        jdbc.update(DELETE_ROUND_BY_ROOM, id);

    }
    
    //We use RowMapper to turn a row of the ResultSet into an object
    public static final class RoundMapper implements RowMapper<Round> {
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundID(rs.getInt("round_id"));
            round.setGameID(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));
  
            Timestamp timestamp = rs.getTimestamp("guess_time");
            round.setGuessTime(timestamp.toLocalDateTime());
            
            round.setResult(rs.getString("result"));
            return round;
        }
    }
    
   
}
