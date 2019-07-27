package main.java;

import main.java.controller.Controller;
import main.java.model.Model;
import main.java.view.View;

public class Sapper {
    public static void main(String[] args){
        Model sapperModel = new Model();
        Controller sapperController = new Controller(sapperModel);
        View sapperView = new View(sapperController);
        sapperModel.setListener(sapperView);
        sapperController.run();
    }
}