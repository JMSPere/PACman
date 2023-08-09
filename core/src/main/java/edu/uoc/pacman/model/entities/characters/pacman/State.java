package edu.uoc.pacman.model.entities.characters.pacman;

public enum State {
    NORMAL(Integer.MAX_VALUE),
    EATER(30),
    INVINCIBLE(5);

    private final int duration;

    State (int duration){
        this.duration = duration;
    }

    public int getDuration(){
        return this.duration;
    }
    @Override
    public String toString(){
        String str = super.toString();
        return str+":"+getDuration();
    }
}
