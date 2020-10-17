package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface ControllerListener extends ActionListener {
    @Override
    void actionPerformed(ActionEvent e);
}
