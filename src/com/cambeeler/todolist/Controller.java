package com.cambeeler.todolist;

import com.cambeeler.todolist.datamodel.TodoData;
import com.cambeeler.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;


import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItemList = new ArrayList<>();

    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea LongTextDescription;
    @FXML
    private Label dueDatesText;

    @FXML
    public void initialize()
    {


//        todoItemList.add(new TodoItem("Buy a 50th birthday card for Jon",
//                                      "purchase card",
//                                      LocalDate.of(2018, Month.SEPTEMBER, 10)));
//        todoItemList.add(new TodoItem("Study 5 chapters of JAVAFX software development",
//                                      "JAVAFX",
//                                      LocalDate.of(2018, Month.SEPTEMBER, 1)));
//        todoItemList.add(new TodoItem("Study 5 chapters of DESIGN PATTERNS in JAVA",
//                                      "DESIGN PATTERNS",
//                                      LocalDate.of(2018, Month.SEPTEMBER, 2)));
//        todoItemList.add(new TodoItem("Study 2 chapters of SPRING BOOT",
//                                      "SPRING",
//                                      LocalDate.of(2018, Month.SEPTEMBER, 2)));
//        todoItemList.add(new TodoItem("Study 1 chapter of AMAZON WEB SERVICES",
//                                      "AWS",
//                                      LocalDate.of(2018, Month.SEPTEMBER, 3)));
//        todoListView.getItems().setAll(todoItemList );
//        TodoData.getInstance().setTodoItems(todoItemList);



        ChangeListener<TodoItem> cl = new ChangeListener<TodoItem>()
        {
//  The listener is declared in the parameter list, and is code inserted instructions to the UI Event Handler
//  where the actual event handler will 'trigger' a response on the listener every time the todoListView value is changed.

            @Override
            public
            void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue)
            {
                if(newValue != null)
                {
                    testingtheory();
                }
            }
        };
        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectedItemProperty().addListener(cl);
        todoListView.getSelectionModel().selectFirst();
    }

    private
    void testingtheory(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        LongTextDescription.setText(item.getLongDescription());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY"); // Pattern mapping method...neat
        dueDatesText.setText(dtf.format(item.getTodoDeadLine()));

    }

}
