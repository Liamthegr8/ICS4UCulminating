import java.awt.*;
public class Boxy extends Rectangle {
    double velX, velY;
    double accelY;
    final double friction = 0.1;

    Boxy() {
        super(0, 0, 100, 100);
        velX = 0;
        velY = 0;
    }
    void applyMovementInput(double x, double y) {
        velX += x;
        velY += y;
    }
    void tick() {
        //apply friction
        if(velX<1 && velX>-1){
            velX=0;
        }
        if(velY<1 && velY>-1){
            velY=0;
        }
        velX *= (1-friction);
        velY *= (1-friction);


        this.x += velX;
        this.y += velY;
    }
}
