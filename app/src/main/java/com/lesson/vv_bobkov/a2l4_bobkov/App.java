package com.lesson.vv_bobkov.a2l4_bobkov;

import android.app.Application;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samsung on 28.11.2017.
 */

public class App extends Application {

    /**
     * To specify the mode for opening a note activity
     */
    static final boolean
            NOTES_MODE_OPEN = true,
            NOTES_MODE_EDIT = false;
    static boolean NOTES_MODE = false;
    /**
     * To specify the opening mode of the AttentionDialog
     */
    static final int
            WHITOUT_BUTTON = 0,
            WITH_TO_RETRY = 1;
    private boolean resultAttentionDialog = false;

    private static App mApp;
    private static Menu mMenu;
    private static DBController mDBController;

    private static ArrayList<NoteWithTitle> mNoteWithTitleList = null;
    private static HashMap<Integer, NoteWithTitle> mSelectedItems;

    public boolean isResultAttentionDialog() {
        return resultAttentionDialog;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApp == null) {
            mApp = this;
        }
        mDBController = new DBController(mApp);
    }

    public static App getmApp() {
        return mApp;
    }

    public Menu getmMenu() {
        return mMenu;
    }

    public void setmMenu(Menu menu) {
        App.mMenu = menu;
    }

    public DBController getmDBController() {
        return mDBController;
    }

    public void setmDBController(DBController mDBController) {
        App.mDBController = mDBController;
    }

    public void setmNoteWithTitleList(ArrayList<NoteWithTitle> noteWithTitleList) {
        App.mNoteWithTitleList = noteWithTitleList;
    }

    public List<NoteWithTitle> getmNoteWithTitleList() {
        return mNoteWithTitleList;
    }

    public boolean mNoteWithTitleListIsEmpty() {

        if (mNoteWithTitleList == null ||
                (mNoteWithTitleList.size() == 1 &&
                        mNoteWithTitleList.get(0).getmTitle().equals(
                                mApp.getResources().getString(R.string.notes_no)
                        ))) return true;
        return false;
    }

    public void addNewNoteToNoteWithTitleList(NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.add(noteWithTitle);
    }

    public void remNoteFromNoteWithTitleList(int position) {
        mNoteWithTitleList.remove(position);
    }

    public void editNoteFromNoteWithTitleList(int position, NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.get(position).editNote(noteWithTitle);
    }

    public boolean selectedItemsIsEmpty() {

        if (mSelectedItems == null ||
                mSelectedItems.isEmpty()) {
            return true;
        }
        return false;
    }

    public HashMap<Integer, NoteWithTitle> getmSelectedItems() {
        return mSelectedItems;
    }

    public void createmSelectedItems() {
        App.mSelectedItems = new HashMap<>();
    }

    public NoteWithTitle getmSelectedItem(Integer positoin) {
        return App.mSelectedItems.get(positoin);
    }

    public void addSelectedItem(Integer positoin, NoteWithTitle noteWithTitle) {
        App.mSelectedItems.put(positoin, noteWithTitle);
    }

    public void prepareMenu(Menu menu) {

        if (mApp.mNoteWithTitleListIsEmpty() || mApp.selectedItemsIsEmpty()) {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);

                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuItem.setVisible(false);
            }
        } else if (mApp.getmSelectedItems().size() > 1) {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);

                menuItem.setVisible(true);

                if (menuItem.getTitle().equals(mApp.getResources().getString(R.string.remove))) {
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                } else {
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                    menuItem.setVisible(false);
                }
            }
        } else {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                menuItem.setVisible(true);
            }
        }
    }

    public Dialog createAttentionDialog(final AppCompatActivity activivty,
                                        final String msg,
                                        final int modeDialog) {

        Dialog dialog = new Dialog(activivty);
        dialog.setTitle(R.string.attantion);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout linearLayout = new LinearLayout(activivty);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(activivty);
        textView.setText(msg);
        linearLayout.addView(textView);

        switch (modeDialog) {
            case WITH_TO_RETRY:
                Button btnToRetry = new Button(activivty);
                btnToRetry.setText(R.string.to_retry);
                btnToRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultAttentionDialog = true;
                    }
                });
                linearLayout.addView(btnToRetry);
                break;
        }
        dialog.setContentView(textView);
        return dialog;
    }
}
