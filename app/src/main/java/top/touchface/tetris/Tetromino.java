package top.touchface.tetris;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Jason
 * 俄罗斯方块的基本形状类
 * **/
public class Tetromino implements Cloneable{
    //由4个方块构成的基本形状
    private Cell cells[];
    //当前形状的颜色
    private int color;
    //当前形状的类型
    private int type;
    public Tetromino(Cell cells[],int color,int type){
        this.cells=cells;
        this.color=color;
        this.type=type;
    }

    public Cell[] getCells() {
        return cells;
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
                        new Cell(4, 0),
                        new Cell(5, 0),
                        new Cell(4, 1),
                        new Cell(5, 1)};
                break;
            case 1:
                //L
                cells = new Cell[]{
                        new Cell(4, 1),
                        new Cell(3, 0),
                        new Cell(3, 1),
                        new Cell(5, 1)};
                break;
            case 2:
                //反L
                cells = new Cell[]{
                        new Cell(4, 1),
                        new Cell(5, 0),
                        new Cell(3, 1),
                        new Cell(5, 1)};
                break;
            case 3:
                //——
                cells = new Cell[]{
                        new Cell(5, 0),
                        new Cell(3, 0),
                        new Cell(4, 0),
                        new Cell(6, 0)};
                break;
            case 4:
                //Z
                cells = new Cell[]{
                        new Cell(4, 1),
                        new Cell(3, 0),
                        new Cell(4, 0),
                        new Cell(5, 1)};
                break;
            case 5:
                //反Z
                cells = new Cell[]{
                        new Cell(4, 1),
                        new Cell(3, 1),
                        new Cell(4, 0),
                        new Cell(5, 0)};
                break;
            case 6:
                //凸
                cells = new Cell[]{
                        new Cell(4, 1),
                        new Cell(3, 1),
                        new Cell(4, 0),
                        new Cell(5, 1)};
                break;
        }
        Tetromino tetromino=new Tetromino(cells,color,type);
        return tetromino;
    }
    @Override
    public Object clone(){
        int type=this.type;
        int color=this.color;
        Cell cs[]=new Cell[4];
        for (int i=0;i<this.cells.length;i++){
            cs[i]=(Cell) cells[i].clone();
        }
        Tetromino tetromino=new Tetromino(cs,color,type);

        return  tetromino;
    }

    @Override
    public String toString() {
        return "Tetromino{" +
                "cells=" + Arrays.toString(cells) +
                ", color=" + color +
                ", type=" + type +
                '}';
    }
}
