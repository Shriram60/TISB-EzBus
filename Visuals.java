import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ConcurrentModificationException;
import javax.sound.midi.*;
public class Visuals extends Application 
{
  final int r = 8;
  final Color stationColor = Color.WHITE;
  final Color busColor = Color.rgb(255, 191, 0);
  final Color dropColor = Color.RED;
  final Color pickColor = Color.rgb(0,100,0);
  Circle[] buses;
  Circle[] stations;
  Line[] roads;
  Grid g;
  Synthesizer synth;
  MidiChannel[] midichannel;
  //Entire animation
  @Override
  public void start(final Stage primaryStage) 
  {
    System.out.println("\f");
    System.out.println("Preparing system...");

    try
    {
      synth = MidiSystem.getSynthesizer();
      synth.open();
      midichannel = synth.getChannels();
      midichannel[0].programChange(0);
    }catch(MidiUnavailableException e)
    {
      e.printStackTrace();
    }
    g = new Grid();
    stations= new Circle[g.stations.size()];
    buses = new Circle[g.buses.size()];
    roads = new Line[10];
    for (int i = 0; i < stations.length; i++) 
    {
      Position b = g.stations.get(i);
      stations[i] = new Circle(b.x * 100 - 70, b.y * 100 - 70, r, stationColor);
    }
    for(int i = 0; i < buses.length; i++)
    {
        Bus b = g.buses.get(i);
        buses[i] = new Circle();
        buses[i].setTranslateX(b.x * 100 - 70);
        buses[i].setTranslateY(b.y * 100 - 70);
        buses[i].setRadius(r);
        buses[i].setFill(busColor);
    }
    for(int i = 0; i < 5; i++)
    {
        Line road =  new Line((i + 1) * 100 - 70, 30, (i + 1) * 100 - 70, 430);
        road.setStroke(Color.WHITE);
        roads[i] = road;
    }
    for(int i = 5; i < 10; i++)
    {
        Line road =  new Line(30 ,(i - 4)* 100 - 70 , 430,(i - 4) * 100 - 70);
        road.setStroke(Color.WHITE);
        roads[i] = road;
    }

    Group main = new Group(stations);
    main.getChildren().add(new Group(roads));
    main.getChildren().add(new Group(buses));
    final Scene scene = new Scene(main, 450, 450, Color.BLACK);
    primaryStage.setScene(scene);
    primaryStage.show();
    midichannel[0].noteOn(60, 80);
    midichannel[0].noteOn(64, 80);
    System.out.println("System ready!");

    System.out.println("--------EzBus Travel Log-------");
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> 
    {
            try
            {
                g.iter();
            }
            catch(ConcurrentModificationException e)
            {

            }
            for(int i = 0; i < stations.length; i++)
            {
                stations[i].setFill(stationColor);
            }
            for(int i = 0; i < buses.length; i++)
            {
                Bus b = g.buses.get(i);
                Node node = buses[i];
                node.setTranslateX(b.x * 100 - 70);
                node.setTranslateY(b.y * 100 - 70);
                for(int i1 = 0; i1 < b.pickup.size(); i1++)
                {
                  PickupPoint p = b.pickup.get(i1);
                    int index = 0;
                    for(int j = 0; j < g.stations.size(); j++)
                     {
                        Position p2 = g.stations.get(j);
                        if(p.x == p2.x && p.y == p2.y)
                        {
                            stations[j].setFill(pickColor);
                        }
                    }
                }
                for(int i1 = 0 ; i1 < b.path.queue.size(); i1++)
                {
                  Position p = b.path.queue.get(i1);
                    for(int j = 0; j < g.stations.size(); j++)
                    {
                        Position p2 = g.stations.get(j);
                        if(p.x == p2.x && p.y==p2.y)
                        {
                            if(stations[j].getFill() != pickColor)
                            {
                                stations[j].setFill(dropColor);
                            }

                        }
                    }
                }
            }
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
  public static void main(String[] args)
  {
        launch(args);
  }
}
