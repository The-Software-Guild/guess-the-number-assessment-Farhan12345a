
package com.fshahbaz.guess_the_number.service;

import com.fshahbaz.guess_the_number.data.GameDao;
import com.fshahbaz.guess_the_number.models.Game;
import com.fshahbaz.guess_the_number.models.Round;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Service {
    
    @Autowired
    GameDao gameDao;
    
    
    public int startGame(){
        Game game = new Game();
        //find way to make answer
        game.setAnswer(createAnswer());
        gameDao.add(game);
        return game.getGameID();
    }
    
    public String createAnswer(){
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(l);
        Integer result = 1000*l.get(0) + 100*l.get(1) + 10*l.get(2) + l.get(3);
    
        return result.toString();
    }
    
    public List<Round> getRoundsViaGameID(int gameID){
        return gameDao.getGameForRound(gameID);
    }
    
    public Game getGame(int gameID){
        Game game = gameDao.findById(gameID);
        if(game.getStatus() == false){
            game.setAnswer("####");
        }
        return game;
    }
    
    public List<Game> getListGames(){
        return gameDao.getAll();
    }
    
   
    public Round makeGuess(Round round) {
        //Gets the gameId of specific round and the correct answer
        String answer = gameDao.findById(round.getGameID()).getAnswer();
        
        //Determines result based of method following
        String result = getResult(round.getGuess(), answer);
        
        //Change the result 
        round.setResult(result);
        
        //Change status if guess matches answer
        if (round.getGuess().equals(answer)) {
            Game game = getGame(round.getGameID());
            game.setStatus(true);
            gameDao.update(game);
        }
        
        return gameDao.addRound(round);
    }
    
    //Method to determine the exact and partial positons
    public String getResult(String guess, String answer) {
        int exactMatch  = 0;
        int partialMatch = 0;
        int index = 0;
        
        //Turn numbers into character array for iteraiton
        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();
        
        //Iterate until the values reach 4
        while(index < guessArray.length){
            //Check if the number exists in the array
            //If its less then 0, it does't exist
            //&& answer.indexOf(guessArray[index]) >= 0 
            if (guess.indexOf(answerArray[index]) >= 0) { 
                //Comparing each character to see if there matched
                if (guessArray[index] == answerArray[index]) {
                    //if so its an exact match
                    exactMatch++;
                } else {
                    //if not, position is off
                    partialMatch++;
                }
            }
            index++;
        }
        
        return "e:" + exactMatch + ":p:" + partialMatch;
    }

    
}
