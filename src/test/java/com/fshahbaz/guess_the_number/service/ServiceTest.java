/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fshahbaz.guess_the_number.service;

import com.fshahbaz.guess_the_number.TestApplicationConfiguration;
import com.fshahbaz.guess_the_number.data.GameDao;
import com.fshahbaz.guess_the_number.models.Game;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ServiceTest {
    
    @Autowired
    Service service;
    
    @Autowired
    GameDao gameDao;


    public ServiceTest() {
        
    }

    @Test
    public void testCreateAnswer() {
        
        String result = service.createAnswer();
        
        //Checking that the length is 4
        assertEquals(4, result.length());

        assertEquals(true, uniqueCharacters(result));
        
    }
    
    //helper function for above test
    boolean uniqueCharacters(String str){
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;
 
        // If no duplicate characters encountered,
        // return true
        return true;
    }
    
    
    @Test
    public void testGetGameFalse() {
        
        Game game = new Game();
        game.setAnswer("3876");
        game.setStatus(false);
        game = gameDao.add(game);
        
        Game newGame = service.getGame(game.getGameID());
        assertEquals("####", newGame.getAnswer());
        
    }
    
    @Test
    public void testGetGameTrue() {
        
        Game game1 = new Game();
        game1.setAnswer("8542");
        game1.setStatus(true);
        game1 = gameDao.add(game1);
        
        Game newGame = service.getGame(game1.getGameID());
        assertEquals(game1.getAnswer(), newGame.getAnswer());
        
    }
    
    
    @Test
    public void testGame1() {
        String guess = "1234";
        String answer = "2159";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:2", result);
    }

    @Test
    public void testGame2() {
        String guess = "1234";
        String answer = "1234";
        String result = service.getResult(guess, answer);

        assertEquals("e:4:p:0", result);
    }

    @Test
    public void testGame3() {
        String guess = "1234";
        String answer = "4321";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:4", result);
    }
    
}
