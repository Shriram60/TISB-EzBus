import java.util.*;
public class Grid
{
  Random rn = new Random();
  int size = 5;
  ArrayList<Position> stations = new ArrayList<Position>();
  ArrayList<Bus> buses = new ArrayList<Bus>();
  ArrayList<PickupPoint> UnresolvedPickup = new ArrayList<PickupPoint>();
  ArrayList<PickupPoint> pickup = new ArrayList<PickupPoint>();
  public  Grid()
  {
    while(stations.size()<5)
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
      stations.add(new Position(x,y));
    }

    }
  }
  public static void main()
  {
    Grid g = new Grid();
    g.buses.add(new Bus(1,1));
    g.add();
    g.updateBuses();
    try
    {
        PickupPoint p = g.pickup.get(0);
        Bus b = g.buses.get(0);
        System.out.println("\n"+p.x+","+p.y+","+p.num+","+Arrays.toString(p.goals));
        int d = g.distance(b.x,b.y,p.x,p.y);
        for(int i = 0;i<=d;i++)
        {
          g.updateBuses();
        }
        System.out.println(b.x+","+b.y);
        Position goal = b.path.queue.get(0);
        System.out.println(goal.x+","+goal.y);
        d = g.distance(b.x,b.y,goal.x,goal.y);
        for(int i = 0;i <= d;i++)
        {
          g.updateBuses();
        }
        System.out.println(b.x + "," + b.y);
    }
    catch(Exception e)
    {
        Grid.main();
    }
  }

  public void updateBuses()
  {
    for(Bus b:buses)
    {
      b.update();
      searchBus();
    }
  }
  public void add()
  {
    int s = rn.nextInt(5) + 1;
    int[] goals = new int[stations.size()];
    int num = 0;
    while(Math.random() > 0.5)
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
      PickupPoint point = new PickupPoint(pos.x,pos.y,num,goals);
      pickup.add(point);
      query(point);
    }
  }

  public void query(PickupPoint p)
  {
    for(int i = 0;i < buses.size();i++)
    {
      Bus a = buses.get(i);
      if(a.addPickup(new Position(p.x,p.y,p.num)))
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
  public int distance(int x1,int y1,int x2,int y2)
  {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }

  public void searchBus()
  {
    for(Bus b:buses)
    {
      if(b.stopped)
      {
        boolean found = false;
        for(int i = 0;i < pickup.size();i++)
        {
            PickupPoint p = pickup.get(i);
          if(b.x == p.x && b.y == p.y)
          {
            found = true;
            busPickup(b,p);
          }
        }
        if(!found)  //looks so cool
        {
          b.drop(); //ezbus
        }
      }
    }
  }
  public void busPickup(Bus b,PickupPoint p)
  {
    b.current_cap += p.num;
    pickup.remove(p);
    for(int i = 0;i < p.goals.length;i++)
    {
      int n = p.goals[i];
      if(n != 0)
      {
        Position p2 = stations.get(i);
        p2.no = n;
        b.addGoal(p2);
      }
    }
    System.out.println(b.current_cap);
    System.out.println(b.path.queue.size());
  }
  class PickupPoint{
    int x;
    int y;
    int[] goals;
    int num;
    public PickupPoint(int x,int y,int num,int[] goals){
      this.x = x;
      this.y = y;
      this.goals =goals;
      this.num = num;
    }
  }
}
