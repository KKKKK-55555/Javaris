package Javaris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    private Mino     mino;
    private Mino     nextMino;
    private Mino     holdMino;
    private boolean  isHold = false;
    

    public GameThread() {
        this.mino     = new Mino();
        this.ga       = new GameArea();
        this.nextMino = new Mino();
    }

    public GameThread(Mino mino, GameArea ga, Mino nextMino) {
        this.mino     = mino;
        this.ga       = ga;
        this.nextMino = nextMino;
    }

    public GameThread(Mino mino, GameArea ga) {
        this.mino     = mino;
        this.ga       = ga;
        this.nextMino = new Mino();
        this.holdMino = new Mino();
        this.holdMino.setinitMino();
    }

    public Mino getMinoNow() {
        return this.mino;
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

    //public void nextMino(Mino nextMino){ 
      //  this.mino = nextMino;
    //}

    @Override
    public void run() {

        while (true) { // ゲーム処理本体

            ga.moveDown(mino);

            // update mino, nextMino and bufferField
            if (ga.isCollison(mino)) {
                // ゲームオーバー判定
                if(mino.getMinoY() <= 1){
                    System.out.println("GameOver");
                    System.out.println(ga.getName() + "  あなたのスコア:" + ga.getScore());
                    System.exit(0);
                }

                ga.bufferFieldAddMino(mino);
                ga.eraseLine();
                ga.initField();
                mino.initMino();

                this.mino     = nextMino;   // 現在のminoをnextMinoに更新する
                this.nextMino = new Mino(); // nextMinoを更新する
            } else {
                ga.eraseLine();
                ga.initField();
                ga.fieldAddMino(mino);
                ga.fieldAddGhost(mino);
            }
            
            // draw display
            ga.drawField();
            System.out.println("NextMino  HoldMino");
            ga.drawNextMino(nextMino, holdMino);
            // ga.drawFieldAndMino(mino);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
