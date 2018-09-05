package com.cambeeler.todolist.datamodel;

import java.time.LocalDate;

public
class TodoItem
{
    private String longDescription, shortDescription;
    private LocalDate todoDeadLine;

    public
    TodoItem(String shortDescription, String longDescription, LocalDate todoDeadLine)
    {
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
        this.todoDeadLine = todoDeadLine;
    }

    public
    String getLongDescription()
    {
        return longDescription;
    }

    public
    void setLongDescription(String longDescription)
    {
        this.longDescription = longDescription;
    }

    public
    String getShortDescription()
    {
        return shortDescription;
    }

    public
    void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public
    LocalDate getTodoDeadLine()
    {
        return todoDeadLine;
    }

    public
    void setTodoDeadLine(LocalDate todoDeadLine)
    {
        this.todoDeadLine = todoDeadLine;
    }

    public void clear()
    {
        this.setLongDescription(null);
        this.setShortDescription(null);
        this.setTodoDeadLine(null);
    }

}
