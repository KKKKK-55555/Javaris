package Javaris;

import java.util.Random;

public class Javali {
    private Random rand;
    private int javaliSize = 1;
    private int[][][] javaliTypes;
    private int x, y;
    private int javaliType;
    private int javaliAngle;
    //private int javaliAngleSize = 2;

    private int[][][] javali_fixed = {
        { 
            { 1, 0, 0, 0 },
        }, // 0度
        {
            { 1, 0, 0, 0 },
        }, // 180度
    };

    public Javali() {
        this.rand = new Random();
        setJavaliType();
        setJavaliAngle();
        randSet();
    }

    public int getJavaliType() {
        return this.javaliType;
    }

    public int getJavaliSize() {
        return this.javaliSize;
    }

    public int getJavaliX() {
        return this.x;
    }

    public int getJavaliY() {
        return this.y;
    }

    public int getJavaliAngle() {
        return this.javaliAngle;
    }

    public int[][][] getJavali() {
        return this.javaliTypes;
    }

    public void setJavaliType() {
        this.javaliType = 1;
    }

    public void setJavaliAngle() {
        this.javaliAngle = 1;
    }

    public void randSet() {
        switch (getJavaliType()) {
            case 1:
                this.javaliTypes = this.javali_fixed;
                this.y = 5 + rand.nextInt(5);
                this.x = 1 + rand.nextInt(9);
                break;
        }
    }
}
