package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseBehaviour;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.characters.pacman.State;
import edu.uoc.pacman.model.entities.items.Wall;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Sprite;
import edu.uoc.pacman.model.Level;

import java.util.Iterator;

public abstract class Ghost extends Character implements Scorable {
    private Behaviour behaviour;
    protected ChaseBehaviour chaseBehaviour;
    private Position scatterPosition;

    protected Ghost (Position startPosition, Position scatterPosition, Direction direction, Behaviour behaviour, Sprite sprite, Level level){
        super(startPosition, direction, sprite, level);
        setScatterPosition(scatterPosition);
        setBehaviour(behaviour);
    }

    public Behaviour getBehaviour(){
        return this.behaviour;
    }
    public void setBehaviour(Behaviour behaviour){
        setDuration(behaviour.getDuration());
        this.behaviour = behaviour;
    }
    private void nextBehaviour(){
        if(getDuration() == 0){
            switch(behaviour){
                case CHASE:
                    setBehaviour(Behaviour.SCATTER);
                    break;
                default:
                    setBehaviour(Behaviour.CHASE);
                    break;
            }
        }else{
            this.setDuration(getDuration()-1);
        }
    }
    public void reset(){
        setPosition(getStartPosition());
        alive();
        setBehaviour(Behaviour.INACTIVE);
        setDirection(Direction.UP);
    }
    @Override
    public boolean equals(Object o){
        Ghost ghost = (Ghost) o;
        if(isDead() == ghost.isDead() && getBehaviour().equals(ghost.getBehaviour()) && getDirection().equals(ghost.getDirection()) && getPosition().equals(ghost.getPosition())){
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return getPosition().toString()+","+getDirection().toString()+","+getBehaviour().toString();
    }
    public Position getScatterPosition(){
        return this.scatterPosition;
    }
    private void setScatterPosition(Position scatterPosition){
        this.scatterPosition = scatterPosition;
    }
    private Position getTargetPosition(){
        if(getBehaviour().equals(Behaviour.CHASE)){
            return this.chaseBehaviour.getChasePosition(this);
        }else if(getBehaviour().equals(Behaviour.SCATTER) || getBehaviour().equals(Behaviour.FRIGHTENED)){
            return getScatterPosition();
        }else{
            return null;
        }
    }
    /*
    public void move(){
        if(!getBehaviour().equals(Behaviour.INACTIVE)){
            boolean found = false;
            Position newPos = null;
            Position opposite = new Position (getPosition().getX() + getDirection().opposite().getX(),
                    getPosition().getY() + getDirection().opposite().getY());
            Position downPos = new Position (getPosition().getX() + Direction.DOWN.getX(), getPosition().getY() + Direction.DOWN.getY());
            Position leftPos = new Position (getPosition().getX() + Direction.LEFT.getX(), getPosition().getY() + Direction.LEFT.getY());
            Position rightPos = new Position (getPosition().getX() + Direction.RIGHT.getX(), getPosition().getY() + Direction.RIGHT.getY());
            Position upPos = new Position (getPosition().getX() + Direction.UP.getX(), getPosition().getY() + Direction.UP.getY());
            Position[] positions = {downPos, leftPos, rightPos, upPos};
            double distance = positions[0].distance(getTargetPosition());
            for(int i = 0; i < 4; i++){
                if(positions[i].distance(getTargetPosition()) <= distance && !positions[i].equals(opposite)){
                    if(getLevel().getMapItem(positions[i]).isPathable()){
                        found = true;
                        distance = positions[i].distance(getTargetPosition());
                        newPos = positions[i];
                        switch (i){
                            case 0:
                                setDirection(Direction.DOWN);
                                break;
                            case 1:
                                setDirection(Direction.LEFT);
                                break;
                            case 2:
                                setDirection(Direction.RIGHT);
                                break;
                            default:
                                setDirection(Direction.UP);
                                break;
                        }
                    }
                }
            }
            if(found){
                setPosition(newPos);
                hit();
            }
        }
        nextBehaviour();
    }*/
    public int stepsCount(Position initialPosition, Position targetPosition, Direction direction){
        int counter = 0;
        double distance = initialPosition.distance(targetPosition);
        boolean found = false;
        Direction currentDirection = direction;
        Position finalNewPos = new Position(0, 0);
        Position newPos = initialPosition;
        Position opposite = Position.add(newPos, new Position(currentDirection.opposite().getX(), currentDirection.opposite().getY()));
        Position downPos = Position.add(newPos, new Position(Direction.DOWN.getX(), Direction.DOWN.getY()));
        Position leftPos = Position.add(newPos, new Position(Direction.LEFT.getX(), Direction.LEFT.getY()));
        Position rightPos = Position.add(newPos, new Position(Direction.RIGHT.getX(), Direction.RIGHT.getY()));
        Position upPos = Position.add(newPos, new Position(Direction.UP.getX(), Direction.UP.getY()));
        Position[] positions = {downPos, leftPos, rightPos, upPos};
        Direction[] directions = {Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP};
        while(!found){
            distance = positions[0].distance(targetPosition);
            for(int i = 0; i < 4; i++){
                if(positions[i].distance(targetPosition) > distance){
                    distance = positions[i].distance(targetPosition);
                }
            }
            for(int i = 0; i < 4; i++){
                if(positions[i].distance(targetPosition) <= distance && !positions[i].equals(opposite) && getLevel().getMapItem(positions[i]).isPathable()){
                    distance = positions[i].distance(targetPosition);
                }
            }
            for(int i = 0; i < 4; i++){
                if(positions[i].distance(targetPosition) == distance && !positions[i].equals(opposite) && getLevel().getMapItem(positions[i]).isPathable()){
                    distance = positions[i].distance(targetPosition);
                    newPos = positions[i];
                    currentDirection = directions[i];
                }
                if(newPos.equals(targetPosition)){
                    found = true;
                }
            }
            if(!finalNewPos.equals(newPos)){
                finalNewPos = newPos;
                opposite = Position.add(finalNewPos, new Position(currentDirection.opposite().getX(), currentDirection.opposite().getY()));
                positions[0] = Position.add(finalNewPos, new Position(Direction.DOWN.getX(), Direction.DOWN.getY()));
                positions[1] = Position.add(finalNewPos, new Position(Direction.LEFT.getX(), Direction.LEFT.getY()));
                positions[2] = Position.add(finalNewPos, new Position(Direction.RIGHT.getX(), Direction.RIGHT.getY()));
                positions[3] = Position.add(finalNewPos, new Position(Direction.UP.getX(), Direction.UP.getY()));
                counter++;
            }else if(!getLevel().getMapItem(Position.add(newPos, new Position(currentDirection.getX(), currentDirection.getY()))).isPathable()){
                distance = opposite.distance(targetPosition);
                currentDirection = currentDirection.opposite();
                finalNewPos = opposite;
                opposite = Position.add(finalNewPos, new Position(currentDirection.opposite().getX(), currentDirection.opposite().getY()));
                positions[0] = Position.add(finalNewPos, new Position(Direction.DOWN.getX(), Direction.DOWN.getY()));
                positions[1] = Position.add(finalNewPos, new Position(Direction.LEFT.getX(), Direction.LEFT.getY()));
                positions[2] = Position.add(finalNewPos, new Position(Direction.RIGHT.getX(), Direction.RIGHT.getY()));
                positions[3] = Position.add(finalNewPos, new Position(Direction.UP.getX(), Direction.UP.getY()));
                counter++;
            }
        }
        return counter;
    }

    public void move(){
        if(!getBehaviour().equals(Behaviour.INACTIVE)){
            int steps = 0;
            int bestPos = 0;
            double distance = getPosition().distance(getTargetPosition());
            Position opposite = Position.add(getPosition(), new Position(getDirection().opposite().getX(), getDirection().opposite().getY()));
            Position downPos = Position.add(getPosition(), new Position(Direction.DOWN.getX(), Direction.DOWN.getY()));
            Position leftPos = Position.add(getPosition(), new Position(Direction.LEFT.getX(), Direction.LEFT.getY()));
            Position rightPos = Position.add(getPosition(), new Position(Direction.RIGHT.getX(), Direction.RIGHT.getY()));
            Position upPos = Position.add(getPosition(), new Position(Direction.UP.getX(), Direction.UP.getY()));
            Position[] positions = {downPos, leftPos, rightPos, upPos};
            Direction[] directions = {Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP};
            for(int i = 0; i < 4; i++){
                if(positions[i].distance(getTargetPosition()) < distance && !positions[i].equals(opposite) && getLevel().getMapItem(positions[i]).isPathable()){
                    if(steps == 0){
                        steps = stepsCount(positions[bestPos], getTargetPosition(), directions[bestPos]);
                        bestPos = i;
                        distance = positions[i].distance(getTargetPosition());
                    }else if(stepsCount(positions[bestPos], getTargetPosition(), directions[bestPos]) < steps){
                        steps = stepsCount(positions[bestPos], getTargetPosition(), directions[bestPos]);
                        bestPos = i;
                        distance = positions[i].distance(getTargetPosition());
                    }
                }
            }
            setPosition(positions[bestPos]);
            setDirection(directions[bestPos]);
            hit();
            nextBehaviour();
        }
    }

    public boolean hit(){
        if(getLevel().getPacman() == null){
            return false;
        }
        if(getLevel().getPacman().getPosition().equals(this.getPosition())){
            if(getBehaviour().equals(Behaviour.FRIGHTENED)){
                this.kill();
            }else if(!getBehaviour().equals(Behaviour.INACTIVE) && getLevel().getPacman().getState().equals(State.NORMAL)){
                getLevel().getPacman().kill();
            }
            return true;
        }
        return false;
    }
    @Override
    public void kill(){
        super.kill();
        getLevel().addPoints(getPoints());
        setBehaviour(Behaviour.INACTIVE);
    }
}
