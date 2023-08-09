package edu.uoc.pacman.model.entities.characters.ghosts;

public enum Behaviour {
    CHASE(20),
    FRIGHTENED(30),
    SCATTER(10),
    INACTIVE(5);

    private final int duration;

    Behaviour (int duration){
        this.duration = duration;
    }

    public int getDuration(){
        return this.duration;
    }
    @Override
    public String toString(){
        return super.toString()+":"+getDuration();
    }
}
