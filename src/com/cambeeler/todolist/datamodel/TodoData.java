package com.cambeeler.todolist.datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public
class TodoData
{
    private static TodoData instance = new TodoData(); // one time load in a static variable - it belongs to the Class...
    private static String filename = "todolistitems.txt";
    private List<TodoItem> todoItems;
    private DateTimeFormatter dtf;

    public static TodoData getInstance() // factory method..
    {
        return instance;
    }

    private TodoData()
    {
        dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
    }

    public
    List<TodoItem> getTodoItems()
    {
        return todoItems;
    }

        public
    void storeTodoItems()
    throws IOException
    {
        Path           path = Paths.get(filename);
        BufferedWriter bw   = Files.newBufferedWriter(path);
        String         input;

        try
        {
            Iterator<TodoItem> iter = todoItems.iterator();
            while(iter.hasNext())
            {
                TodoItem item = iter.next();
                bw.write(String.format("%s@%s@%s",
                                       item.getShortDescription(),
                                       item.getLongDescription(),
                                       item.getTodoDeadLine().format(dtf)));
                bw.newLine();
            }

        }
        finally
        {
            if(bw!=null){
                bw.close();
            }
        }
    }

    public
    void loadTodoItems()
        throws IOException
    {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);
        String input;
        try
        {
            while((input = br.readLine()) != null)
            {
                String[] itemPieces = input.split("@");
                String shortDescription = itemPieces[0];
                String longDescription = itemPieces[1];
                String dateString = itemPieces[2];
                LocalDate date = LocalDate.parse(dateString, dtf);
                TodoItem todoItem = new TodoItem(shortDescription, longDescription, date);

                todoItems.add(todoItem);
            }
        }
        finally
        {
            if(br!=null)
            {
                br.close();
            }
        }
    }
}
