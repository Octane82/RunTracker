package ru.sample.runtracker;

//import android.support.v4.app.Fragment;


import android.support.v4.app.Fragment;

/**
 * Вывод списка серий
 */
public class RunListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }
}
