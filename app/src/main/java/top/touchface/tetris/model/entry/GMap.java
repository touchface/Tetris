package top.touchface.tetris.model.entry;

import top.touchface.tetris.model.entry.Cell;

/**
 * @author Jason
 * 游戏地图类，用于保存堆积的方块
 * **/
public class GMap {
    public Cell cells[][];
    public GMap(int map_x, int map_y){
        cells=new Cell[map_x][map_y];
    }
    /**
     * 清空游戏地图类
     * **/
    public void cleanGmap(){
        if(cells==null){
            return;
        }
        for (int x=0;x<cells.length;x++){
            for (int y=0;y<cells.length;y++){
                cells[x][y]=null;
            }
        }
    }
}
