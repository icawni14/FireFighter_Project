import java.util.*;


public class Model {
  Grid grid;
  int colCount, rowCount;
  List<Position> firefighters = new ArrayList<>();
  Set<Position> fires = new HashSet<>();
  List<Position> ffNewPositions;

  List<Position> clouds = new ArrayList<>();
  List<Position> cloudNewPositions;



  List<Position> fireFightersMotorized = new ArrayList<>();
  List<Position> fireFightersMotorizedPositions;

  int step = 0;

  public Model(Grid grid) {
    this.grid = grid;
    colCount = grid.colCount;
    rowCount = grid.rowCount;
  }


  public void initialisation(int fireNumber, int fireFighterNumber, int cloudNumber, int fireFightersMotorizedNumber){
    for(int index=0; index<fireNumber;index++)
      fires.add(randomPosition());
    for(int index=0; index<fireFighterNumber;index++)
      firefighters.add(randomPosition());

    //clouds
    for(int index = 0; index < cloudNumber; index++){
      clouds.add(randomPosition());
    }

    //fireFightersMotorized
    for(int index = 0; index < fireFightersMotorizedNumber; index++){
      fireFightersMotorized.add(randomPosition());
    }


  }

  private Position randomPosition() {
    return new Position((int) (Math.random()*rowCount), (int) (Math.random()*colCount));
  }


  public void activation(){
    ffNewPositions = new ArrayList<>();
    for(Position ff : firefighters){
      Position newPosition = activateFirefighter(ff);
      grid.paint(ff.row,ff.col);
      grid.paintFF(newPosition.row, newPosition.col);
      ffNewPositions.add(newPosition);
    }
    firefighters = ffNewPositions;


    if(step%2==0){
      List<Position> newFires = new ArrayList<>();
      for(Position fire : fires){
        newFires.addAll(activateFire(fire));
      }
      for(Position newFire : newFires)
        grid.paintFire(newFire.row, newFire.col);

      fires.addAll(newFires);}
    step++;

    //for cloud
    cloudNewPositions = new ArrayList<>();
    for(Position cloud : clouds){
      Position newCloudPosition = activateClouds(cloud);
      grid.paint(cloud.row, cloud.col);
      grid.paintCloud(newCloudPosition.row, newCloudPosition.col);
      cloudNewPositions.add(newCloudPosition);
    }
    clouds = cloudNewPositions;


    //for fireFighterMotorized

    fireFightersMotorizedPositions = new ArrayList<>();
    for(Position ffM : fireFightersMotorized){
      Position newPosition = activateFirefighterMotorized(ffM);
      grid.paint(ffM.row,ffM.col);
      grid.paintFFM(newPosition.row, newPosition.col);
      fireFightersMotorizedPositions.add(newPosition);
    }
    fireFightersMotorized = fireFightersMotorizedPositions;



  }


  private List<Position> activateFire(Position position) {
    return next(position);
  }

  private Position activateFirefighter(Position position) {
    Position randomPosition = aStepTowardFire(position);
    //next(position).get((int) (Math.random()*next(position).size()));
    List<Position> nextFires = next(randomPosition).stream().filter(fires::contains).toList();
    extinguish(randomPosition);
    for (Position fire : nextFires)
      extinguish(fire);
    return randomPosition;
    }

    private void extinguish(Position position) {
      fires.remove(position);
      grid.paint(position.row, position.col);
  }

  //activateClouds

  private Position activateClouds(Position position){
    Position newPosition;
    Position beforeChangePosition = new Position(0,0);
    boolean check = false;
    while(!check) {
      beforeChangePosition = new Position(position.row() + randomInt(), position.col() + randomInt());
      if (beforeChangePosition.row < this.rowCount && beforeChangePosition.row > 0 && beforeChangePosition.col < this.colCount && beforeChangePosition.col > 0)
        check = true;
    }
    newPosition = beforeChangePosition;
    List<Position> nextFires = next(newPosition).stream().filter(fires::contains).toList();
    extinguish(newPosition);
    for (Position fire : nextFires)
      extinguish(fire);
    return newPosition;
  }
  private int randomInt () {
    int min = 0, max=2;
    int result = (int) (min + Math.floor ((max - min + 1) * Math.random ()));
    return result-1;
  }

  private Position activateFirefighterMotorized(Position position) {
    Position randomPosition = twoStepTowardFire(position);
    //next(position).get((int) (Math.random()*next(position).size()));
    List<Position> nextFires = next(randomPosition).stream().filter(fires::contains).toList();
    extinguish(randomPosition);
    for (Position fire : nextFires)
      extinguish(fire);
    return randomPosition;
  }

  private List<Position> next(Position position){
    List<Position> list = new ArrayList<>();
    if(position.row>0) list.add(new Position(position.row-1, position.col));
    if(position.col>0) list.add(new Position(position.row, position.col-1));
    if(position.row<rowCount-1) list.add(new Position(position.row+1, position.col));
    if(position.col<colCount-1) list.add(new Position(position.row, position.col+1));
    return list;
  }

  private List<Position> nextTwoPosition(Position position){
    List<Position> list = new ArrayList<>();
    if(position.row>0) list.add(new Position(position.row-2, position.col));
    if(position.col>0) list.add(new Position(position.row, position.col-2));
    if(position.row<rowCount-1) list.add(new Position(position.row+2, position.col));
    if(position.col<colCount-1) list.add(new Position(position.row, position.col+2));
    return list;
  }

  private Position aStepTowardFire(Position position){
    Queue<Position> toVisit = new LinkedList<>();
    Set<Position> seen = new HashSet<>();
    HashMap<Position,Position> firstMove = new HashMap<>();
    toVisit.addAll(next(position));
    for(Position initialMove : toVisit)
      firstMove.put(initialMove,initialMove);
    while(!toVisit.isEmpty()){
      Position current = toVisit.poll();
      if(fires.contains(current))
        return firstMove.get(current);
      for(Position adjacent : next(current)){
        if(seen.contains(adjacent)) continue;
        toVisit.add(adjacent);
        seen.add(adjacent);
        firstMove.put(adjacent, firstMove.get(current));
      }
    }
    return position;
  }

  // pompiers motorisés

  private Position twoStepTowardFire(Position position){
    Queue<Position> toVisit = new LinkedList<>();
    Set<Position> seen = new HashSet<>();
    HashMap<Position,Position> firstMove = new HashMap<>();
    toVisit.addAll(nextTwoPosition(position));
    for(Position initialMove : toVisit)
      firstMove.put(initialMove,initialMove);
    while(!toVisit.isEmpty()){
      Position current = toVisit.poll();
      if(fires.contains(current))
        return firstMove.get(current);
      for(Position adjacent : nextTwoPosition(current)){
        if(seen.contains(adjacent)) continue;
        toVisit.add(adjacent);
        seen.add(adjacent);
        firstMove.put(adjacent, firstMove.get(current));
      }
    }
    return position;
  }


  public record Position(int row, int col){}



}
