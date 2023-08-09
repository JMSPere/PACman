package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;

public interface ChaseBehaviour {
    Position getChasePosition(Ghost ghost);
}
