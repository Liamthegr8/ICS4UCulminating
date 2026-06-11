/**
 * Player.java
 * Class containing all the data and methods pertaining to the Player. Collision, movement, and abilities can all be found here.
 * Created by Tanush, Liam, Erik
 */
import java.awt.*;
import java.util.*;

public class Player extends Rectangle {
    ArrayList<Tile> surroundingTiles;
    ArrayList<Tile> drawnTiles;
    int[] playerLocation;
    int[] lastSafeLocation;
    double xx, yy, vx, vy, gravity;
    //checks to see if the player is able to control the player character(Depricated, was intended for room transitions)
    //repurposed for dashes
    boolean canControl, isDead, canDash, isGrounded, isTouchingRightWall, isTouchingLeftWall, isPlayerCollidable;
    int numDashes = 1;
    boolean[] abilities = new boolean[5];
    int playerHealth, lastSurfaceTouched;
    int maxvx = 40;
    int maxvy = 40;
    int collisionRadiusCheck = width*3;
    boolean damageImmunity = false;

    double jumpStrength = 20;
    double dashSpeed=15;
    double dashSpeedDiag=(dashSpeed * Math.sqrt(0.5));
   //double dashSpeedDiag= dashSpeed*.7;
    double dashx=0;
    double dashy=0;
    //true is right false is left
    boolean directionFaced=true;
    boolean isDash=false;
    final double airFriction = 0.03;
    final double friction = 0.1;
    int noControlTime=0;
    int noGravityTime=0;
    int noCollisionTime=0;
    int dashTime=10;

    int coyoteTime=0;
    int bufferTime = 0;

    boolean isWin=false;

    boolean fastFall = false;
    boolean reverseGravity = false;
    boolean floating = false;

    boolean godMode = false;

    Sound dashSound;

    Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        surroundingTiles = new ArrayList<Tile>();
        drawnTiles = new ArrayList<Tile>();
        vx = 0;
        vy = 0;
        gravity = 1;
        isDead = false;
        playerHealth = 200;
        canControl = true;
        canDash = false;
        isGrounded = false;
        isTouchingRightWall = false;
        isTouchingLeftWall = false;
        playerLocation = null;
        lastSafeLocation = new int[2];
        isPlayerCollidable = true;    
        //testing
        // abilities[0] = true;
        // abilities[1] = true;
        // abilities[2] = true;
        // abilities[3] = true;
        // abilities[4] = true;

