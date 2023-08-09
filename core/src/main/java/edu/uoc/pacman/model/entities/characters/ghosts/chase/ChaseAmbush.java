package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;

public class ChaseAmbush implements ChaseBehaviour{
    private static final int TILES_OFFSET = 4;
    public ChaseAmbush(){

    }

    public Position getChasePosition(Ghost ghost) {
        Position pacPosition = ghost.getLevel().getPacman().getPosition();
        Position tilesDirection = new Position (ghost.getLevel().getPacman().getDirection().getX()*TILES_OFFSET,
                ghost.getLevel().getPacman().getDirection().getY()*TILES_OFFSET);
        return Position.add(pacPosition, tilesDirection);
    }
}
