package Javaris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    private Mino     mino;
    private Mino     nextMino;
    private Mino     holdMino;
    private boolean  isHold = false;
    private Javali   javaliNow;
    

    public GameThread() {
        this.mino     = new Mino();
        this.ga       = new GameArea();
        this.nextMino = new Mino();
    }

    public GameThread(Mino mino, GameArea ga) {
        this.mino     = mino;
        this.ga       = ga;
        this.nextMino = new Mino();
        this.holdMino = new Mino();
        this.holdMino.setinitMino();
        this.javaliNow = new Javali();
    }

    public void setMinoNow(Mino _mino) {
        this.mino = _mino;
    }

    public void setNextMino(Mino _nextMino) {
        this.nextMino = _nextMino;
    }

    public Mino getMinoNow() {
        return this.mino;
    }

    public Mino getNextMino() {
        return this.nextMino;
    }

    public void updateNextMino() {
        this.mino = this.nextMino;
        this.nextMino = new Mino();
    }

    // 初期holdMino　
    public void initHoldMino() {
        this.holdMino = this.mino;
        this.mino     = this.nextMino;
        this.nextMino = new Mino();
        this.isHold   = true;
        this.holdMino.setMinoX(5);
        this.holdMino.setMinoY(0);
    }

    // holdMino 今のミノをholdMinoにしてholdMinoを今のミノに切り替える
    public void changeHoldMino(){
        Mino minoNow  = this.mino;
        this.mino     = this.holdMino;
        this.holdMino = minoNow;
        this.holdMino.setMinoX(5);
        this.holdMino.setMinoY(0); 
    }

    // isHold holdしているかどうか
    public boolean isHold(){
        return this.isHold;
    }

    public Mino getHoldMino() {
        return this.holdMino;
    }

    public Javali getJavaliNow() {
        return this.javaliNow;
    }

    public void actJavali() {
        if (this.mino.getMinoType() == 1) {
            ga.javaliScore();
            ga.initBufferField();
            this.javaliNow.randSet();
        } else {
            ga.decScore();
        }

        ga.eraseLine();
        ga.initField();
        mino.initMino();

        updateNextMino();
    }

    @Override
    public void run() {

        while (true) { // ゲーム処理本体

            ga.moveDown(mino);

            if (ga.isCollison(mino) && !ga.isMinoCollisionJavali(holdMino)) {
                // ゲームオーバー判定
                if(mino.getMinoY() <= 1) {
                    System.out.println("GameOver");
                    System.out.println(ga.getName() + "  あなたのスコア:" + ga.getScore());
                    System.exit(0);
                }

                ga.eraseLine();
                ga.initField();
                ga.fieldAddJavali(javaliNow);
                mino.initMino();
                updateNextMino();
            } else {
                if (ga.isMinoCollisionJavali(mino)) {
                    actJavali();
                }
                ga.eraseLine();
                ga.initField();
                ga.fieldAddMino(mino);
                ga.fieldAddGhost(mino);
                ga.fieldAddJavali(javaliNow);
            }
            
            // draw display
            ga.drawField();
            System.out.println("NextMino  HoldMino");
            ga.drawNextMino(nextMino, holdMino);
            System.out.println();
            ga.drawFieldAndMino(mino, nextMino, holdMino, javaliNow);
            
            try {
                Thread.sleep(1100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
