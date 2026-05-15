import java.awt.*;
import java.util.*;

public class Player extends Rectangle {
    ArrayList<Tile> surroundingTiles;
    int[] playerLocation;
    double xx, yy;
    double vx, vy;
    double gravity;
    final double friction = 0.2;
    //checks to see if the player is able to control the player character(Depricated, was intended for room transitions)
    //repurposed for dashes
    boolean canControl;
    int playerHealth;
    boolean isDead;
    boolean canDash;
    boolean isGrounded;
    boolean isTouchingRightWall;
    boolean isTouchingLeftWall;
    int maxvx = 40;
    int maxvy = 40;
    int collisionRadiusCheck = width*3;
    int noGravityTime=0;
    int dashTime=10;
    double dashSpeed=25;
    double dashSpeedDiag=(dashSpeed * Math.sqrt(0.5));
    double dashx=0;
    double dashy=0;
    //true is right false is left
    boolean directionFaced=true;
    boolean isDash=false;
    final double airFriction = 0.04;
    int noControlTime=0;
    int coyoteTime=0;
    int wallCoyoteTime=0;
    int bufferTime = 0;


    Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        surroundingTiles = new ArrayList<Tile>();
        vx = 0;
        vy = 0;
        gravity = 0.9;
        isDead = false;
        playerHealth = 100;
        canControl = true;
        canDash = false;
        isGrounded = false;
        isTouchingRightWall = false;
        isTouchingLeftWall = false;
        playerLocation = null;    
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
    //changed so that now always update veloctity
    //but you cant change velocity if you cant control
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
    //works evev if player cant control
    void setDashVelocity(double VX, double VY){
        vx = VX; 
        vy = VY;
    }

    void applyVelocity() {
        //apply gravity
        //the 3 lines below moved to outside in logic statement before trymove
        // if (vy != 0) {
        //     isGrounded = false;
        // }
        
        //appliesGravity
        //noGravityTime ticks down one every tick until it equals to 0
        if(noGravityTime==0){
            vy += gravity;
        }
        
        
        //apply friction only if player can control(not it dash)
        if(canControl){
            if(isGrounded){
                vx *= (1-friction);
                vy *= (1-airFriction);
            }else{
                vy *= (1-airFriction);
                vx *= (1-airFriction);
            }
            
            
        }
        //round super low velocities to 0
        if(vx<0.1 && vx>-0.1){
            vx=0;
        }
        if(vy<0.1 && vy>-0.1){
            vy=0;
        }
        
        //max speed limits
        /** 
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
            */

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
                            
                            Tile reaLocationTile = new GhostTile(t);
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
        System.out.println(yAmount);
        this.y = (int)Math.round(this.yy);

        for (int i=0; i<surroundingTiles.size(); i++) {
            Tile t = surroundingTiles.get(i);
            if (t != null) {
                Rectangle tile = new Rectangle(t.x, t.y, t.width, t.height);
                Rectangle testground = new Rectangle(t.x, t.y-1, t.width, t.height);
                if (this.intersects(tile) && t.isCollidable) {  
                    if (t.killPlayer) {
                        isDead = true;
                    }   
                        if (yAmount > 0) {
                            //from top
                            this.y = tile.y - this.height;
                            isGrounded = true;
                            coyoteTime = 10;
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
                }else if (this.intersects(testground) && t.isCollidable) {
                    isGrounded =true;
                    coyoteTime=100;
                }

                
            }
        }
    }

    //Checks where player is on map
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
        coyoteTime--;
        if(bufferTime>0){
            jump();
        }
        getSurroundingTiles(map);
        playerLocation = getPlayerLocation(map);
        // System.out.println(xx);
        // System.out.println(yy);
        if(isDash){

            setDashVelocity(dashx, dashy);

            if(noGravityTime > 0)  {
                noGravityTime--;
            }

            //nocontrol
            if(noControlTime>0){
                canControl=false;
                noControlTime--;
            }else{
                isDash=false;
                setDashVelocity(0, 0);
            }
        }else{
            noControlTime=0;
            noGravityTime=0;
            canControl=true;
        }

        if (true) {
            isGrounded = false; //if removed, weeeeeeeeeeeee
            tryMoveY(vy, surroundingTiles);
        } //else not moving, no new updates basically
        //assume grounded, touchingWalls are false until proven
        //try to move (considers collisions)
        if (true) {
            isTouchingLeftWall = false;
            isTouchingRightWall = false;
            tryMoveX(vx, surroundingTiles);
        } //else not moving, no new updates basically
        

        //check death
        if (playerHealth <= 0) {
            isDead = true;
        } // else dont force to dead, as we might manually set it dead true
        applyVelocity();
    }

    void jump() {
        // isGrounded = false;
        // vy = 0;
        bufferTime--;
        if (coyoteTime>0) {
            isDash = false;
            //System.out.println(vx);
            setVelocity(vx, -25); 
            //System.out.println(vx);
            coyoteTime=0;
            bufferTime=0;
        }
        
            
    }
    
    void wallJump() {
    }
    void dash(boolean w, boolean a, boolean s, boolean d) {
        
    if (canDash) {
        canDash = false;
        noGravityTime = dashTime;
        noControlTime = dashTime;
        isDash=true;
            if (w && !a && !d) {
           // setVelocity(0,-dashSpeed);
            dashx = 0.00;
            dashy = -dashSpeed;
        }
        else if (a && !w && !s && !d) {
            setVelocity(-dashSpeed,0);
            dashx = -dashSpeed;
            dashy = 0;
        }
        else if (s && !a && !d) {
            //setVelocity(0,dashSpeed);
            dashx = 0.0;
            dashy = dashSpeed;
        }
        else if (d && !w && !s && !a) {
            //setVelocity(dashSpeed,0);
            dashx = dashSpeed;
            dashy = 0;
        }
        else if (w && a) {
            //setVelocity(-dashSpeedDiag,-dashSpeedDiag);
            dashx = -dashSpeedDiag;
            dashy = -dashSpeedDiag;
        }
        else if (s && a) {
            //setVelocity(-dashSpeedDiag,dashSpeedDiag);
            dashx = -dashSpeedDiag;
            dashy = dashSpeedDiag;
        }
        else if (w && d) {
            //setVelocity(dashSpeedDiag,-dashSpeedDiag);
            dashx = dashSpeedDiag;
            dashy = -dashSpeedDiag;
        }
        else if (s && d) {
            //setVelocity(dashSpeedDiag,dashSpeedDiag);
            dashx = dashSpeedDiag;
            dashy = dashSpeedDiag;
        }
        else if(directionFaced){
            //setVelocity(dashSpeedDiag,0);
            dashx = dashSpeed;
            dashy = 0;
        }
        else{
            //setVelocity(-dashSpeedDiag,0);
            dashx = -dashSpeed;
            dashy = 0;
        }    
    }
    
        
    }
    void dashPastWall() { // just teleport x and y, and then apply some velocity in that dir
    }
    void parachute() {
    }
    void grapplingHook() {   
    }

}