package pl.wietwioorki.to22019.util;

import pl.wietwioorki.to22019.controller.AbstractWindowController;

import java.util.ArrayList;
import java.util.List;

public class MyEventHandler {
    List<AbstractWindowController> listeners = new ArrayList<>();

    public void AddListener(AbstractWindowController listener) {
        listeners.add(listener);
    }

    public void userChanged() {
        for (AbstractWindowController listener : listeners) {
            listener.handleChangeUser();
        }
    }

    public void dataChanged() {
        for (AbstractWindowController listener : listeners) {
            listener.handleChangeData();
        }
    }
}
