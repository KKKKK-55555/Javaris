package Javaris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    private Mino     mino;
    private Mino     nextMino;
    

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
    }

    public Mino getMinoNow() {
        return this.mino;
    }

    public Mino getNextMino() {
        return this.nextMino;
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
                // ToDo
                if(mino.getMinoY() <= 1){
                    System.out.println("GameOver");
                    System.out.println(ga.getName() + "  あなたのスコア:" + ga.getScore());
                    System.exit(0);
                }

                ga.bufferFieldAddMino(mino);
                ga.eraseLine();
                // ga.addScore();
                // ga.resetCount();
                ga.initField();
                mino.initMino();

                this.mino     = nextMino;   // 現在のminoをnextMinoに更新する
                this.nextMino = new Mino(); // nextMinoを更新する
                //nextMino.initMino(); 
            } else {
                ga.eraseLine();
                // ga.addScore();
                // ga.resetCount();
                ga.initField();
                ga.fieldAddMino(mino);
            }
            
            // draw display
            ga.drawField();
            System.out.println("NextMino"); 
            ga.drawNextMino(nextMino); 
            // ga.drawFieldAndMino(mino);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
