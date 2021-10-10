import java.util.*;
public class Pathfinder
{
  int removed = 0;
  ArrayList<Position> queue = new ArrayList<Position>();

  //To add a new Position
  public void addPosition(Position p)
  {
    boolean found = false;
    for(Position p2:queue)
    {
      if(p2.x == p.x && p2.y == p.y)
      {
        found = true;
        p2.no += p.no;
      }
    }
    if(!found)
    {
      queue.add(p);
    }
  }

  //To update the bus' goal
  public Position update(int x, int y)
  {
    int c = 0;
    int cIndex = 0;
    int g = 0;
    int index= 0;
    for(int i = 0; i < queue.size(); i++)
    {
      Position p = queue.get(i);
      int d = distance(x, y, p.x, p.y);
      int score = 10 * p.no - d + p.wait;
      if(d == 0)
      {
        queue.remove(p);
        removed = p.no;
        System.out.println("The bus goal has been updated to " + p.x + "," + p.y);
        return p;
      }
      p.wait++;
      if(score > g || i == 0){
        g = score;
        index = i;
      }
      if(d<c){
           c = d;
           cIndex = i;
      }
    }
    if(queue.size() == 0)
    {
      return new Position(0, 0, -1);
    }
    Position p1 = queue.get(index);
    Position p2 = queue.get(cIndex);
    if(distance(x,y,p2.x,p2.y)+distance(p1,p2)==distance(x,y,p1.x,p1.y)){
        return p2;
    }
    return p1;
  }

  //To caluclate the distance between the bus' and destination's positon
  public int distance(int x1, int y1, int x2, int y2)
  {
    return Math.abs(x1 - x2)+Math.abs(y1 - y2);
  }
  //To calculate distance between two points
  public int distance(Position p1,Position p2){
    return distance(p1.x,p1.y,p2.x,p2.y);
  }
}
