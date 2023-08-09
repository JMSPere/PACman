package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;

public class ChasePatrol implements ChaseBehaviour{
    private static final int TILES_OFFSET = 2;
    private static final int VECTOR_INCREASE = 2;

    public ChasePatrol(){

    }

    public Position getChasePosition(Ghost ghost) {
        Position pacPosition = ghost.getLevel().getPacman().getPosition();
        Position tilesDirection = new Position (ghost.getLevel().getPacman().getDirection().getX()*TILES_OFFSET,
                ghost.getLevel().getPacman().getDirection().getY()*TILES_OFFSET);
        Position targetBlinkyPosition = Position.add(pacPosition, tilesDirection);
        Position firstBlinkyPosition = ghost.getLevel().getBlinky().getPosition();
        Position vector = new Position (VECTOR_INCREASE*(targetBlinkyPosition.getX()-firstBlinkyPosition.getX()),
                VECTOR_INCREASE*(targetBlinkyPosition.getY()-firstBlinkyPosition.getY()));
        return vector;
    }
}
