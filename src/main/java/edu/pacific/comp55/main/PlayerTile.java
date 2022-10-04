package edu.pacific.comp55.main;

public class PlayerTile implements Tiles {

    @Override
    public String getType() {
        return "PlayerTile";
    }

    @Override
    public String getChar() {
        return "p";
    }
    
}
