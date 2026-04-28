import java.awt.*;
import java.util.*;

public class Player extends Rectangle {
    ArrayList<TileRef> surroundingTiles;
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
    int collisionRadiusCheck = 100;

    Player(int width, int height) {
        super(0, 0, width, height);
        surroundingTiles = new ArrayList<TileRef>();
        vx = 0;
        vy = 0;
        gravity = 1.07;
        isDead = false;
        canMove = true;
        isGrounded = false;
        isTouchingRightWall = false;
        isTouchingLeftWall = false;
    }

    //apply velocity
    void updateVelocity(double x, double y) {
        vx += x;
        vy += y;
    }

    void applyVelocity() {
        //apply gravity
        //the 3 lines below moved to outside in logic statement before trymove
        // if (vy != 0) {
        //     isGrounded = false;
        // }
        
        //regardless if grounded
        vy += gravity;
        
        
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
                                
                                //proximity check
                                Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                //compare centers of objs
                                if ((Math.abs((this.x+(this.width/2))-(tile.x+(Tile.tileSize/2))) < collisionRadiusCheck) && (Math.abs((this.y+(this.height/2))-(tile.y+(Tile.tileSize/2))) < collisionRadiusCheck)) {
                                    surroundingTiles.add(new TileRef(t, i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    //scratch griffpatch raycaster ref with edge setback
    //is the passing class attribute suroundtiles to func redudant?
    void tryMoveX(int xAmount, ArrayList<TileRef> surroundingTiles) {
        
        this.x += xAmount;

        for (int i=0; i<surroundingTiles.size(); i++) {
            
            TileRef t = surroundingTiles.get(i);
            if (t != null) {
                Rectangle tile = new Rectangle(t.x, t.y, Tile.tileSize, Tile.tileSize);

                if (this.intersects(tile) && t.isCollidable) {     
                    if (xAmount > 0) {
                        //from right
                        this.x = tile.x - this.width;
                        // isTouchingRightWall = true;
                        // isTouchingLeftWall = false;
                    } else if (xAmount < 0) {
                        //from left
                        this.x = tile.x + tile.width;
                        // isTouchingRightWall = false;
                        // isTouchingLeftWall = true;
                    }
                    this.vx = 0;

                    // //check touching wall seperately, not dependent on trying to collide, just be next to it
                    // Rectangle playerShiftedRight = new Rectangle(this.x+5, this.y, this.width, this.height);
                    // Rectangle playerShiftedLeft = new Rectangle(this.x-5, this.y, this.width, this.height);
                    // if (playerShiftedRight.intersects(tile)) {
                    //     //from right
                    //     isTouchingRightWall = true;
                    // } else {
                    //     isTouchingRightWall = false;
                    // }
                    // if (playerShiftedLeft.intersects(tile)) {
                    //     //from left
                    //     isTouchingLeftWall = true;
                    // } else {
                    //     isTouchingLeftWall = false;
                    // }
                }
            }
        }
    }

    void tryMoveY(int yAmount, ArrayList<TileRef> surroundingTiles) {
        this.y += yAmount;

        for (int i=0; i<surroundingTiles.size(); i++) {
            
            TileRef t = surroundingTiles.get(i);
            if (t != null) {
                Rectangle tile = new Rectangle(t.x, t.y, Tile.tileSize, Tile.tileSize);

                if (this.intersects(tile) && t.isCollidable) {     
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

    

    void tick(Map map) {
        //isGrounded = false;

        applyVelocity();
        getSurroundingTiles(map);
        
        //assume grounded, touchingWalls are false until proven
        //try to move (considers collisions)
        if (vx != 0) {
            isTouchingLeftWall = false;
            isTouchingRightWall = false;
            tryMoveX((int)vx, surroundingTiles);
        } //else not moving, no new updates basically
        if (vy != 0) {
            isGrounded = false; //if removed, weeeeeeeeeeeee
            tryMoveY((int)vy, surroundingTiles);
        } //else not moving, no new updates basically

    }

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