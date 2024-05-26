package Javaris;

import javax.swing.JFrame;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.Scanner;


public class App extends JFrame {

    // オブジェクトをAppクラスで利用できるようにフィールド（メンバ変数）を準備
    GameArea   ga;
    Mino       mino;
    Mino       nextMino;
    GameThread gt;

    public App(String name) {
        // オブジェクトをインスタンス化してAppクラスフィールド（メンバ変数）へ追加
        this.mino     = new Mino();
        this.ga       = new GameArea();
        this.nextMino = new Mino();

        this.ga.setName(name);

        // スレッド呼び出し
        this.gt = new GameThread(mino, ga);
        gt.start();
        
        // コントローラーの呼び出し
        initControls();

    }


    // mainメソッド 1番最初に動く特別なメソッド
    public static void main(String[] args) throws Exception {
        // Start Screen
        System.out.println("Tetris");
        System.out.print("名前を入力してください:");

        // Scan player's name
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        
        
        // 名前入力においてのルール
        int l = name.length();

        if(0 < l && l <= 16) {
            System.out.println("ようこそ" + name + "さん！"); 
            GameArea player = new GameArea();
            player.setName(name);
        } else {
            System.out.println("ゲスト");
            GameArea player = new GameArea();
            player.setName("ゲスト");
        }
        
        System.out.println("EnterKeyを押してスタート！！");
        while ((System.in.read()) != '\n') ;
    
        // ?
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App(name).setVisible(true);
            }
        });
        
        sc.close();

        //ゲームオーバーの定義
        //固定されてるミノ（buffer）と出現したミノが重なる
        //「重なる」ことを示すコード
        //固定ミノ＝initmino

        //なぜ重なるのか？→
        // 上限として設定されているfieldAreaを超えないように置きたいから
        // →新しく出てきたミノが固定化される前に上限をbufferが超えていたらゲームオーバー

        //GameThread gt = new GameThread();
        //GameThread.mino.initMino();
        //GameThread.ga.bufferFieldAddMino();

        //if(ga.bufferFieldAddMino() > Mino.initMino()) { //ゲームオーバーの定義
            //System.out.println("Game Over");
            //System.out.println(name + "　あなたのスコア：" + "100");//A班からもらう変数
            
        //}


    }


    private void initControls() {

        // Initialize maps
        InputMap  im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        // RIGHT Key Control
        im.put(KeyStroke.getKeyStroke("RIGHT"),
               "right");
        
        am.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (ga.isCollison(
                    gt.getMinoNow(),                // mino
                    gt.getMinoNow().getMinoX() + 1, // minoX where the mino moves right
                    gt.getMinoNow().getMinoY(),     // minoY
                    gt.getMinoNow().getMinoAngle()  // minoAngle
                    ) == false
                ) {
                    ga.moveRight(gt.getMinoNow());
                    //ga.drawFieldAndMino(gt.getMinoNow(), gt.getMinoNow());
                }
            }
        });

        // LEFT Key Control
        im.put(KeyStroke.getKeyStroke("LEFT"),
               "left");
        
        am.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(
                    gt.getMinoNow(),
                    gt.getMinoNow().getMinoX() - 1,
                    gt.getMinoNow().getMinoY(),
                    gt.getMinoNow().getMinoAngle()
                    )
                ) {
                    ga.moveLeft(gt.getMinoNow());
                    //ga.drawFieldAndMino(gt.getMinoNow(), gt.getMinoNow());
                }
            }
        });

        // DOWN Key Control

        im.put(KeyStroke.getKeyStroke("DOWN"),
               "down");
        
        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(gt.getMinoNow(),
                    gt.getMinoNow().getMinoX(),
                    gt.getMinoNow().getMinoY() + 1,
                    gt.getMinoNow().getMinoAngle()
                    )
                ) {
                    ga.moveDown(gt.getMinoNow());
                    //ga.drawFieldAndMino(gt.getMinoNow(), gt.getMinoNow());
                }
            }
        });

        // Rotaion Key Control
        im.put(KeyStroke.getKeyStroke("UP"),
               "up");
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(gt.getMinoNow(),
                    gt.getMinoNow().getMinoX(),
                    gt.getMinoNow().getMinoY(),
                    (gt.getMinoNow().getMinoAngle() + 1) % gt.getMinoNow().getMinoAngleSize()
                    )
                ) {
                    ga.rotation(gt.getMinoNow());
                    //ga.drawFieldAndMino(gt.getMinoNow(), gt.getMinoNow());
                }
            }
        });
    }

}
