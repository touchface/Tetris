package top.touchface.tetris.model.entry;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Jason
 * 俄罗斯方块的基本形状类
 * **/
public class Tetromino implements Cloneable{
    //由4个方块构成的基本形状
    private Cell cells[];
    //当前形状的类型
    private int type;
    public Tetromino(Cell cells[],int type){
        this.cells=cells;
        this.type=type;
    }

    public Cell[] getCells() {
        return cells;
    }

    public int getType() {
        return type;
    }

    /**
     *移动当前的形状
     * **/
    public void move(int x,int y){
        for (Cell cell:cells){
            cell.move(x,y);
        }
    }
    /**
     *对当前形状进行旋转
     * **/
    public void rotate(){
        for (int i=0;i<cells.length;i++){
            int checkX=-cells[i].getY()+cells[0].getY()+cells[0].getX();
            int checkY= cells[i].getX()-cells[0].getX()+cells[0].getY();
            cells[i].setX(checkX);
            cells[i].setY(checkY);
        }
    }
    public static Tetromino newRandomInstance(){
        //七种颜色值
        int ranColor[]={0xffff0000,0xffff7f00,0xffffff00,0xff00ff00,0xff00ffff,0xff0000ff,0xff8b00ff};

        Random random=new Random();
        Tetromino tetromino;
        int type=random.nextInt(7);//生成类型
        int color=ranColor[random.nextInt(ranColor.length)];//生成随机颜色
        Cell cells[]=null;
        switch (type) {
            case 0:
                //田
                cells = new Cell[]{
                        new Cell(4, 0,color),
                        new Cell(5, 0,color),
                        new Cell(4, 1,color),
                        new Cell(5, 1,color)};
                break;
            case 1:
                //L
                cells = new Cell[]{
                        new Cell(4, 1,color),
                        new Cell(3, 0,color),
                        new Cell(3, 1,color),
                        new Cell(5, 1,color)};
                break;
            case 2:
                //反L
                cells = new Cell[]{
                        new Cell(4, 1,color),
                        new Cell(5, 0,color),
                        new Cell(3, 1,color),
                        new Cell(5, 1,color)};
                break;
            case 3:
                //——
                cells = new Cell[]{
                        new Cell(5, 0,color),
                        new Cell(3, 0,color),
                        new Cell(4, 0,color),
                        new Cell(6, 0,color)};
                break;
            case 4:
                //Z
                cells = new Cell[]{
                        new Cell(4, 1,color),
                        new Cell(3, 0,color),
                        new Cell(4, 0,color),
                        new Cell(5, 1,color)};
                break;
            case 5:
                //反Z
                cells = new Cell[]{
                        new Cell(4, 1,color),
                        new Cell(3, 1,color),
                        new Cell(4, 0,color),
                        new Cell(5, 0,color)};
                break;
            case 6:
                //凸
                cells = new Cell[]{
                        new Cell(4, 1,color),
                        new Cell(3, 1,color),
                        new Cell(4, 0,color),
                        new Cell(5, 1,color)};
                break;
        }
        tetromino=new Tetromino(cells,type);
        return tetromino;
    }
    @Override
    public Object clone(){
        Tetromino tetromino=null;
        try {
            super.clone();
            int type=this.type;
            Cell cs[]=new Cell[4];
            for (int i=0;i<this.cells.length;i++){
                cs[i]=(Cell) cells[i].clone();
            }
            tetromino=new Tetromino(cs,type);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  tetromino;
    }

    @Override
    public String toString() {
        return "Tetromino{" +
                "cells=" + Arrays.toString(cells) +
                ", type=" + type +
                '}';
    }
}
