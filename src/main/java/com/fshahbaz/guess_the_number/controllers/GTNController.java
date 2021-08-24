
package com.fshahbaz.guess_the_number.controllers;

import com.fshahbaz.guess_the_number.data.GameDao;
import com.fshahbaz.guess_the_number.models.Game;
import com.fshahbaz.guess_the_number.models.Round;
import com.fshahbaz.guess_the_number.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @RestController: turn any Java class (a POJO) into a web-enabled controller. 
 * enables the annotated class to handle RESTful HTTP requests with its methods.
 * 
 * makes class injectable, tells MVC to scan for methods that handle HTTP request,
 * tells Spring MVC to convert methods to JSON
 */
@RestController
@RequestMapping("/api/gtn") //first mapping annotation. we Tell Spring MVC that this class can only handle URLs that begin with "/api
public class GTNController {
    
    @Autowired
    Service service;


    //Starts a game, generates an answer, and sets the correct status. Should 
    //return a 201 CREATED message as well as the created gameId.
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)//sets the HTTP status code to 204 Created for all responses generated from this method
    public int create() {
        return service.startGame();
    }
    
    //Makes a guess by passing the guess and gameId in as JSON. The program must 
    //calculate the results of the guess and mark the game finished if the guess is
    //correct. It returns the Round object with the results filled in.
    @PostMapping("/guess") // relative URL is appended to the @RequestMapping's URL
    public Round guess(@RequestBody Round round) { //@RequestBody tells Spring MVC to expect the data fully serialized in the HTTP request body
        return service.makeGuess(round);
    }
    
    
    // Returns a list of all games. Be sure in-progress games do not display their answer
    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getListGames();
    }
    
    
    // Returns a specific game based on ID. Be sure in-progress games do not display their answer
    //@PathVariable tells Spring MVC to find the parameter in the URL
    @GetMapping("/game/{game_id}")
    public Game getGame(@PathVariable("game_id") int id) {
        return service.getGame(id);
    }
    
    // Returns a list of rounds for the specified game sorted by time
    @GetMapping("rounds/{game_id}")
    public List<Round> getRounds(@PathVariable("game_id") int id) {
        return service.getRoundsViaGameID(id);
    }
   
}
