import controller.Controller;
import model.Model;
import view.View;

public class Sapper {
    public static void main(String[] args){
        Model sapperModel = new Model();
        Controller sapperController = new Controller(sapperModel);
        View sapperView = new View(sapperController);
        sapperModel.setListener(sapperView);
        sapperController.run();
    }
}