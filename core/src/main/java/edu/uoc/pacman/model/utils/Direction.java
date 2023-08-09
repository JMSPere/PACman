package edu.uoc.pacman.model.utils;

public enum Direction {
    DOWN (0, 1, 20),
    LEFT (-1, 0, 21),
    RIGHT (1, 0, 22),
    UP (0, -1, 19);

    private final int x;
    private final int y;
    private final int keyCode;
    Direction (int x, int y, int keyCode){
        this.keyCode = keyCode;
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getKeyCode(){
        return this.keyCode;
    }
    public static Direction getDirectionByKeyCode(int keyCode){
        switch (keyCode){
            case 20:
                return DOWN;
            case 21:
                return LEFT;
            case 22:
                return RIGHT;
            case 19:
                return UP;
            default:
                return null;
        }
    }
    public Direction opposite(){
        switch (getDirectionByKeyCode(this.getKeyCode())){
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            default:
                return null;
        }
    }
}
