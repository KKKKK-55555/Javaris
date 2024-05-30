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

        // スレッドを呼び出す
        this.gt = new GameThread(mino, ga);
        gt.start();
        
        // コントローラーを呼び出す
        initControls();
    }


    // mainメソッド 1番最初に動く特別なメソッド
    public static void main(String[] args) throws Exception {
        // Start Screen
        System.out.println("         ______\n        /     /\n        |   _/\n        |  |\n        |  |\n        | @|\n  /|    |  |\n / |    |  |\n/  |    |  |avaris\n\\  \\    /  |\n \\  \\__/  /\n  \\______/");
        System.out.println("---------------------------");
        System.out.println("Javarisを立ち上げています\n");
        System.out.print("名前を入力してください:");


        // Scan player's name
        Scanner sc = new Scanner(System.in, "utf-8");
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

        System.out.println();
        System.out.println("<操作方法>");
        System.out.println("-----------------\nキー：操作\n-----------------\n→ 　：右移動\n← 　：左移動\n↓ 　：下移動\n↑ 　：回転\nS 　：スキップ\nH 　：ホールド\nG 　：ハードドロップ\n-----------------\nスタートしたらJavaコントローラーを立ち上げてください！\n");

        
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
                    if (ga.isMinoCollisionJavali(gt.getMinoNow())) {
                        gt.actJavali();
                    }
                    ga.drawFieldAndMino(gt.getMinoNow(), gt.getNextMino(), gt.getHoldMino(), gt.getJavaliNow(), gt);
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
                    if (ga.isMinoCollisionJavali(gt.getMinoNow())) {
                        gt.actJavali();
                    }
                    ga.drawFieldAndMino(gt.getMinoNow(), gt.getNextMino(), gt.getHoldMino(), gt.getJavaliNow(), gt);
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
                    if (ga.isMinoCollisionJavali(gt.getMinoNow())) {
                        gt.actJavali();
                    }
                    ga.drawFieldAndMino(gt.getMinoNow(), gt.getNextMino(), gt.getHoldMino(), gt.getJavaliNow(), gt);
                }
            }
        });

        // Rotaion Key Control
        im.put(KeyStroke.getKeyStroke("UP"),
               "up");
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ga.rotation(gt.getMinoNow(), gt.getMinoNow(), gt.getHoldMino());
                if (ga.isMinoCollisionJavali(gt.getMinoNow())) {
                    gt.actJavali();
                }
                ga.drawFieldAndMino(gt.getMinoNow(), gt.getNextMino(), gt.getHoldMino(), gt.getJavaliNow(), gt);
            }
        });
        // skip 
        im.put(KeyStroke.getKeyStroke("S"),
               "s");
        am.put("s", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gt.updateNextMino();
            }
        });
        // hold
        im.put(KeyStroke.getKeyStroke("H"),
               "h");
        am.put("h", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(gt.isHold()){
                    gt.changeHoldMino();
                } else {
                    gt.initHoldMino();
                }
            }
        });

        // Hard Drop
        im.put(KeyStroke.getKeyStroke("G"),
                "g");
        am.put("g", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gt.getMinoNow().setMinoY(gt.getMinoNow().getMinoY() + ga.getHardBlockCount(gt.getMinoNow()) - 1);
                ga.drawFieldAndMino(gt.getMinoNow(), gt.getNextMino(), gt.getHoldMino(), gt.getJavaliNow(), gt);
            }
        });
    }

}