        dashSound = new Sound();
        dashSound.load("/assets/dash.wav");
    }

    void applyDamage(int dmg) {
        playerHealth -= dmg;
    }

    /**
     * set players global coordinates
     * @param x x coordinate
     * @param y y coordinate 
     */
    void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * change players global coordinates
     * @param x x coordinate
     * @param y y coordinate 
     */
    void changeCoordinates(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    /**
     * update player's velocity
     * @param vx    horizontal velocity
     * @param vy    vertical velocity
     */
    void updateVelocity(double vx, double vy) {
            if (canControl) {
                this.vx += vx; //IF canControl, then apply these
                this.vy += vy;
            }
    }

    /**
     * set player's velocity
     * @param vx    horizontal velocity
     * @param vy    vertical velocity
     */
    void setVelocity(double vx, double vy) {
            if (canControl) {
                this.vx = vx; //IF canControl, then apply these
                this.vy = vy;
            }
    }
    /**
     * set player's velocity on NO conditions
     * @param vx    horizontal velocity
     * @param vy    vertical velocity
     */
    void setDashVelocity(double VX, double VY){
        vx = VX; 
        vy = VY;
    }

    /**
     * handle gravity and velocity
     */
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

    
    /**
     * return tiles around the player within certain radius
     * @param map   reference of map
     */
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
    void getDrawnTiles(Map map) {
        drawnTiles.clear();

        for (int i=0; i<map.mapRooms.length; i++) {
            for (int j=0; j<map.mapRooms.length; j++) {
                Room r = map.mapRooms[i][j];
                if (r != null) {
                    for (int k=0; k<r.roomTiles.size(); k++) {
                        Tile t = r.roomTiles.get(k);
                        if (t != null) {
                            
                            //proximity checks
                            Rectangle collisionArea = new Rectangle (x+(width/2)-Game.windowX, y+(height/2)-Game.windowY, Game.windowX*2, Game.windowY*2);
                            
                            Tile reaLocationTile = new GhostTile(t);
                            reaLocationTile.x += i*Room.roomWidth;
                            reaLocationTile.y += j*Room.roomHeight;

                            Rectangle tileArea = new Rectangle(reaLocationTile.x, reaLocationTile.y, reaLocationTile.width, reaLocationTile.height);

                            if (collisionArea.intersects(tileArea)) {
                                // Keep OG tiles in room-local coordinates, use edited copy tile for collision
                                drawnTiles.add(reaLocationTile);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //scratch griffpatch raycaster ref with edge setback
    /**
     * try to move player in x direction, if collides with wall, move player next to wall and set velocity to 0, sets isTouchingWall variables
     * @param xAmount   amount to move player in x direction
     * @param surroundingTiles  list of tiles around player to check for collisions with
     */
    void tryMoveX(double xAmount, ArrayList<Tile> surroundingTiles) {
        this.xx = (double)x;
        this.xx += xAmount;
        this.x = (int)Math.round(this.xx);

        for (int i=0; i<surroundingTiles.size(); i++) {
            
            Tile t = surroundingTiles.get(i);
            if (t != null) {
                //real x and y required
                Rectangle tile = new Rectangle(t.x, t.y, t.width, t.height);

                if (this.intersects(tile) && t.isCollidable && (isPlayerCollidable || t.overideDisabledCollisions)) {     //tanush edited for dash past wall
                    if(t.winCondition){
                            isWin = true;
                        }
                    if (t.tileID == 05 && !t.enabled) {
                        continue;
                    }
                    else if (t.killPlayer && !damageImmunity) {
                        this.damageImmunity = true;
                        applyDamage(25);
                        this.x = 11400;
                        this.y = 8670;
                        this.vx = 0;
                        this.vy = 0;
                        break;
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
                        if (abilities[0]) {
                            lastSurfaceTouched = 3;
                            coyoteTime = 20;
                        }
                    } 
                    else {
                        isTouchingRightWall = false;
                    }
                    if (playerShiftedLeft.intersects(tile)) {
                        //from left
                        isTouchingLeftWall = true;
                        if (abilities[0]) {
                            lastSurfaceTouched = 2;
                            coyoteTime = 20;
                        }
                    } 
                    else {
                        isTouchingLeftWall = false;
                    }
                }
            }
        }
    }

    /**
     * try to move player in y direction, if collides with wall, move player next to wall and set velocity to 0, sets isGrounded variable
     * @param yAmount   amount to move player in y direction
     * @param surroundingTiles  list of tiles around player to check for collisions with
     */
    void tryMoveY(double yAmount, ArrayList<Tile> surroundingTiles) {
        this.yy = (double)y;
        this.yy += yAmount;
        //System.out.println(yAmount);
        this.y = (int)Math.round(this.yy);

        for (int i=0; i<surroundingTiles.size(); i++) {
            Tile t = surroundingTiles.get(i);
            if (t != null) {
                Rectangle tile = new Rectangle(t.x, t.y, t.width, t.height);
                Rectangle testground = new Rectangle(t.x, t.y-1, t.width, t.height);
                
                if (this.intersects(tile) && t.isCollidable && (isPlayerCollidable || t.overideDisabledCollisions)) { //tanush edited for dash past wall
                    if (t.tileID == 05 && !t.enabled) {
                        continue;
                    }
                    if (t.winCondition) {
                        isWin = true;
                    }

                    if (t.ability1) this.abilities[0] = true; //wall jump
                    if (t.ability2) this.abilities[1] = true; //phase dash
                    if (t.ability3) this.abilities[2] = true; //double dash
                    if (t.ability4) this.abilities[3] = true; //parachute
                    if (t.ability5) this.abilities[4] = true; //grav flip
                    if (abilities[0] || abilities[1] || abilities[2] || abilities[3] || abilities[4]) {
                        System.out.println(String.valueOf(abilities[0])); 
                        System.out.println(String.valueOf(abilities[1])); 
                        System.out.println(String.valueOf(abilities[2])); 
                        System.out.println(String.valueOf(abilities[3])); 
                        System.out.println(String.valueOf(abilities[4])); 
                    }

                    if (t.killPlayer && !damageImmunity) {
                        this.damageImmunity = true;
                        applyDamage(25);
                        this.x = 11400;
                        this.y = 8670;
                        this.vx = 0;
                        this.vy = 0;
                        break;
                    }
                    if (yAmount > 0) {
                            //from top
                            // if (!t.killPlayer && t.tileID != 03 && isGrounded) {
                            //     lastSafeLocation[0] = this.x;
                            //     lastSafeLocation[1] = this.y;
                            // }
                            this.y = tile.y - this.height;
                            isGrounded = true;
                            lastSurfaceTouched = 1;
                            coyoteTime = 20;
                            canDash = true;
                            if (abilities[2]) {
                                numDashes = 2;
                            }
                            else {
                                numDashes = 1;
                            }

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
                            if (reverseGravity) {
                                isGrounded = true;
                                lastSurfaceTouched = 1;
                                coyoteTime = 20;
                                canDash = true;
                                if (abilities[2]) {
                                    numDashes = 2;
                                }
                                else {
                                    numDashes = 1;
                                }
                            }
                        }

                        this.vy = 0;
                }else if (this.intersects(testground) && t.isCollidable) {
                    isGrounded =true;
                    coyoteTime=20;
                }

                
            }
        }
    }

    /**
     * return player location in the form of an integer array 
     * @param map   reference to map
     * @return  int array with 2 values, x and y location of room which is in the map
     */
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

    /**
     * update player location, get tiles and check collisions and other variables every tick
     * @param map   reference to map
     */
    void tick(Map map) {
         //System.out.println(isWin);
        //isGrounded = false;
        //System.out.println(coyoteTime);
        if (coyoteTime>0){coyoteTime--;}
        if(bufferTime>0){
            jump();
        }
        gravityUpdate();
        getSurroundingTiles(map);
        getDrawnTiles(map);
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
                setDashVelocity(vx*0.4, vy*0.4);
            }

            //tanush  edited code
            if(noCollisionTime > 0)  {
                isPlayerCollidable = false;
                noCollisionTime--;
            } else {
                isPlayerCollidable = true;
            }

        }else{
            noControlTime=0;
            noGravityTime=0;
            noCollisionTime=0;
            canControl=true;
            isPlayerCollidable = true;
        }

        isGrounded = false; //if removed, weeeeeeeeeeeee
        tryMoveY(vy, surroundingTiles);
         //else not moving, no new updates basically
        //assume grounded, touchingWalls are false until proven
        //try to move (considers collisions)

        isTouchingLeftWall = false;
        isTouchingRightWall = false;
        tryMoveX(vx, surroundingTiles);
         //else not moving, no new updates basically
        

        //check death
        if (playerHealth <= 0) {
            isDead = true;
        } // else dont force to dead, as we might manually set it dead true
        applyVelocity();
        this.damageImmunity = false;
        if (godMode) this.damageImmunity=true;
    }

    //ability methods

    /**
     * make player jump and handle wall jumps and check coyote and buffer time and set velocities accordingly
     */
    void jump() {
        // isGrounded = false;
        // vy = 0;
        bufferTime--;
        if (coyoteTime>0) {
            if(lastSurfaceTouched == 1) {
                isDash = false;
                //System.out.println(vx);
                //setDashVelocity(vx, -20+vy); 
                if(isDash) {
                    setDashVelocity(vx, vy); 
                }
                else{
                    setDashVelocity(vx, -jumpStrength); 
                }
                //System.out.println(vx);
                coyoteTime=0;
                bufferTime=0;
            }
            else if (abilities[0]) {
                if (lastSurfaceTouched == 2) {
                    if (isDash){
                        setDashVelocity(5, vy); 
                    }
                    else {
                        setDashVelocity(5, -jumpStrength); 
                    }
                    isDash = false;
                    //System.out.println(vx);
                
                    //System.out.println(vx);
                    coyoteTime=0;
                    bufferTime=0;
                }
                else if(lastSurfaceTouched == 3) {
                    if (isDash) {
                        setDashVelocity(-5, vy); 
                    }
                    else {
                        setDashVelocity(-5, -jumpStrength); 
                    }
                    isDash = false; 
                    //System.out.println(vx);
                    coyoteTime=0;
                    bufferTime=0;
                }
            }   
        }   
    }
    
    /**
     * make player dash in a direction based on input and set velocities as needed
     * @param w bool that is true if player is pressing w
     * @param a bool that is true if player is pressing a
     * @param s bool that is true if player is pressing s
     * @param d bool that is true if player is pressing d
     */
    void dash(boolean w, boolean a, boolean s, boolean d) {
        if (canDash) {
            dashSound.play();
            numDashes--;
            if(numDashes<=0){
                canDash=false;
            }
            noGravityTime = dashTime;
            noControlTime = dashTime;
            isDash=true;
            coyoteTime = 0;
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
        
        //not used methods yet, for future    
    }
    void dashPastWall(boolean w, boolean a, boolean s, boolean d) { // just teleport x and y, and then apply some velocity in that dir
        if (abilities[1]) {
            if (canDash) {
        numDashes--;
        if(numDashes<=0){
            canDash=false;
        }
        noGravityTime = dashTime;
        noControlTime = dashTime;
        noCollisionTime = dashTime;
        isDash=true;
        coyoteTime = 0;
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
    }
    void gravityShift() {
        if (abilities[4]) {
            if(isGrounded){
            gravity *= -1;
            jumpStrength *= -1;
            reverseGravity = !reverseGravity;}
        }
    }
    void gravityUpdate() {
        if (reverseGravity) {
            if(fastFall){
            gravity = -1.1;
        }else gravity = -.8;
        }
        else {
            if(fastFall){
                gravity = 1.1;
            }else gravity = .8;
        }
        if (floating) {
            if (abilities[3]) {
                if (vy > 0) {
                    gravity *= 0.3;
                }
            }
        }
    }

}