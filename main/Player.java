import java.awt.*;
import java.util.*;

public class Player extends Rectangle {
    ArrayList<Tile> surroundingTiles;
    int[] playerLocation;
    double xx, yy;
    double vx, vy;
    double gravity;
    final double friction = 0.1;
    boolean canControl;
    int playerHealth;
    boolean isDead;
    boolean canDash;
    boolean isGrounded;
    boolean isTouchingRightWall;
    boolean isTouchingLeftWall;
    int maxvx = 15;
    int maxvy = 15;
    int collisionRadiusCheck = width*3;

    Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        surroundingTiles = new ArrayList<Tile>();
        vx = 0;
        vy = 0;
        gravity = 1.07;
        isDead = false;
        playerHealth = 100;
        canControl = true;
        canDash = false;
        isGrounded = false;
        isTouchingRightWall = false;
        isTouchingLeftWall = false;    
    }

    // void applyDamage(int dmg) {
    //     playerHealth -= dmg;
    // }

    void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void changeCoordinates(int x, int y) {
        this.x += x;
        this.y += y;
    }

    void updateVelocity(double vx, double vy) {
            if (canControl) {
                this.vx += vx; //IF canControl, then apply these
                this.vy += vy;
            }
    }

    void setVelocity(double vx, double vy) {
            if (canControl) {
                this.vx = vx; //IF canControl, then apply these
                this.vy = vy;
            }
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

    
    //for enhanced collisions
 void getSurroundingTiles(Map map) {
        surroundingTiles.clear();

        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                if (r != null) {
                    for (int k=0; k<r.roomTiles.size(); k++) {
                        Tile t = r.roomTiles.get(k);
                        if (t != null) {
                            
                            //proximity checks
                            Rectangle collisionArea = new Rectangle (x+(width/2)-collisionRadiusCheck, y+(height/2)-collisionRadiusCheck, collisionRadiusCheck*2, collisionRadiusCheck*2);
                            
                            Tile reaLocationTile = new Tile(t);
                            reaLocationTile.x += i*Room.roomWidth;
                            reaLocationTile.y += j*Room.roomHeight;

                            Rectangle tileArea = new Rectangle(reaLocationTile.x, reaLocationTile.y, reaLocationTile.width, reaLocationTile.height);

                            if (collisionArea.intersects(tileArea)) {
                                // Keep OG tiles in room-local coordinates, use edited copy tile for collision
                                surroundingTiles.add(reaLocationTile);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //scratch griffpatch raycaster ref with edge setback
    //is the passing class attribute suroundtiles to func redudant?
    void tryMoveX(double xAmount, ArrayList<Tile> surroundingTiles) {
        this.xx = (double)x;
        this.xx += xAmount;
        this.x = (int)Math.round(this.xx);

        for (int i=0; i<surroundingTiles.size(); i++) {
            
            Tile t = surroundingTiles.get(i);
            if (t != null) {
                //real x and y required
                Rectangle tile = new Rectangle(t.x, t.y, t.width, t.height);

                if (this.intersects(tile) && t.isCollidable) {     
                    if (t.killPlayer) {
                        isDead = true;
                    }
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
                    Rectangle playerShiftedRight = new Rectangle(this.x+5, this.y, this.width, this.height);
                    Rectangle playerShiftedLeft = new Rectangle(this.x-5, this.y, this.width, this.height);
                    if (playerShiftedRight.intersects(tile)) {
                        //from right
                        isTouchingRightWall = true;
                    } else {
                        isTouchingRightWall = false;
                    }
                    if (playerShiftedLeft.intersects(tile)) {
                        //from left
                        isTouchingLeftWall = true;
                    } else {
                        isTouchingLeftWall = false;
                    }
                }
            }
        }
    }

    void tryMoveY(double yAmount, ArrayList<Tile> surroundingTiles) {
        this.yy = (double)y;
        this.yy += yAmount;
        this.y = (int)Math.round(this.yy);

        for (int i=0; i<surroundingTiles.size(); i++) {
            
            Tile t = surroundingTiles.get(i);
            if (t != null) {
                Rectangle tile = new Rectangle(t.x, t.y, t.width, t.height);

                if (this.intersects(tile) && t.isCollidable) {  
                    if (t.killPlayer) {
                        isDead = true;
                    }   
                        if (yAmount > 0) {
                            //from top
                            this.y = tile.y - this.height;
                            isGrounded = true;
                            canDash = true;

                            //movingPlatformTile Script
                            /* if (surroundingTiles.get(i) instanceof MovingPlatformTile) {
                                x += t.getMovePlayerX();
                                y += t.getMovePlayerY();
                            }
                            */
                            
                            
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

    int[] getPlayerLocation(Map map) {
        int[] tempPlayerLocation = new int[2]; //[roomx, roomy]
        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                
                if (r != null) {
                    Rectangle room = new Rectangle(i*Room.roomWidth, j*Room.roomHeight, Room.roomWidth, Room.roomHeight);
                    if (room.intersects(this)) {
                        tempPlayerLocation[0] = i;
                        tempPlayerLocation[1] = j;
                        return tempPlayerLocation;
                    }
                }
            }
        }
        return null;
        
    }

    void tick(Map map) {
        //isGrounded = false;

        applyVelocity();
        getSurroundingTiles(map);
        playerLocation = getPlayerLocation(map);
        // System.out.println(xx);
        // System.out.println(yy);

        if (vy != 0) {
            isGrounded = false; //if removed, weeeeeeeeeeeee
            tryMoveY(vy, surroundingTiles);
        } //else not moving, no new updates basically
        //assume grounded, touchingWalls are false until proven
        //try to move (considers collisions)
        if (vx != 0) {
            isTouchingLeftWall = false;
            isTouchingRightWall = false;
            tryMoveX(vx, surroundingTiles);
        } //else not moving, no new updates basically
        

        //check death
        if (playerHealth <= 0) {
            isDead = true;
        } // else dont force to dead, as we might manually set it dead true
    }

    void jump() {
        // isGrounded = false;
        // vy = 0;
        if (isGrounded) {
            updateVelocity(0, -50); 
        }
            
    }
    
    void wallJump() {
    }
    void dash(boolean w, boolean a, boolean s, boolean d) {
        if (canDash) {
            if (w && !a && !d) {
            setVelocity(0,-15);
        }
        if (a && !w && !s) {
            setVelocity(-15,0);
        }
        if (s && !a && !d) {
            setVelocity(0,15);
        }
        if (d && !w && !s) {
            setVelocity(15,0);
        }
        if (w && a) {
            setVelocity(-15,-15);
        }
        if (s && a) {
            setVelocity(-15,15);
        }
        if (w && d) {
            setVelocity(15,-15);
        }
        if (s && d) {
            setVelocity(15,15);
        }
        canDash = false;
        }
        
    }
    void dashPastWall() { // just teleport x and y, and then apply some velocity in that dir
    }
    void parachute() {
    }
    void grapplingHook() {   
    }

}