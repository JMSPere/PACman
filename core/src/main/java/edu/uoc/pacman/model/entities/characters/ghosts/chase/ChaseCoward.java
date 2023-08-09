package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;

public class ChaseCoward implements ChaseBehaviour{
    private static final int TILES_TO_CHASE = 8;

    public ChaseCoward(){

    }

    public Position getChasePosition(Ghost ghost) {
        Position pacPosition = ghost.getLevel().getPacman().getPosition();
        if(ghost.getPosition().distance(pacPosition) > TILES_TO_CHASE){
            return pacPosition;
        }
        return ghost.getScatterPosition();
    }
}
