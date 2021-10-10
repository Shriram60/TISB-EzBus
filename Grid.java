import java.util.*;
public class Grid
{
  Random rn = new Random();
  int size = 5;
  ArrayList<Position> stations = new ArrayList<Position>();
  ArrayList<Bus> buses = new ArrayList<Bus>();
  ArrayList<PickupPoint> UnresolvedPickup = new ArrayList<PickupPoint>();

  //To assign random position for stations inside the grid
  public Grid()
  {
    while(stations.size() < 5)
    {
    int x = rn.nextInt(5) + 1;
    int y = rn.nextInt(5) + 1;
    boolean b = true;
    for(Position p:stations)
    {
      if(p.x == x && p.y == y)
      {
        b = false;
      }
    }
    if(b)
    {
      stations.add(new Position(x, y,0));
    }

    }
    buses.add(new Bus(stations.get(0).x, stations.get(0).y));
  }
  //To update all the Buses
  public void updateBuses()
  {
    for(Bus b:buses)
    {
      b.update();
      ScanBus();
    }
  }

  //To add a new Pickup Point
  public void add()
  {
    int s = rn.nextInt(5);
    int[] goals = new int[stations.size()];
    int num = 0;
    while(Math.random() > 0.75)
    {
      int r = rn.nextInt(stations.size());
      while(r == s)
      {
        r = rn.nextInt(stations.size());
      }
      goals[r]++;
      num++;
    }
    if(num != 0)
    {
      Position pos = stations.get(s);
      PickupPoint point = new PickupPoint(pos.x, pos.y, num,goals);
      System.out.println("New pickup point added at "+point.x+","+point.y+" with "+point.num+" people");
      query(point);
    }

  }

  //To scan the buses
  public void ScanBus()
  {
    for(Bus b:buses)
    {
      if(b.stopped)
      {
        b.stopped = false;
        b.busPickup(stations);
      }
    }
  }

  //To send a query to the buses, to see if any can pick up
  public void query(PickupPoint p)
  {
    for(int i = 0; i < buses.size(); i++)
    {
      Bus a = buses.get(i);
      if(a.addPickup(p))
      {
        if(UnresolvedPickup.contains(p))
        {
          UnresolvedPickup.remove(p);
        }
        return;
      }
    }
    if(!UnresolvedPickup.contains(p))
    {
      UnresolvedPickup.add(p);
    }
  }

  //To calculate the distance between the positon of the bus and the destination
  public int distance(int x1, int y1, int x2, int y2)
  {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
  public void iter()
  {
    if(Math.random()>0.5)
    {
      add();
    }
    updateBuses();
  }
}
