package com.example.soushinyamaoka.todolist3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syama on 2018/01/27.
 */

public class Todo {

    private long createdTime;

    private String value;


    public Todo(String value, long createdTime) {
        this.value = value;
        this.createdTime = createdTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * テスト表示用にダミーのリストアイテムを作成.
     */
    public static List<Todo> addDummyItem() {
        List<Todo> items = new ArrayList<>();
        items.add(new Todo("猫に小判", System.currentTimeMillis() + 1));
        items.add(new Todo( "猫の手も借りたい", System.currentTimeMillis() + 2));
        items.add(new Todo( "窮鼠猫を噛む", System.currentTimeMillis() + 3));
        items.add(new Todo("猫は三年飼っても三日で恩を忘れる", System.currentTimeMillis() + 4));
        items.add(new Todo( "猫も杓子も", System.currentTimeMillis() + 5));
        return items;
    }
}
