import controller.Controller;


import model.Difficult;
import model.Model;
import view.View;
import org.junit.Test;

public class ControllerTest {


    private Difficult ExpectedDifficult;


    @Test
    public void setDifficultTest(){
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.setListener(view);
        assert model.getDifficult()== ExpectedDifficult;

    }
    @Test
    public void leftClickTest(){
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.setListener(view);
        int ExpectedLine = 1;
        int ExpectedColumn = 1;
        controller.leftClick(ExpectedLine,ExpectedColumn);
        model.openCell(ExpectedColumn,ExpectedLine);
        assert false;
    }

    @Test
    public void setCustomLevelTest() {

        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.setListener(view);
        int ExpectedLine = 16;
        int ExpectedColumn = 16;
        int ExpectedMine = 40;
        controller.setCustomLevel(ExpectedLine,ExpectedColumn,ExpectedMine);
        assert model.getHeight() == ExpectedLine;
        assert model.getWidth() == ExpectedColumn;
        assert model.getCountMinesOnField() == ExpectedMine;
    }

    @Test
    public void startTimerTest (){
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.setListener(view);
        controller.getTime();
        model.getSeconds();
        assert false;

    }
    @Test
    public void stopTimerTest (){
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.setListener(view);
        controller.stopTimer();
        assert false;
    }

}

