import java.awt.*;
public class Player extends Rectangle {
    double vx, vy;
    double gravity;
    final double friction = 0.07;
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
        vx = 0;
        vy = 0;
        gravity = 1.0;
        isDead = false;
        canMove = true;
    }

    //apply velocity
    void applyVelocity(double x, double y) {
        vx += x;
        vy += y;
    }

    void tick() {
        //round super low velocities to 0
        if(vx<1 && vx>-1){
            vx=0;
        }
        if(vy<1 && vy>-1){
            vy=0;
        }
        
        //update apply friction limits
        vx *= (1-friction);
        vy *= (1);

        //update position
        this.x += vx;
        this.y += vy;

        //apply gravity
        if (isGrounded == false) {
            vy += gravity;
        } else {vy = 0;}
        // if (isGrounded == true && vy > 0) {
        //     vy = 0;
        // }

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
        if (vx <= -maxvy) {
            vy = -maxvy;
        }
    }

    // void checkCollision() {
        
    // }

    void jump() {
        isGrounded = false;
        vy = 0;
        applyVelocity(0, -14);     
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
