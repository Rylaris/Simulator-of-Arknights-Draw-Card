import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class Main extends Application
{
    double probabilityOfSixStar = 0.02;
    double probabilityOfFiveStar = 0.08;
    double probabilityOfFourStar = 0.5;
    int withoutSixStarTimes = 0;
    int drawTimes = 0;
    int sixStarTimes = 0;
    boolean withoutSixOrFiveStarAtFirst10Times = true;

    double probabilityOfStar;       //星级概率
    int indexOfName;          //具体干员位置
    String[] Six = {"斯卡蒂","能天使","推进之王","伊芙利特","艾雅法拉","安洁莉娜","闪灵","夜莺","星熊","塞雷娅","银灰"};
    String[] Five = {"夜魔","白面鸮","凛冬","德克萨斯","芙兰卡","拉普兰德","幽灵鲨","蓝毒","白金","陨星","天火","梅尔",
            "赫墨","华法琳","临光","红","雷蛇","可颂","普罗旺斯","守林人","崖心","初雪","真理","空","狮蝎","食铁兽"};
    String[] Four = {"猎蜂","夜烟","远山","杰西卡","流星","白雪","清道夫","红豆","杜宾","缠丸","霜叶","慕斯","砾",
            "暗索","末药","调香师","角峰","蛇屠箱","古米","深海色","地灵","阿消"};
    String[] Three = {"月见夜","空爆","芬","香草","翎羽","玫兰莎","卡缇","米格鲁","克洛斯","炎熔","芙蓉","安赛尔","史都华德","梓兰"};

    private final Button start = new Button("开始招募");
    private final Button start10Times = new Button("一键十连");
    private final Button reset = new Button("重置");
    private final Label time = new Label("次数");
    private final Label name = new Label("干员姓名");
    private final Label star = new Label("星级");
    private final Label sixTimes = new Label("本期UP斯卡蒂次数：");
    private final TextField[] times = new TextField[10];
    private final TextField[] names = new TextField[10];
    private final TextField[] stars = new TextField[10];

    @Override
    public void start(Stage primaryStage)
    {
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        HBox draw = new HBox(40);
        draw.getChildren().addAll(start, start10Times);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.add(time,0,0);
        for(int i = 0; i < times.length; i++)
        {
            times[i] = new TextField("");
            pane.add(times[i],0,i+1);
            GridPane.setHalignment(times[i],HPos.CENTER);
        }
        pane.add(name,1,0);
        for(int i = 0; i < names.length; i++)
        {
            names[i] = new TextField("");
            pane.add(names[i],1,i+1);
            GridPane.setHalignment(names[i],HPos.CENTER);
        }
        pane.add(star,2,0);
        for(int i = 0; i < stars.length; i++)
        {
            stars[i] = new TextField("");
            pane.add(stars[i],2,i+1);
            GridPane.setHalignment(stars[i],HPos.CENTER);
        }
        pane.add(sixTimes,0,11);
        pane.add(draw,1,11);
        pane.add(reset,2,11);

        GridPane.setHalignment(time,HPos.CENTER);
        GridPane.setHalignment(name,HPos.CENTER);
        GridPane.setHalignment(star,HPos.CENTER);
        GridPane.setHalignment(draw,HPos.CENTER);
        GridPane.setHalignment(sixTimes,HPos.LEFT);
        GridPane.setHalignment(reset,HPos.RIGHT);

        pane.setPadding(new Insets(10));
        primaryStage.setScene(scene);
        primaryStage.setTitle("罗德岛人事招募模拟系统（搅动潮汐之剑卡池）");

        start.setOnAction(e -> drawCard());
        start10Times.setOnAction(e -> drawCard10Times());
        reset.setOnAction(e -> resetAll());

        primaryStage.show();
    }

    private void drawCard()
    {
        probabilityOfStar = Math.random();
        if(drawTimes == 9 && withoutSixOrFiveStarAtFirst10Times)
        {
            probabilityOfStar = probabilityOfStar / 10;
        }
        if(probabilityOfStar < probabilityOfSixStar)
        {
            drawSixStar();
        }
        else if(probabilityOfStar >= probabilityOfSixStar && probabilityOfStar < probabilityOfSixStar + probabilityOfFiveStar)
        {
            drawFiveStar();
        }
        else if(probabilityOfStar >= probabilityOfSixStar + probabilityOfFiveStar && probabilityOfStar < probabilityOfSixStar + probabilityOfFiveStar + probabilityOfFourStar)
        {
            drawFourStar();
        }
        else
        {
            drawThreeStar();
        }
        sixTimes.setText("本期UP六星次数：" + sixStarTimes);
    }

    private void drawCard10Times()
    {
        for(int i = 0;i<10;i++)
        {
            drawCard();
        }
    }

    private void resetAll()
    {
        for(int i = 0; i < stars.length; i++)
        {
            stars[i].setText("");
            times[i].setText("");
            names[i].setText("");
            probabilityOfSixStar = 0.02;
            withoutSixStarTimes = 0;
            withoutSixOrFiveStarAtFirst10Times = true;
            drawTimes = 0;
            sixStarTimes = 0;
        }
    }

    private void drawSixStar()
    {
        indexOfName = (int)(Math.random() * 20);
        if(indexOfName > 10)
        {
            indexOfName = 0;
        }
        stars[drawTimes % 10].setText("六星");
        names[drawTimes % 10].setText(Six[indexOfName]);
        times[drawTimes % 10].setText(String.valueOf(drawTimes + 1));
        drawTimes++;
        withoutSixStarTimes = 0;
        probabilityOfSixStar = 0.02;
        withoutSixOrFiveStarAtFirst10Times = false;
        sixStarTimes ++;
    }

    private void drawFiveStar()
    {
        indexOfName = (int)(Math.random() * 48);
        if(indexOfName > 25)
        {
            indexOfName = (indexOfName % 2 == 0) ? 0 :15;
        }
        stars[drawTimes % 10].setText("五星");
        names[drawTimes % 10].setText(Five[indexOfName]);
        times[drawTimes % 10].setText(String.valueOf(drawTimes + 1));
        drawTimes++;
        withoutSixStarTimes++;
        withoutSixOrFiveStarAtFirst10Times = false;
        if(withoutSixStarTimes > 50)
        {
            probabilityOfSixStar += 0.02;
        }
    }

    private void drawFourStar()
    {
        indexOfName = (int)(Math.random() * 24);
        if(indexOfName > 21)
        {
            indexOfName = (indexOfName % 2 == 0) ? 0 :13;
        }
        stars[drawTimes % 10].setText("四星");
        names[drawTimes % 10].setText(Four[indexOfName]);
        times[drawTimes % 10].setText(String.valueOf(drawTimes + 1));
        drawTimes++;
        withoutSixStarTimes++;
        if(withoutSixStarTimes > 50)
        {
            probabilityOfSixStar += 0.02;
        }
    }

    private void drawThreeStar()
    {
        indexOfName = (int)(Math.random() * 14);
        stars[drawTimes % 10].setText("三星");
        names[drawTimes % 10].setText(Three[indexOfName]);
        times[drawTimes % 10].setText(String.valueOf(drawTimes + 1));
        drawTimes++;
        withoutSixStarTimes++;
        if(withoutSixStarTimes > 50)
        {
            probabilityOfSixStar += 0.02;
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
