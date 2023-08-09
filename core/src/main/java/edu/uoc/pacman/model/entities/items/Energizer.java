package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.utils.Sprite;
import edu.uoc.pacman.model.utils.Position;

public class Energizer extends MapItem implements Pickable, Scorable {
    private boolean picked;
    private static final int POINTS = 5;

    public Energizer (Position position){
        super(position, true, Sprite.ENERGIZER);
    }

    public int getPoints(){
        return this.POINTS;
    }
    public boolean isPicked(){
        return this.picked;
    }
    public void setPicked(boolean picked){
        this.picked = picked;
    }
}
