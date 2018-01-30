package com.example.soushinyamaoka.todolist3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Todo> mTodoList;

    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ダミーデータ作成
        mTodoList = Todo.addDummyItem();

        //TODOリスト一覧を表示
        showTodoList();

        //タブレットレイアウトなら右側にフォーム画面を表示
        //FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);
        //if (container2 != null) {
        //    mIsTablet = true;
        //    showTodoForm(mTodoList.get(0));
        //}
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            //フォーム画面を開いている場合は画面を閉じる
            getSupportFragmentManager().popBackStack();
        } else {
            //リスト画面の場合は通常のバックキー処理(アプリを終了)
            super.onBackPressed();
        }
    }

    /**
     * TODOリスト一覧を表示
     */
    public void showTodoList() {
        String tag = TodoListFragment.TAG;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                TodoListFragment.newInstance(), tag).commit();
    }

    /**
     * TODOフォーム画面を表示
     *
     * @param item TODOリストデータ
     */
    public void showTodoForm(Todo item) {
        String tag = TodoFormFragment.TAG;
        TodoFormFragment fragment;
        if (item == null) {
            fragment = TodoFormFragment.newInstance();
        } else {
            fragment = TodoFormFragment.newInstance(item.getValue(), item.getCreatedTime());
        }
            //スマートフォンレイアウトの場合はcontainerに表示
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    fragment, tag).addToBackStack(tag).commit();
        //else{
            //タブレットレイアウトの場合はcontainer2に表示
         //  getSupportFragmentManager().beginTransaction().replace(R.id.container2,
         //          fragment, tag).addToBackStack(tag).commit();
        //}
    }

    public List<Todo> getTodoList() {
        return mTodoList;
    }

    /**
     * タブレットか判定.
     * @return
     */
    public boolean isTablet() {
        return mIsTablet;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
