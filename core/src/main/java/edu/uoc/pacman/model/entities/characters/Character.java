package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Entity;

public abstract class Character extends Entity implements Hitable, Movable{
    private boolean dead;
    private Direction direction;
    private int duration;
    private Level level;
    private Position startPosition;

    public Character (Position position, Direction direction, Sprite sprite, Level level){
        super(position,true, sprite);
        if(position == null){
            this.startPosition.setX(0);
            this.startPosition.setY(0);
        }else{
            setStartPosition(position);
        }
        if(direction == null){
            setDirection(Direction.UP);
        }else{
            setDirection(direction);
        }
        setLevel(level);
        alive();
    }

    public Direction getDirection(){
        return this.direction;
    }
    public void setDirection(Direction direction){
        if(direction == null){

        }else{
            this.direction = direction;
        }
    }

    protected int getDuration(){
        return this.duration;
    }
    protected void setDuration(int duration){
        this.duration = duration;
    }
    public Level getLevel(){
        return this.level;
    }
    public void setLevel(Level level){
        this.level = level;
    }
    protected Position getStartPosition(){
        return this.startPosition;
    }
    private void setStartPosition(Position position){
        this.startPosition = position;
    }

    public boolean isDead(){
        return this.dead;
    }
    private void setDead(boolean dead){
        this.dead = dead;
    }
    public void alive(){
        setDead(false);
    }
    public void kill(){
        setDead(true);
    }
    public void reset(){
        this.alive();
    }
    @Override
    public String toString(){
        return this.getPosition().toString()+","+this.direction.toString();
    }

}
