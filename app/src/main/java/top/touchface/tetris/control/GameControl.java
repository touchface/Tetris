package top.touchface.tetris.control;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import top.touchface.tetris.Config;
import top.touchface.tetris.MainActivity;
import top.touchface.tetris.R;
import top.touchface.tetris.model.GMapModel;
import top.touchface.tetris.model.ScoreModel;
import top.touchface.tetris.model.TetrominoModel;
import top.touchface.tetris.model.entry.Cell;
public class GameControl {

    //状态画笔
    private Paint statePaint;

    //声明地图模型
    private GMapModel mapModel;
    //声明方块模型
    private TetrominoModel tetrominoModel;
    //声明分数模型
    private ScoreModel scoreModel;
    //主控件
    private MainActivity mActivity;
    //自动下落线程
    private Thread downThread;
    //暂停状态
    private boolean isPause=true;
    //游戏状态
    private boolean isOver=true;

    public  GameControl(MainActivity mActivity){
        this.mActivity=mActivity;
        //初始化状态画笔
        statePaint = new Paint();
        statePaint.setColor(0xff000000);
        statePaint.setAntiAlias(true);
        statePaint.setTextSize(100);

    }
    /**
     * 初始化数据
     * **/
    public void initData(){

        int cellSize= Config.XWIDTH/ Config.MAP_X;
        //初始化地图模型
        mapModel=new GMapModel(Config.MAP_X, Config.MAP_Y, Config.XWIDTH, Config.YHEIGHT,cellSize);
        //初始化方块模型
        tetrominoModel =new TetrominoModel(cellSize);
        //初始化分数模型
        scoreModel=new ScoreModel(mActivity);

    }
    public void draw(Canvas canvas){
        //绘制地图
        mapModel.drawMap(canvas);
        //绘制地图辅助线
        mapModel.drawLines(canvas);
        //绘制方块
        tetrominoModel.drawPresentTetromino(canvas);

        tetrominoModel.drawShadow(canvas,mapModel.map);
        //绘制游戏状态
        //创建发送的消息

        Message msg=new Message();

        if (isPause && !isOver) {

            canvas.drawText(mActivity.getString(R.string._paused), Config.XWIDTH / 2 - statePaint.measureText("Paused") / 2, Config.YHEIGHT / 2, statePaint);
        }
        if (isOver && !isPause) {
            canvas.drawText(mActivity.getString(R.string._game_over), Config.XWIDTH / 2 - statePaint.measureText("GameOver") / 2, Config.YHEIGHT / 2, statePaint);
        }
        if(!isOver){
            msg.what=1;
            msg.obj=mActivity.getString(R.string._restart);
        }else{
            msg.what=1;
            msg.obj=mActivity.getString(R.string._start);
        }
        mActivity.sendMessageToGVH(msg);
        msg=new Message();
        if(!isPause){
            msg.what=2;
            msg.obj=mActivity.getString(R.string._pause);
        }else{
            msg.what=2;
            msg.obj=mActivity.getString(R.string._continue);
        }
        mActivity.sendMessageToGVH(msg);

    }

    /**绘制预览方块**/
    public void drawNext(Canvas canvas){
        tetrominoModel.drawNextTetromino(canvas);
    }
    /**
     * 开始游戏
     * **/
    private void startGame () {
        //清空地图
        mapModel.cleanMaps();
        //初始化分数
        scoreModel.initScore();
        //判断线程是否为空
        if (downThread == null) {

            downThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            break;
                        }
                        //判断游戏是否结束
                        if (isOver) {
                            continue;
                        }
                        //判断游戏是否暂停
                        if (isPause) {
                            continue;
                        }
                        //执行下落
                        moveBottom();
                        //通知主线程刷新
                        Message msg=new Message();
                        msg.what=0;
                        //重绘游戏界面
                        msg.obj="invalidate";
                        mActivity.sendMessageToGVH(msg);
                    }
                }
            };
            downThread.start();
        }
        //生成新的方块
        tetrominoModel.newPresentTetromino();
        isPause = false;
        isOver = false;
    }
    /**
     * 暂停游戏
     * **/
    private void setPause () {
        if (!isOver) {
            isPause = !isPause;
        }
    }
    /**
     * 处理点击事件
     * **/
    public void onClick(int id){

        switch (id) {

            case R.id.btnLeft:
                //左
                if(isPause||isOver){
                    return;
                }
                tetrominoModel.moveLeft(mapModel.map);
                break;
            case R.id.btnRotate:
                //旋转
                if(isPause||isOver){
                    return;
                }
                tetrominoModel.rotate(mapModel.map);
                break;
            case R.id.btnRight:
                //右
                if(isPause||isOver){
                    return;
                }
                tetrominoModel.moveRight(mapModel.map);
                break;
            case R.id.btnBottom:
                //下(快速下落)
                moveBottom();
                while (true) {
                    if (!moveBottom()) {
                        break;
                    }
                }
                break;
            case R.id.btnStart:
                //开始
                startGame();
                break;
            case R.id.btnPause:
                //暂停
                setPause();
                break;
        }
    }

    /**
     * 结束判断
     * **/
    private boolean checkOver () {

        Cell cells[]=tetrominoModel.getPresentTetromino().getCells();
        for (Cell cell:cells){
            if(mapModel.map.cells[cell.getX()][cell.getY()]!=null){
                return true;
            }
        }
        return false;
    }
    /**
     * 下落
     * * **/
    private boolean moveBottom () {
        if (isPause || isOver ) {
            return false;
        }
        //1.执行移动
        if (tetrominoModel.moveBottom(mapModel.map)) {
            return true;
        }
        Cell cells[]=tetrominoModel.getPresentTetromino().getCells();
        //2.堆积处理
        for (Cell cell:cells){
            mapModel.map.cells[cell.getX()][cell.getY()]=cell;
        }
        scoreModel.addScore(mapModel.cleanLine());//消行处理
        Message message=new Message();
        message.what=3;
        mActivity.sendMessageToGVH(message);
        //3.生成新形状
        tetrominoModel.newPresentTetromino();
        //4.检测游戏是否结束
        isOver = checkOver();
        if(isOver){
            scoreModel.saveScore("LOCAL");
        }
        return false;
    }
    public int getScore(){
        return scoreModel.getScore();
    }
    public int getMaxScore(){
        return scoreModel.getMaxScore();
    }

}