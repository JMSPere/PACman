package edu.uoc.pacman.model.utils;

import java.util.Objects;

import static java.lang.Math.*;

public class Position {
    private int x;
    private int y;

    public Position (int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public int getX(){
        return this.x;
    }
    public void setX (int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public void setY (int y){
        this.y = y;
    }
    public double distance (Position other){
        if(other == null){
            return 0;
        }
        return sqrt(pow(abs(this.getX() - other.getX()), 2) + pow(abs(this.getY() - other.getY()), 2));
    }
    public static Position add (Position p1, Position p2){
        return new Position(p1.getX() + p2.getX(), p1.getY() + p2.getY());
    }
    @Override
    public boolean equals (Object other){
        return other.getClass() == this.getClass() && ((Position) other).getX() == getX() && ((Position) other).getY() == getY();
    }
    @Override
    public int hashCode(){
        return Objects.hash(getX(), getY());
    }
    @Override
    public String toString(){
        return getX()+","+getY();
    }
}
