package ru.sample.runtracker;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Абстрактный класс служит родителем для других фрагментов т.к. код практически повторяется
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    // Абстрактный метод для переопределения
    // Используется для создания экземпляра фрагмента
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();   // Можно и getFragmentManager() - но не будет совместимости

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }


}
