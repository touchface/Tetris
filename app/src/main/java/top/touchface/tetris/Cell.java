package top.touchface.tetris;
/**
 * @author Jason
 * 格子类，用于构成俄罗斯方块的基本形状
 * **/
public class Cell implements Cloneable{

    private int x;
    private int y;
    private int color;
    public Cell(){
        x=0;
        y=0;
        color=0;
    }
    /**
     *格子的构造方法，用于初始化格子对象
     *@param x 格子的横坐标
     *@param y 格子的纵坐标
     * **/
    public Cell(int x, int y,int color) {
        this();
        this.x = x;
        this.y = y;
        this.color=color;
    }
    public int getColor(){return color;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(int color){
        this.color=color;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(int x, int y){
        this.x+=x;
        this.y+=y;
    }
    @Override
    public Object clone(){
        Cell cell=null;
        try {
            cell=(Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  cell;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
