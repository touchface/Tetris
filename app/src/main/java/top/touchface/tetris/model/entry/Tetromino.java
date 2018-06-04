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
        int ranColor[]={0xFFFF0000,0xFFFF7F00,0xFFFFFF00,0xFF00FF00,0xFF00FFFF,0xFF0000FF,0xFF8B00FF};

        Random random=new Random();
        int type=random.nextInt(7);//生成类型
        int color=random.nextInt(ranColor.length);//生成随机颜色
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
        Tetromino tetromino=new Tetromino(cells,type);
        return tetromino;
    }
    @Override
    public Object clone(){
        int type=this.type;
        Cell cs[]=new Cell[4];
        for (int i=0;i<this.cells.length;i++){
            cs[i]=(Cell) cells[i].clone();
        }
        Tetromino tetromino=new Tetromino(cs,type);

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
