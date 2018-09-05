package com.cambeeler.todolist;

import com.cambeeler.todolist.datamodel.TodoData;
import com.cambeeler.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
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
    private ContextMenu         listContextMenu;


    @FXML
    public void initialize()
    {

//        Action handler for the new menu item used for deleting todo items
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public
            void handle(ActionEvent event)
            {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);

            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);

        todoListView.setItems(TodoData.getInstance().getTodoItems());
//                getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>()
        {


//  The listener is declared in the parameter list, and is code inserted instructions to the UI Event Handler
//  where the actual event handler will 'trigger' a response on the listener every time the todoListView value is changed.

            @Override
            public
            void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue)
            {
                if(newValue != null)
                {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    LongTextDescription.setText(item.getLongDescription());
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY");
                    dueDatesText.setText(dtf.format(item.getTodoDeadLine()));
                }
            }
        });
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>()
        {
//            Callback has two (2) parameters
//            * Object being watched, the parameter for 'call()'
//            * Return type for 'call()'
//            call() is the method that is @overriden to provide conditional code updates

            @Override
            public
            ListCell<TodoItem> call(ListView<TodoItem> param)
            {
                ListCell<TodoItem> cell = new ListCell<>()
                {
                    @Override
                    protected
                    void updateItem(TodoItem item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if(empty)
                        {
                            setText(null);
                        }
                        else
                        {
                            setText(item.getShortDescription());
                            if(item.getTodoDeadLine().isBefore(LocalDate.now().plusDays(1)))
                            {
                                setTextFill(Color.RED);
                            }
                            else if (item.getTodoDeadLine().equals(LocalDate.now().plusDays(+1)))
                            {
                                setTextFill(Color.DARKGREEN);
                            }
                        }
                    }

                }; // end anonymous class call/define/implement with semicolon - not a definition, but an implementation.

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty)->
                 {
                     if(isNowEmpty)
                     {
                         cell.setContextMenu(null);
                     }
                     else
                     {
                         cell.setContextMenu(listContextMenu);
                     }

                 });
                return cell;
            }
        });
    }

    @FXML
    public
    void handleKeyPressed(KeyEvent keyEvent)
    {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null)
        {
            if(keyEvent.getCode().equals(KeyCode.DELETE))
            {
                deleteItem(selectedItem);
            }
        }
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
            DialogueController dc      = fxmlLoader.getController();// fxmlLoader is the reference point to our dialog controller!
            TodoItem           newItem = dc.processResults();
            todoListView.getSelectionModel().select(newItem);

        }
        else
        {
            return;
        }

    }


    //  The delete item dialog
    //  set a confirmation dialog to verify intent

    public
    void deleteItem(TodoItem item)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete todo item");
        alert.setHeaderText("Delete Item: " + item.getShortDescription());
        alert.setContentText("Are you sure?  Press OK to confirm or Cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get()==ButtonType.OK))
        {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }


}