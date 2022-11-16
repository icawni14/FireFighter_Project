import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Grid extends Canvas{
    int width, height, colCount, rowCount;
    Model model;


    public Grid(int width, int height, int colCount, int rowCount) {
        super(width,height);
        this.width = width;
        this.height = height;
        this.colCount = colCount;
        this.rowCount = rowCount;
        setFocusTraversable(true);
        setOnMousePressed(this::mousePressed);
        model = new Model(this);
        model.initialisation(3,8, 2); /// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public void restart(MouseEvent mouseEvent){
        model = new Model(this);
        model.initialisation(3,6, 2);
        getGraphicsContext2D().clearRect(0,0,width,height);
        repaint();
    }
    private void mousePressed(MouseEvent mouseEvent) {
        model.activation();
        repaint();
            /*double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            model.click((int)x*rowCount/height,(int)y*colCount/width);*/
    }

    void repaint(){
        for(int col=0; col<colCount; col++)
            getGraphicsContext2D().strokeLine(0, col*width/colCount,height, col*width/colCount);
        for(int row=0; row<rowCount;row++)
            getGraphicsContext2D().strokeLine(row*height/rowCount,0,row*height/rowCount,width);

    }

    void paint(int row, int col){
        getGraphicsContext2D().setFill(Color.WHITE);
        getGraphicsContext2D().fillRect(row*height/rowCount,col*width/colCount,height/rowCount,width/colCount);
    }

    public void paintFF(int row, int col) {
        getGraphicsContext2D().setFill(Color.BLUE);
        getGraphicsContext2D().fillOval(row*height/rowCount,col*width/colCount,height/rowCount,width/colCount);
    }

    public void paintFire(int row, int col) {
        getGraphicsContext2D().setFill(Color.RED);
        getGraphicsContext2D().fillRect(row*height/rowCount,col*width/colCount,height/rowCount,width/colCount);
    }

     public void paintCloud(int row, int col){
        getGraphicsContext2D().setFill(Color.GRAY);
        getGraphicsContext2D().fillRect(row*height/rowCount, col*width/colCount, height/rowCount, width/colCount);
    }
    public void paintFFM(int row, int col){
    }
    public void paintMountain(int row, int col){
        getGraphicsContext2D().setFill(Color.GRAY);
        getGraphicsContext2D().fillRect(row*height/rowCount, col*width/colCount, height/rowCount, width/colCount);
    }
    public void paintRoad(int row, int column){

    }
    public void paintRocks(int row, int column){

    }

}
