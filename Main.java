import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
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
    private double probabilityOfSixStar = 0.02;
    private double probabilityOfFiveStar = 0.08;
    private double probabilityOfFourStar = 0.5;
    private int withoutSixStarTimes = 0;
    private int drawTimes = 0;
    private int sixStarTimes = 0;
    private int allSixTimes = 0;
    private int allFiveTimes = 0;
    private int allFourTimes = 0;
    private int allThreeTimes = 0;

    private double probabilityOfStar;       //星级概率
    private int indexOfName;          //具体干员位置
    private String[] Six = {"斯卡蒂","能天使","推进之王","伊芙利特","艾雅法拉","安洁莉娜","闪灵","夜莺","星熊","塞雷娅","银灰"};
    private String[] Five = {"夜魔","白面鸮","凛冬","德克萨斯","芙兰卡","拉普兰德","幽灵鲨","蓝毒","白金","陨星","天火","梅尔",
            "赫墨","华法琳","临光","红","雷蛇","可颂","普罗旺斯","守林人","崖心","初雪","真理","空","狮蝎","食铁兽"};
    private String[] Four = {"猎蜂","夜烟","远山","杰西卡","流星","白雪","清道夫","红豆","杜宾","缠丸","霜叶","慕斯","砾",
            "暗索","末药","调香师","角峰","蛇屠箱","古米","深海色","地灵","阿消"};
    private String[] Three = {"月见夜","空爆","芬","香草","翎羽","玫兰莎","卡缇","米格鲁","克洛斯","炎熔","芙蓉","安赛尔","史都华德","梓兰"};

    private final Button start = new Button("开始寻访");
    private final Button start10Times = new Button("一键十连");
    private final Button reset = new Button("重置");
    private final Label time = new Label("次数");
    private final Label name = new Label("干员姓名");
    private final Label star = new Label("星级");
    private final Label sixTimes = new Label("本期UP特定六星次数：0");
    private final Label information = new Label("【搅动潮汐之剑】干员列表\n" +
            "※出现率上升※\n" +
            "★★★★★★\n" +
            "斯卡蒂   （占6★出率的50%）\n" +
            "★★★★★\n" +
            "夜魔/临光（占5★出率的50%）\n" +
            "★★★★\n" +
            "猎蜂/暗索（占4★出率的20%）\n" +
            "\n" +
            "该寻访为【标准寻访】");
    private final Label sixTimesResult = new Label("六星次数：0，概率：0%");
    private final Label fiveTimesResult = new Label("五星次数：0，概率：0%");
    private final Label fourTimesResult = new Label("四星次数：0，概率：0%");
    private final Label threeTimesResult = new Label("三星次数：0，概率：0%");
    private final TextField[] times = new TextField[10];
    private final TextField[] names = new TextField[10];
    private final TextField[] stars = new TextField[10];

    @Override
    public void start(Stage primaryStage)
    {
        GridPane drawCardResult = new GridPane();
        GridPane main = new GridPane();
        Scene scene = new Scene(main);
        HBox drawMod = new HBox(40);
        VBox resultInformation = new VBox(10);
        drawMod.getChildren().addAll(start, start10Times);
        resultInformation.getChildren().addAll(information,sixTimesResult,fiveTimesResult,fourTimesResult,threeTimesResult);
        drawCardResult.setHgap(10);
        drawCardResult.setVgap(10);
        drawCardResult.add(time,0,0);
        for(int i = 0; i < times.length; i++)
        {
            times[i] = new TextField("");
            times[i].setAlignment(Pos.CENTER);
            drawCardResult.add(times[i],0,i+1);
            GridPane.setHalignment(times[i],HPos.CENTER);
        }
        drawCardResult.add(name,1,0);
        for(int i = 0; i < names.length; i++)
        {
            names[i] = new TextField("");
            names[i].setAlignment(Pos.CENTER);
            drawCardResult.add(names[i],1,i+1);
            GridPane.setHalignment(names[i],HPos.CENTER);
        }
        drawCardResult.add(star,2,0);
        for(int i = 0; i < stars.length; i++)
        {
            stars[i] = new TextField("");
            stars[i].setAlignment(Pos.CENTER);
            drawCardResult.add(stars[i],2,i+1);
            GridPane.setHalignment(stars[i],HPos.CENTER);
        }
        drawCardResult.add(sixTimes,0,11);
        drawCardResult.add(drawMod,1,11);
        drawCardResult.add(reset,2,11);

        GridPane.setHalignment(time,HPos.CENTER);
        GridPane.setHalignment(name,HPos.CENTER);
        GridPane.setHalignment(star,HPos.CENTER);
        GridPane.setHalignment(drawMod,HPos.CENTER);
        GridPane.setHalignment(sixTimes,HPos.LEFT);
        GridPane.setHalignment(reset,HPos.RIGHT);

        main.add(drawCardResult,0,0);
        main.add(resultInformation,1,0);

        drawCardResult.setPadding(new Insets(10));
        primaryStage.setScene(scene);
        primaryStage.setTitle("罗德岛制药公司干员寻访模拟系统（搅动潮汐之剑卡池）");

        start.setOnAction(e -> drawCard());
        start10Times.setOnAction(e -> drawCard10Times());
        reset.setOnAction(e -> resetAll());

        primaryStage.show();
    }

    private void drawCard()
    {
        probabilityOfStar = Math.random();
        if(drawTimes == 9)
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
        updateInformation();
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
        probabilityOfSixStar = 0.02;
        withoutSixStarTimes = 0;
        drawTimes = 0;
        sixStarTimes = 0;
        allSixTimes = 0;
        allFiveTimes = 0;
        allFourTimes = 0;
        allThreeTimes = 0;
        for(int i = 0; i < stars.length; i++)
        {
            stars[i].setText("");
            times[i].setText("");
            names[i].setText("");
        }
        updateInformation();
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
        allSixTimes ++;
        if(indexOfName == 0)
        {
            sixStarTimes ++;
        }
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
        allFiveTimes ++;
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
        allFourTimes ++;
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
        allThreeTimes ++;
        if(withoutSixStarTimes > 50)
        {
            probabilityOfSixStar += 0.02;
        }
    }

    private void updateInformation()
    {
        sixTimesResult.setText("六星次数："+ allSixTimes+"，概率："+ ((double)allSixTimes * 100/drawTimes) +"%");
        fiveTimesResult.setText("五星次数："+ allFiveTimes+"，概率："+ ((double)allFiveTimes * 100/drawTimes) +"%");
        fourTimesResult.setText("四星次数："+ allFourTimes+"，概率："+ ((double)allFourTimes * 100/drawTimes) +"%");
        threeTimesResult.setText("三星次数："+ allThreeTimes+"，概率："+ ((double)allThreeTimes * 100/drawTimes) +"%");
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
