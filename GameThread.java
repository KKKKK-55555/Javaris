package app_TETRIS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    private Mino mino;
    private Mino nextMino;
    

    public GameThread() {
        this.mino = new Mino();
        this.ga = new GameArea();
        this.nextMino = new Mino();
    }

    public GameThread(Mino mino, GameArea ga, Mino nextMino) {
        this.mino = mino;
        this.ga = ga;
        this.nextMino = nextMino;
    }

    public GameThread(Mino mino, GameArea ga) {
        this.mino = mino;
        this.ga = ga;
        this.nextMino = new Mino();
    }

    //public void nextMino(Mino nextMino){ 
      //  this.mino = nextMino;
    //}

    public void run() {

        while (true) {
            ga.moveDown(mino);

            if (ga.isCollison(mino)) {
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
                this.mino = nextMino; 
                this.nextMino = new Mino(); 
                //nextMino.initMino(); 
            } else {
                ga.eraseLine();
                // ga.addScore();
                // ga.resetCount();
                ga.initField();
                ga.fieldAddMino(mino);
            }
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
