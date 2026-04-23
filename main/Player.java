import java.awt.*;
public class Player extends Rectangle {
    double velX, velY;
    double gravity;
    final double friction = 0.1;
    boolean canMove;
    boolean isDead;
    boolean canDash;
    boolean isGrounded;
    int maxVelX = 40;
    int maxVelY = 40;

    Player(int width, int height) {
        super(0, 0, width, height);
        velX = 0;
        velY = 0;
        gravity = 1;
        isDead = false;
        canMove = true;
    }

    //apply velocity
    void applyVelocity(double x, double y) {
        velX += x;
        velY += y;
    }

    void tick() {
        //round super low velocities to 0
        if(velX<1 && velX>-1){
            velX=0;
        }
        if(velY<1 && velY>-1){
            velY=0;
        }
        
        //update velocity
        velX *= (1-friction);
        velY *= (1-friction);

        this.x += velX;
        this.y += velY;

        if (isGrounded == false) {
            velY += gravity;
        }
        
        if (isGrounded == true && velY > 0) {
            velY = 0;
        }
        //max speed limits
        if (velX >= maxVelX) {
            velX = maxVelX;
        }
        if (velX <= -maxVelX) {
            velX = -maxVelX;
        }
        if (velY >= maxVelY) {
            velY = maxVelY;
        }
        if (velX <= -maxVelY) {
            velX = -maxVelY;
        }
    }

    // void checkCollision() {
        
    // }

    void jump() {
        applyVelocity(0, -20);
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
