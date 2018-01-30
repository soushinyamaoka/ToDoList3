package com.example.soushinyamaoka.todolist3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by syama on 2018/01/27.
 */

public class TodoFormFragment extends Fragment {

    public static final String TAG = TodoFormFragment.class.getSimpleName();

    private static final int MENU_ADD = 1;

    public static final String ARGS_VALUE = "key-value";

    public static final String ARGS_CREATEDTIME = "key-createdtime";

    private long mCreatedTime = 0;

    private EditText mEtInput;

    private boolean mIsTextEdited = false;

    private MenuItem mMenuAdd;

    public static TodoFormFragment newInstance() {
        return new TodoFormFragment();
    }

    public static TodoFormFragment newInstance(String value, long createdTime) {
        TodoFormFragment fragment = new TodoFormFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_VALUE, value);
        args.putLong(ARGS_CREATEDTIME, createdTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MenuItemの追加を許可
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        //入力フォームのインスタンスを取得
        mEtInput = (EditText) rootView.findViewById(R.id.input);
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキストの中身が変更されたら編集したと判定
                mIsTextEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //編集データを受け取っていたらセット
        Bundle args = getArguments();
        if (args != null) {

            //値をセット
            String value = args.getString(ARGS_VALUE);
            mEtInput.setText(value);

            //作成時間をセット
            mCreatedTime = args.getLong(ARGS_CREATEDTIME, 0);
        }

        return rootView;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(MENU_ADD);
        if (menuItem == null) {
            mMenuAdd = menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "ADD");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mMenuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ADD) {
            //TODOリストを追加
            String value = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(value) && mIsTextEdited) {
                Intent resultData = new Intent();
                resultData.putExtra(ARGS_VALUE, value);
                if (mCreatedTime == 0) {
                    //作成時間がない場合は新規データとして作成時間を生成
                    resultData.putExtra(ARGS_CREATEDTIME, System.currentTimeMillis());
                } else {
                    //作成時間がある場合は既存のデータを更新
                    resultData.putExtra(ARGS_CREATEDTIME, mCreatedTime);
                }

                //Broadcastを送信
                resultData.setAction(TodoListFragment.ACTION_CREATE_TODO);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(resultData);

                boolean isTablet = ((MainActivity) getActivity()).isTablet();
                if (!isTablet) {
                    //スマートフォンレイアウトの場合はリスト画面に戻る
                    getFragmentManager().popBackStack();
                } else {
                    //タブレットレイアウトで新規TODOを作成した場合はテキストをクリア
                    if (getArguments() == null) {
                        mEtInput.getText().clear();
                    }
                }

                //ソフトウェアキーボードを閉じる
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
