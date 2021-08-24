/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fshahbaz.guess_the_number.models;

import java.time.LocalDateTime;
import java.util.Objects;


public class Round {
    private int roundID;
    private int gameID;
    private LocalDateTime guessTime;
    private String guess;
    private String result;

    public Round() {
        
    }

    public Round(int roundID, int gameID, LocalDateTime guessTime, String guess, String result) {
        this.roundID = roundID;
        this.gameID = gameID;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }
    
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }


    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.roundID;
        hash = 67 * hash + this.gameID;
        hash = 67 * hash + Objects.hashCode(this.guessTime);
        hash = 67 * hash + Objects.hashCode(this.guess);
        hash = 67 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundID != other.roundID) {
            return false;
        }
        if (this.gameID != other.gameID) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.guessTime, other.guessTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Round{" + "roundID=" + roundID + ", gameID=" + gameID + ", guessTime=" + guessTime + ", guess=" + guess + ", result=" + result + '}';
    }
    
    

   
    
    
    
}
