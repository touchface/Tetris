package top.touchface.tetris;

import android.graphics.Canvas;
import android.graphics.Paint;

import top.touchface.tetris.model.entry.GMap;
/**
 *@author Jason
 * 游戏地图的控制类
 *
 * **/
public class GMapControl {
    public GMap map;
    //方块大小
    private int xWidth,yHeight,cellSize;
    //声明地图画笔
    private Paint mapPaint;
    //声明辅助线画笔
    private Paint linePaint;
    public GMapControl(int map_x,int map_y,int xWidth,int yHeight,int cellSize){
        this.xWidth=xWidth;
        this.yHeight=yHeight;
        this.cellSize=cellSize;
        //初始化地图
        map=new GMap(map_x,map_y);
        //初始辅助线化画笔
        linePaint = new Paint();
        linePaint.setColor(0x10000000);
        linePaint.setAntiAlias(true);
        //初始化地图画笔
        mapPaint = new Paint();
        mapPaint.setColor(0xcccccccc);
        mapPaint.setAntiAlias(true);
    }
    /**
     * 绘制地图
     * **/
    public void drawMap(Canvas canvas){
        //绘制地图
        for (int x = 0; x < map.cells.length; x++) {
            for (int y = 0; y < map.cells[0].length; y++) {
                if (map.cells[x][y]!=null) {
                    mapPaint.setColor(map.cells[x][y].getColor());
                    canvas.drawRect(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize + cellSize,
                            y * cellSize + cellSize, mapPaint);
                }
            }
        }
    }
    /**
     * 绘制辅助线
     * **/
    public void drawLines(Canvas canvas){

        for (int x = 0; x <map.cells.length; x++) {
            canvas.drawLine(x * cellSize, 0, x * cellSize, yHeight, linePaint);
        }
        for (int y = 0; y < map.cells[0].length; y++) {
            canvas.drawLine(0, y * cellSize, xWidth, y * cellSize, linePaint);
        }
    }

    /**清空填充满的行**/
    public int cleanLine () {
        int count=0;
        for (int y = map.cells[0].length - 1; y >= 0; y--) {
            int num=0;
            for (int x = 0; x < map.cells.length; x++) {
                if (map.cells[x][y]!=null) {
                    num++;
                }
            }
            if (num == map.cells.length) {
                deleteLine(y);
                y = map.cells[0].length;
                count++;

            } else if (num == 0) {
                break;
            }
        }
        return count;
    }
    /**
     * 执行消行
     * **/
    private void deleteLine ( int dy){
        for (int y = dy; y > 0; y--) {
            for (int x = 0; x < map.cells.length; x++) {
                map.cells[x][y] = map.cells[x][y - 1];
            }
        }
    }
    /**
     *清空地图
     * **/
    public void cleanMaps(){
        map.cleanGmap();
    }

}
