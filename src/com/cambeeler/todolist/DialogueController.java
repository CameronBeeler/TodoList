package com.cambeeler.todolist;

import com.cambeeler.todolist.datamodel.TodoData;
import com.cambeeler.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public
class DialogueController
{
@FXML private TextField  shortValue;
@FXML private TextArea   longValue;
@FXML private DatePicker tdDue;

    public TodoItem processResults()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY");
        String shortDesc = shortValue.getText().trim();
        String longDesc = longValue.getText().trim();
        LocalDate ldchoice = tdDue.getValue();
        TodoItem newItem = new TodoItem(shortDesc, longDesc, ldchoice);
        if (newItem.getLongDescription().trim().isEmpty() ||
            newItem.getShortDescription().trim().isEmpty() ||
            newItem.getTodoDeadLine().toString().trim().isEmpty())

        {
           return (TodoItem) null;
        }
        else
        {
            TodoData.getInstance().addTodoItem(newItem);
            return newItem;

        }

    }
}
