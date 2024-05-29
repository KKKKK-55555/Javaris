package Javaris;

public class GameArea { //15結合済み
    private int fieldHeight = 21; // ミノ操作可能なフィールドの高さ
    private int fieldWidth  = 12; // ミノ操作可能なフィールドの幅
    private int grandHeight = 30; // 書き込み用フィールドの高さ 広めに確保
    private int grandWidth  = 20; // 書き込み用フィールドの高さ 広めに確保
    private int[][] field;        // 描画用フィールド
    private int[][] bufferField;  // 書き込み用フィールド

    // parameter for display
    private int score     = 0;
    private int linecount = 0;
    private int linetotal = 0;

    // private Mino mino;
    private String name;

    // field offsets
    private int heightOverOffset  = 5;
    private int heightUnderOffset = 4;
    private int widthOffset       = 4;
    //private int fieldStartX       = widthOffset;
    //private int fieldStartY       = heightOverOffset;

    public GameArea() {
        // this.mino = mino;
        this.field       = new int[grandHeight][grandWidth];
        this.bufferField = new int[grandHeight][grandWidth];
        initBufferField();
        initField();
    }

    public int getScore(){ 
        return this.score;
    }

    public int getcount(){ 
        return this.linecount;
    }

    public int resetCount(){ 
        this.linecount = 0;
        return this.linecount;
    }

    public int getFieldHeight() {
        return this.fieldHeight;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }

    public int getGrandHeight() {
        return this.grandHeight;
    }

    public int getGrandWidth() {
        return this.grandWidth;
    }

    public int[][] getBufferField() {
        return this.bufferField;
    }

    public int[][] getField() {
        return this.field;
    }

    // ?
    public GameArea(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
    


    // 描画用Field初期化
    public void initField() {
        for (int y = 0; y < getGrandHeight(); y++) {
            for (int x = 0; x < getGrandWidth(); x++) {
                field[y][x] = bufferField[y][x];
            }
        }
    }

    // 固定ブロック用BufferField初期化
    public void initBufferField() {
        // 1を敷き詰める
        for (int y = 0; y < getGrandHeight(); y++) {
            for (int x = 0; x < getGrandWidth(); x++) {
                bufferField[y][x] = 1;
            }
        }

        // フィールドに0を敷き詰める
        for (int y = 0; y < getFieldHeight()+ heightOverOffset - 1; y++) {
            for (int x = widthOffset+1; x < getGrandWidth()-widthOffset-1; x++) {
                bufferField[y][x] = 0;
            }
        }
        /*
        // 壁を作る
        for (int y = 0; y < getFieldHeight(); y++) {
            bufferField[y][0] = bufferField[y][getFieldWidth() - 1] = 1;
        }
        // 床を作る
        for (int x = 0; x < getFieldWidth(); x++) {
            bufferField[getFieldHeight() - 1][x] = 1;
        }
        */
    }

    // 描画メソッド
    public void drawField() {
        for (int y = heightOverOffset; y < getGrandHeight()-heightUnderOffset; y++) {
            for (int x = widthOffset; x < getGrandWidth()-widthOffset; x++) {
                System.out.printf("%s", (field[y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
        
        System.out.println("消したライン数：" + linetotal); 
        System.out.print("名前:" + name +"   ");
        System.out.println("スコア：" + score);
    }

    //fieldの下にnextMinoを出力
    public void drawNextMino(Mino nextMino, Mino holdMino) {

        int[][][] m = nextMino.getMino();
        int[][][] h = holdMino.getMino();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.printf("%s", (m[0][y][x] == 1 ? "回" : "・"));
            }
            // holdMinoを表示
            for (int x = 0; x < 4; x++) {
                System.out.printf("%s", (h[holdMino.getMinoAngle()][y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
    }


    // コントローラー用再描画メソッド
    public void drawFieldAndMino(Mino mino, Mino nextMino) {
        if (isCollison(mino)) {
            bufferFieldAddMino(mino);
            // eraseLine();
            // addScore(); //操作したタイミングでしか機能しない
            // resetCount();
            initField();
            mino.initMino();
        } else {
            // eraseLine();
            initField();
            fieldAddMino(mino);
            // addScore();
            // resetCount();
        }
        drawField();
        System.out.println();
       // resetCount();   //　点数が加算され続ける
    }

    public void fieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.field[heightOverOffset + mino.getMinoY() + y][widthOffset + mino.getMinoX() + x] |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    public void bufferFieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.bufferField[heightOverOffset + mino.getMinoY() + y][widthOffset + mino.getMinoX() + x]
                    |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    // 当たり判定 自動落下用
    public boolean isCollison(Mino mino) {
        for (int r = 0; r < mino.getMinoSize(); r++) {
            for (int c = 0; c < mino.getMinoSize(); c++) {
                // 1カラム下の行を確認して1があるか確認
                if (this.bufferField[heightOverOffset + mino.getMinoY() + r + 1][widthOffset + mino.getMinoX() + c] == 1
                    && mino.getMino()[mino.getMinoAngle()][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // 当たり判定 コントローラー用
    public boolean isCollison(Mino mino, int _x, int _y, int _angle) {
        for (int r = 0; r < mino.getMinoSize(); r++) {     // r means ROW
            for (int c = 0; c < mino.getMinoSize(); c++) { // c means COLUMN
                if (getBufferField()[heightOverOffset + _y + r][widthOffset + _x + c] == 1
                    && mino.getMino()[_angle][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // ライン削除処理
    public void eraseLine() {
        boolean isFill = true;
        resetCount();

        for (int y = getFieldHeight() + 3; y > 0; y--) {
            for (int x = 5; x < getFieldWidth() +3 ; x++) {
                if (bufferField[y][x] == 0) {
                    isFill = false;
                }
            }
            if (isFill) {
                for (int _y = y - 1; _y > 0; _y--) {
                    for (int x = 4; x < getFieldWidth()+4; x++) {
                        bufferField[_y + 1][x] = bufferField[_y][x];
                    } // for end
                }
                this.linecount++; 
                this.linetotal++;

            } // if end
            isFill = true;
            // addScore(); //1行ごとに処理された
            // resetCount(); // 0のまま更新されない
        } // for end
        addScore();  
        // resetCount();

    }

    public void addScore(){ //スコア計算を行う
        
        int count = getcount(); //消えたライン数を数える
        int intMax = 21_4748_3647;
        // if(score == intMax){
        //     System.out.println("max_score");
        // }else{
            switch (count) {//ライン数によって加算されるスコアの場合分け
                case 1:
                    if(score - 40 > intMax - 40){
                        score = intMax;
                    }else{
                        score += 40;
                    }
                    break;
                case 2:
                    if(score - 100 > intMax - 100){
                        score = intMax;
                    }else{
                        score += 100;
                    }
                    break;
                case 3:
                    if(score - 300 > intMax - 300){
                        score = intMax;
                    }else{
                        score += 300;
                    }
                    break;
                case 4:
                    if(score - 1200 > intMax - 1200){
                        score = intMax;
                    }else{
                        score += 1200;
                    }
                    break;
                default:
                    score += 0;
                    break;
            }
    
        }
        
    // }

    // コントローラー呼び出しメソッド← ↓ → 回転 ↑
    public void moveDown(Mino mino) {
        mino.addMinoY();
    }

    public void moveRight(Mino mino) {
        mino.addMinoX();
    }

    public void moveLeft(Mino mino) {
        mino.decMinoX();
    }

    public void rotation(Mino mino) {
        mino.setMinoAngle((mino.getMinoAngle() + 1) % mino.getMinoAngleSize());
    }

}
