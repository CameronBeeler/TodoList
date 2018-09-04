package com.cambeeler.todolist;

import com.cambeeler.todolist.datamodel.TodoData;
import com.cambeeler.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItemList = new ArrayList<>();

    @FXML
    private BorderPane         MainWindow;
    @FXML
    private DialogPane          FileMenuDialog;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea           LongTextDescription;
    @FXML
    private Label              dueDatesText;

    @FXML
    public void initialize()
    {
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

    @FXML
    public void showNewItemDialogue()
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newItemDialogue.fxml"));

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(MainWindow.getScene().getWindow());
        dialog.setTitle("Add New Todo Item");
        dialog.setHeaderText("Use this dialog to create new events");
        try
        {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e)
        {
            System.out.println("Could not load the dialog pane");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            System.out.println("OK button selected");
            DialogueController dc      = fxmlLoader.getController();// fxmlLoader is the reference point to our dialog controller!
            TodoItem           newItem = dc.processResults();
            todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(newItem);

        }
        else
        {
            System.out.println("Cancel pressed");
        }

    }

    private
    void testingtheory(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        LongTextDescription.setText(item.getLongDescription());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY"); // Pattern mapping method...neat
        dueDatesText.setText(dtf.format(item.getTodoDeadLine()));

    }

}