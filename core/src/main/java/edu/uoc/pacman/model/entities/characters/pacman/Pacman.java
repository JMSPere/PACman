package edu.uoc.pacman.model.entities.characters.pacman;

import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.items.*;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Sprite;
import edu.uoc.pacman.model.Level;

import java.util.Iterator;

public class Pacman extends Character {
    private State state;

    public Pacman (Position startPosition, Direction direction, State state, Level level){
        super(startPosition, direction, Sprite.PACMAN_UP, level);
        setState(state);
    }

    public State getState(){
        return this.state;
    }
    public void setState(State state){
        if(state != null){
            this.state = state;
            this.setDuration(getState().getDuration());
        }
    }
    private void nextState(){

        if(getDuration() != 0){
            setDuration(getDuration() - 1);
        }
        if(this.getDuration() == 0){
            /*
            Iterator itr = getLevel().getGhostList().iterator();
            while(itr.hasNext()){
                Ghost ghost = (Ghost) itr.next();
                switch(ghost.getBehaviour()){
                    case CHASE:
                        ghost.setBehaviour(Behaviour.SCATTER);
                        break;
                    default:
                        ghost.setBehaviour(Behaviour.CHASE);
                        break;
                }
            }*/
            if(getState().equals(State.EATER) || getState().equals(State.INVINCIBLE)){
                setState(State.NORMAL);
            }
        }
    }
    public void reset(){
        setPosition(getStartPosition());
        alive();
        setState(State.INVINCIBLE);
        setDirection(Direction.UP);
    }
    public void move(){
        Position newPos = Position.add(getPosition(), new Position (getDirection().getX(), getDirection().getY()));
        if(getLevel().getMapItem(newPos).isPathable()){
            setPosition(newPos);
            eat();
        }
        hit();
        nextState();
    }
    @Override
    public void setDirection(Direction direction){
        if(direction != null){
            switch(direction){
                case UP -> setSprite(Sprite.PACMAN_UP);
                case DOWN -> setSprite(Sprite.PACMAN_DOWN);
                case LEFT -> setSprite(Sprite.PACMAN_LEFT);
                case RIGHT -> setSprite(Sprite.PACMAN_RIGHT);
            }
            super.setDirection(direction);
        }
    }
    private void eat(){
        MapItem item = getLevel().getMapItem(getPosition());
        if(item.getClass().equals(Dot.class)){
            ((Dot) item).setPicked(true);
            getLevel().addPoints(((Dot) item).getPoints());
        }else if(item.getClass().equals(Life.class)){
            ((Life) item).setPicked(true);
            getLevel().increaseLives();
        }else if(item.getClass().equals(Energizer.class)){
            ((Energizer) item).setPicked(true);
            getLevel().addPoints(((Energizer) item).getPoints());
            getLevel().setGhostsFrightened();
            setState(State.EATER);
        }
        getLevel().removeMapItem(item);
        getLevel().addMapItem(new Path (getPosition()));
    }
    public boolean hit(){
        //it has been redeemed unnecessary to kill both pacman and a ghost again in this hit method, since ghosts already have a hit method
        Iterator itr = getLevel().getGhostList().iterator();
        Ghost hitGhost = null;
        boolean found = false;
        while(!found && itr.hasNext()){
            Ghost ghost = (Ghost) itr.next();
            if(getPosition().equals(ghost.getPosition())){
                hitGhost = ghost;
                found = true;
            }
        }
        if(found){
            if(getState().equals(State.INVINCIBLE) || hitGhost.getBehaviour().equals(Behaviour.INACTIVE)){
                hitGhost.hit();
                return false;
            }else if(hitGhost.getBehaviour().equals(Behaviour.FRIGHTENED)){
                hitGhost.hit();
                return true;
            }else{
                kill();
                return true;
            }
        }
        return false;
    }
    @Override
    public void kill(){
        super.kill();
        setState(State.INVINCIBLE);
        getLevel().decreaseLives();
    }
}
