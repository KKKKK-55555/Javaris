package Javaris;

import java.util.Random;

public class Mino {

    private Random rand;
    private int minoSize = 4;
    private int[][][] minoTypes;
    private int x, y;
    private int minoType;
    private int minoAngle;
    private int minoAngleSize = 4;

    private int[][][] mino_I = {
        { 
            { 1, 0, 0, 0 },
            { 1, 0, 0, 0 },
            { 1, 0, 0, 0 },
            { 1, 0, 0, 0 },
        }, // 0度
        {
            { 1, 1, 1, 1 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 0, 0, 0, 1 },
            { 0, 0, 0, 1 },
            { 0, 0, 0, 1 },
            { 0, 0, 0, 1 },
        }, // 180度
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 1, 1, 1, 1 },
        }, // 270度
    };

    private int[][][] mino_O = {
        {
            { 1, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 1, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 1, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 1, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
    };

    private int[][][] mino_S = {
        {
            { 0, 1, 1, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 0, 1, 0, 0 },
            { 0, 1, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 0, 1, 1, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 0, 1, 0, 0 },
            { 0, 1, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
    };

    private int[][][] mino_Z = {
        {
            { 1, 1, 0, 0 },
            { 0, 1, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 0, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 1, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 1, 1, 0, 0 },
            { 0, 1, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 0, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 1, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 2700度
    };

    private int[][][] mino_J = {
        {
            { 0, 1, 0, 0 },
            { 0, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 1, 0, 0, 0 },
            { 1, 1, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 1, 1, 0, 0 },
            { 1, 0, 0, 0 },
            { 1, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 1, 1, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
    };

    private int[][][] mino_L = {
        {
            { 1, 0, 0, 0 },
            { 1, 0, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 1, 1, 1, 0 },
            { 1, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
        {
            { 1, 1, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 0, 0, 1, 0 },
            { 1, 1, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度

    };

    private int[][][] mino_T = {
        {
            { 0, 1, 0, 0 },
            { 1, 1, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 1, 0, 0, 0 },
            { 1, 1, 0, 0 },
            { 1, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
        {
            { 1, 1, 1, 0 },
            { 0, 1, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 0, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度

    };

    private int[][][] mino_0 = {
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 0度
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 90度
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 180度
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
        }, // 270度
    };

    // I ミノ用の壁キックオフセット
    private static final int[][][] WALL_KICK_I = {
        {{0, 0}, {-2, 0}, {1, 0}, {-2, -1}, {1, 2}},
        {{0, 0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}},
        {{0, 0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}},
        {{0, 0}, {1, 0}, {-2, 0}, {1, -2}, {-2, 1}}
        };
            // その他のミノ用の壁キックオフセット
    private static final int[][][] WALL_KICK_NORMAL = {
    {{0, 0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}},
    {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},
    {{0, 0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}},
    {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}}
    };

    public Mino() {
        this.rand = new Random();
        this.y = 0;
        this.x = 5;
        setMinoType();
        setMinoAngle();
        // this.minoType = 1; // debug用
        // this.minoAngle = 1;// debug用
        randSet();
    }

    // 出現するミノ
    public void initMino() {
        this.y = 0;
        this.x = 5;
        setMinoType();
        setMinoAngle();
        randSet();
    }

    public int[][][] getMino() {
        return minoTypes;
    }

    public int getMinoSize() {
        return this.minoSize;
    }

    public int getMinoAngleSize() {
        return this.minoAngleSize;
    }

    public int getMinoX() {
        return this.x;
    }

    public int getMinoY() {
        return this.y;
    }

    public int[][][] getMino0() {
        return this.mino_0;
    }

    public void setMinoX(int x) {
        this.x = x;
    }

    public void setMinoY(int y) {
        this.y = y;
    }

    public void addMinoX() {
        this.x++;
    }

    public void addMinoY() {
        this.y++;
    }

    public void decMinoX() {
        this.x--;
    }

    public int getMinoAngle() {
        return this.minoAngle;
    }

    public int getMinoType() {
        return this.minoType;
    }

    public void setMinoType() {
        this.minoType = rand.nextInt(7) + 1;
    }

    public void setMinoType(int _minoType) {
        this.minoType = _minoType;
    }

    public void setMinoAngle() {
        this.minoAngle = rand.nextInt(4);
    }

    public void setMinoAngle(int minoAngle) {
        this.minoAngle = minoAngle;
    }

    public void setinitMino() {
        this.minoTypes = this.mino_0;
    }


    private void randSet() {
        switch (getMinoType()) {
            case 1:
                this.minoTypes = this.mino_I;
                break;
            case 2:
                this.minoTypes = this.mino_O;
                break;
            case 3:
                this.minoTypes = this.mino_S;
                break;
            case 4:
                this.minoTypes = this.mino_Z;
                break;
            case 5:
                this.minoTypes = this.mino_J;
                break;
            case 6:
                this.minoTypes = this.mino_L;
                break;
            case 7:
                this.minoTypes = this.mino_T;
                break;
        }
    }

    public void rotateMino(GameArea ga) {
        int newAngle = (minoAngle + 1) % minoAngleSize;
        int[][][] wallKickData = (minoType == 1) ? WALL_KICK_I : WALL_KICK_NORMAL;
        for (int[] offset : wallKickData[minoAngle]) {
            int newX = x + offset[0];
            int newY = y + offset[1];

            if (!ga.isCollison(this, newX, newY, newAngle)) {
                x = newX;
                y = newY;
                setMinoAngle(newAngle);
                return;
            }
        }
    }

}

