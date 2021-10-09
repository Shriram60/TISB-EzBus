import java.util.*;
public class Pathfinder
{
  int removed = 0;
  ArrayList<Position> queue = new ArrayList<Position>();
  public void addP(Position p){
    boolean found = false;
    for(Position p2:queue){
      if(p2.x == p.x && p2.y == p.y){
        found = true;
        p2.no += p.no;
      }
    }
    if(!found)
    {
      queue.add(p);
    }
  }
  public Position update(int x,int y)
  {
    int g = 0;
    int index= 0;
    for(int i = 0;i < queue.size();i++)
    {
      Position p = queue.get(i);
      int d = distance(x,y,p.x,p.y);
      int score = 10 * p.no - d + p.wait;
      if(d == 0)
      {
        queue.remove(p);
        removed = p.no;
        return p;
      }
      p.wait++;
      if(score > g || i == 0){
        g = score;
        index = i;
      }
    }
    if(queue.size() == 0)
    {
      return new Position(0, 0, -1);
    }
    return queue.get(index);
  }
  public int distance(int x1,int y1,int x2,int y2)
  {
    return Math.abs(x1-x2)+Math.abs(y1-y2);
  }
}
