package top.touchface.tetris.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import top.touchface.tetris.model.entry.Cell;
import top.touchface.tetris.model.entry.GMap;
import top.touchface.tetris.model.entry.Tetromino;


/**
 *@author Jason
 * 形状的控制类
 * 包含当前形状和下一个形状
 * **/
public class TetrominoModel {

    //当前的形状
    private Tetromino presentTetromino;
    //下一个形状
    private Tetromino nextTetromino;
    //声明方块画笔
    private Paint tetrominoPaint;
    //声明投影画笔
    private Paint shadowPaint;
    //格子的大小
    private int cellSize;

    public TetrominoModel(int cellSize){
        //初始化方块大小
        this.cellSize=cellSize;

        //初始化画笔
        tetrominoPaint=new Paint();
        tetrominoPaint.setAntiAlias(true);
        //初始化投影画笔
        shadowPaint=new Paint();
        shadowPaint.setAntiAlias(true);
        //shadowPaint.setStyle(Paint.Style.STROKE);
        //shadowPaint.setStrokeWidth(2);
    }
    /**
     * 生成当前的游戏方块
     **/
    public void newPresentTetromino() {
        if(presentTetromino==null){
            newNextTetromino();
        }
        //当前方块等于下一块方块
        presentTetromino=nextTetromino;
        newNextTetromino();
    }
    /**
     * 生成下一个游戏方块
     * **/
    private void  newNextTetromino(){

        nextTetromino=Tetromino.newRandomInstance();
    }

    /**
     * 绘制当前游戏方块
     **/
    public void drawPresentTetromino(Canvas canvas) {
        //方块绘制
        if(presentTetromino==null){
            return;
        }
        //方块绘制
        for (Cell cell:presentTetromino.getCells()) {
            tetrominoPaint.setColor(cell.getColor());
            shadowPaint.setAlpha(150);
            canvas.drawRect(
                    (cell.getX())* cellSize,
                    (cell.getY()) * cellSize,
                    (cell.getX()) * cellSize + cellSize,
                    (cell.getY()) * cellSize + cellSize,tetrominoPaint);
        }
    }
    /**
     * 绘制下一个方块
     * **/
    public void drawNextTetromino(Canvas canvas) {
        //方块绘制
        if(nextTetromino==null){
            return;
        }
        int width=canvas.getWidth();
        int cellSize=width/5;
        //统计X轴有多少给方块
        Map<Integer,Integer> map=new HashMap<>();
        for (Cell cube:nextTetromino.getCells()) {
            map.put(cube.getX(),cube.getY());
        }
        int shapeWidth=cellSize*map.size();
        int left=(width-shapeWidth)/2;
        if(nextTetromino.getType()==0){
            left-=cellSize;
        }
        Log.e("LOG","left:"+left+",width:"+width);
        //方块绘制
        for (Cell cell:nextTetromino.getCells()) {
            tetrominoPaint.setColor(cell.getColor());
            shadowPaint.setAlpha(150);
            canvas.drawRect(
                    (cell.getX() -3)* cellSize+left,
                    (cell.getY()+1) * cellSize,
                    (cell.getX()-3) * cellSize + cellSize+left,
                    (cell.getY()+1) * cellSize + cellSize, tetrominoPaint);
        }

    }
    /**
     * 绘制当前方块的投影
     * **/
    public void drawShadow(Canvas canvas,GMap map) {
        //方块绘制
        if(map==null||presentTetromino==null){
            return;
        }
        //复制当前形状
        Tetromino shadow=(Tetromino) presentTetromino.clone();
        //获取形状中的格子
        Cell cells[]=shadow.getCells();
        while (true){
            boolean flag=false;
            //判断移动后是否出界
            for (Cell cell:cells) {
                if(checkBoundary(cell.getX(),cell.getY()+1,map)){

                    flag=true;
                    break;
                }
            }
            if(!flag) {
                //移动所有方块
                for (Cell cell :cells) {
                    //向下移动
                    cell.move(0,1);
                }
            }else{
                break;
            }
        }
        //投影绘制
        for (Cell cell:shadow.getCells()) {
            shadowPaint.setColor(cell.getColor());
            shadowPaint.setAlpha(20);
            canvas.drawRect(
                    (cell.getX())* cellSize,
                    (cell.getY()) * cellSize,
                    (cell.getX()) * cellSize + cellSize,
                    (cell.getY()) * cellSize + cellSize,shadowPaint);
        }
    }
    public boolean moveLeft(GMap map){
        return move(-1,0,map);
    }
    public boolean moveRight(GMap map){
        return move(1,0,map);
    }
    public boolean moveBottom(GMap map){
        return move(0,1,map);
    }
    /**
     * 移动
     * **/
    public boolean move(int x,int y,GMap map){

        //判断移动后是否出界
        for (Cell cell:presentTetromino.getCells()) {
            if(checkBoundary(cell.getX()+x,cell.getY()+y,map)){
                return false;
            }
        }
        //移动当前图形
        presentTetromino.move(x, y);
        return true;
    }
    /**
     * 旋转
     * **/
    public boolean rotate(GMap map){

        //如果当前的旋转方块是田字形旋转失败
        if(presentTetromino.getType()==0){
            return false;
        }
        //判断旋转是否出界
        Cell cells[]=presentTetromino.getCells();
        for (int i=0;i<cells.length;i++){
            int checkX=-cells[i].getY()+cells[0].getY()+cells[0].getX();
            int checkY= cells[i].getX()-cells[0].getX()+cells[0].getY();
            if(checkBoundary(checkX,checkY,map)){
                return false;
            }
        }
        //进行旋转
        presentTetromino.rotate();
        return true;
    }

    public Tetromino getPresentTetromino() {
        return presentTetromino;
    }

    /**
     * 判断x,y是否在边界外
     * @param x 方块的x坐标
     * @param y 方块的y坐标
     * @param map 地图对象
     * @return 在边界外返回true，在边界内返回false
     * **/
    private boolean checkBoundary(int x,int y,GMap map){

        return (x<0||y<0||x>=map.cells.length||y>=map.cells[0].length||map.cells[x][y]!=null);
    }
}
