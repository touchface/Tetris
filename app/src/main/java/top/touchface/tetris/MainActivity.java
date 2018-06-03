package top.touchface.tetris;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;

public class MainActivity extends Activity implements View.OnClickListener {

    //开始按钮
    Button buttonStart;
    //暂停按钮
    Button buttonPause;
    //当前分数控件
    TextView textNowScore;
    //当前玩家最高的分数
    TextView textMaxScore;
    //声明一个游戏控件
    View gameView;
    //声明下一块方块面板
    View nextView;
    //游戏控制器

    //处理别的线程发过来的信息
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
        handler = new GameViewHandler(this);
    }
    static class GameViewHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        GameViewHandler(MainActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            String s_msg=(String)msg.obj;
            switch (msg.what){
                case 0:
                    //重绘游戏组件
                    if("invalidate".equals(s_msg)) {
                        if (mActivity.get() != null) {
                            mActivity.get().invalidateGame();
                        }
                    }
                    break;
                case 1:
                    //设置buttonStart的值
                    mActivity.get().setBtnStartText(s_msg);
                    break;
                case 2:
                    //设置buttonPause的值
                    mActivity.get().setBtnPauseText(s_msg);
                    break;
            }
        }
    }

    /**
     * 初始化数据
     **/
    private void initData() {

        //初始化游戏控制

    }

    /**
     * 初始化视图
     **/
    private void initView() {
        buttonStart = findViewById(R.id.btnStart);
        buttonPause = findViewById(R.id.btnPause);
        textNowScore=findViewById(R.id.textNowScore);
        textMaxScore=findViewById(R.id.textMaxScore);

        //1.得到游戏区域的父容器
        FrameLayout layoutGame = findViewById(R.id.layoutGame);
        //2.实例化游戏区域
        gameView = new View(this) {
            //重写游戏区域的绘制
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                //绘制游戏内容

                //更新分数信息
                freshTextScore();
            }
        };
        int width=getScreenWidth();
        Config.XWIDTH=width*2/3;
        Config.YHEIGHT=Config.XWIDTH*2;
        //3.设置游戏区域的大小
        gameView.setLayoutParams(new FrameLayout.LayoutParams(Config.XWIDTH, Config.YHEIGHT));
        gameView.setBackgroundColor(0x10000000);
        //4.将游戏区域添加进入父类容器
        layoutGame.addView(gameView);

        //初始化下一块显示区域
        nextView=new View(this){
            @Override
            protected void onDraw(Canvas canvas){
                super.onDraw(canvas);

            }
        };

        //设置参数
        nextView.setBackgroundColor(0x10000000);
        nextView.setLayoutParams(new FrameLayout.LayoutParams(-1,250));
        FrameLayout layoutNext=(FrameLayout)findViewById(R.id.layoutNext);
        layoutNext.addView(nextView);


    }
    private void freshTextScore(){

    }

    /**
     * 初始化监听器
     **/
    private void initListener() {
        findViewById(R.id.btnLeft).setOnClickListener(this);
        findViewById(R.id.btnRotate).setOnClickListener(this);
        findViewById(R.id.btnRight).setOnClickListener(this);
        findViewById(R.id.btnBottom).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnPause).setOnClickListener(this);
    }

    /**
     * 重绘游戏控件
     **/
    private void invalidateGame() {
        if (gameView != null) {
            gameView.invalidate();
        }
        if(nextView!=null){
            nextView.invalidate();
        }
    }
    /**
     * 设置开始键的文本内容
     * **/
    private void setBtnStartText(String msg){
        if(buttonStart!=null){
            buttonStart.setText(msg);
        }
    }
    /**
     * 设置暂停键的文本内容
     * **/
    private void setBtnPauseText(String msg){
        if(buttonPause!=null){
            buttonPause.setText(msg);
        }
    }
    /**
     * 用于别的线程向主线程发送消息
     **/
    public void sendMessageToGVH(Message msg) {
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }
    /**
     * 捕捉点击事件
     **/
    @Override
    public void onClick(View v) {

        //更新数据后重绘视图
        gameView.invalidate();
    }
    private int getScreenWidth(){
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


}
