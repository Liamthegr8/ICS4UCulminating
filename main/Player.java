import java.awt.*;
import java.util.*;

public class Player extends Rectangle {
    ArrayList<Tile> surroundingTiles;
    double vx, vy;
    double gravity;
    final double friction = 0.1;
    boolean canMove;
    boolean isDead;
    boolean canDash;
    boolean isGrounded;
    boolean isTouchingRightWall;
    boolean isTouchingLeftWall;
    int maxvx = 15;
    int maxvy = 15;

    Player(int width, int height) {
        super(0, 0, width, height);
        surroundingTiles = new ArrayList<Tile>();
        vx = 0;
        vy = 0;
        gravity = 1.07;
        isDead = false;
        canMove = true;
    }

    //apply velocity
    void updateVelocity(double x, double y) {
        vx += x;
        vy += y;
    }

    void applyVelocity() {
        //apply gravity
        if (vy != 0) {
            isGrounded = false;
        }
        
        vy += gravity;
        // if (isGrounded == false) {
        //     vy += gravity;
        // } else {vy = 0;}
        // if (isGrounded == true && vy > 0) {
        //     vy = 0;
        // }
        
        //apply friction
        vx *= (1-friction);
        vy *= (1);
        
        //round super low velocities to 0
        if(vx<0.1 && vx>-0.1){
            vx=0;
        }
        if(vy<0.1 && vy>-0.1){
            vy=0;
        }
        
        //max speed limits
        if (vx >= maxvx) {
            vx = maxvx;
        }
        if (vx <= -maxvx) {
            vx = -maxvx;
        }
        if (vy >= maxvy) {
            vy = maxvy;
        }
        if (vy <= -maxvy) {
            vy = -maxvy;
        }
    }

    void getSurroundingTiles(Map map) {
        surroundingTiles.clear();

        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                if (r != null) {
                    for (int k=0; k<r.roomTiles.length; k++) {
                        for (int l=0; l<r.roomTiles.length; l++) {
                            Tile t = r.roomTiles[k][l];
                            if (t != null) {
                                
                                //FIXED 100 proximity check
                                Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                if ((Math.abs(this.x-tile.x) < 100) && (Math.abs(this.y-tile.y) < 100)) {
                                    surroundingTiles.add(t);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //scratch griffpatch raycaster ref with edge setback
    void tryMoveX(int xAmount, Map map) {
        this.x += xAmount;

        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                if (r != null) {
                    for (int k=0; k<r.roomTiles.length; k++) {
                        for (int l=0; l<r.roomTiles.length; l++) {
                            Tile t = r.roomTiles[k][l];
                            if (t != null) {
                                
                                Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                if (this.intersects(tile)) {     
                                    if (xAmount > 0) {
                                        //from right
                                        this.x = tile.x - this.width;
                                        isTouchingRightWall = true;
                                        isTouchingLeftWall = false;
                                    } else if (xAmount < 0) {
                                        //from left
                                        this.x = tile.x + tile.width;
                                        isTouchingRightWall = false;
                                        isTouchingLeftWall = true;
                                    }
                                    this.vx = 0;
                                }
                                    

                            }
                        }
                    }
                }
            }
        }
    }

    void tryMoveY(int yAmount, Map map) {
        this.y += yAmount;

        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                if (r != null) {
                    for (int k=0; k<r.roomTiles.length; k++) {
                        for (int l=0; l<r.roomTiles.length; l++) {
                            Tile t = r.roomTiles[k][l];
                            if (t != null) {
                                
                                Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                if (this.intersects(tile)) {  
                                    if (yAmount > 0) {
                                        //from top
                                        this.y = tile.y - this.height;
                                        isGrounded = true;
                                        // this.vy = 0;
                                    } else if (yAmount < 0) {
                                        //from bottom
                                        this.y = tile.y + tile.height;
                                        isGrounded = false;
                                    }
                                    this.vy = 0;
                                }
                               
                            }
                        }
                    }
                }
            }
        }
    }

    void tick(Map map) {
        isGrounded = false;
        isTouchingLeftWall = false;
        isTouchingRightWall = false;

        applyVelocity();
        
        //update position (considers collisions)
        if (vx != 0) {
            tryMoveX((int)vx, map);
        }
        if (vy != 0) {
            tryMoveY((int)vy, map);
        }

        // tryMoveX((int)vx, map);
        // tryMoveY((int)vy, map);
        
    }

    // void checkCollision() {
        
    // }

    void jump() {
        // isGrounded = false;
        // vy = 0;
        updateVelocity(0, -50);     
    }
    






    void wallJump() {
    }
    void dash() {
    }
    void dashPastWall() {
    }
    void parachute() {
    }
    void grapplingHook() {   
    }

}
