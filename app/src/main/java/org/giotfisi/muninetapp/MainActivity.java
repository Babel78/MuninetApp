package org.giotfisi.muninetapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.giotfisi.muninetapp.Fragments.InicioFragment;
import org.giotfisi.muninetapp.Fragments.NosotrosFragment;
import org.giotfisi.muninetapp.Fragments.PublicarFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AHBottomNavigation bottomNavigation;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation=findViewById(R.id.bottom_navigation);
        frameLayout=findViewById(R.id.frame);
        InicializarBottomNavigation();
    }


    private void AddItemBottomNavigation(){
        AHBottomNavigationItem item1=new AHBottomNavigationItem("Publicar",R.drawable.ic_public);
        AHBottomNavigationItem item2=new AHBottomNavigationItem("Inicio",R.drawable.ic_home);
        AHBottomNavigationItem item3=new AHBottomNavigationItem("Conocenos",R.drawable.ic_nosotros);
        ArrayList<AHBottomNavigationItem> lista_items=new ArrayList<>();
        lista_items.add(item1);
        lista_items.add(item2);
        lista_items.add(item3);
        bottomNavigation.addItems(lista_items);
    }

    @SuppressLint("ResourceAsColor")
    private void InicializarBottomNavigation(){
        AddItemBottomNavigation();
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffb20e"));
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setInactiveColor(R.color.no_pressed_item_bottom_navigation);
        bottomNavigation.setCurrentItem(1);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,InicioFragment.newInstance());
        fragmentTransaction.commit();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                android.support.v4.app.Fragment selectFragment=null;;
                switch (position){
                    case 0:
                        selectFragment=PublicarFragment.newInstance();
                        break;
                    case 1:
                        selectFragment= InicioFragment.newInstance();
                        break;

                    case 2:
                        selectFragment= NosotrosFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                if(selectFragment!=null){
                    transaction.replace(R.id.frame,selectFragment);
                    transaction.commit();
                }

                return true;
            }
        });
        bottomNavigation.setBehaviorTranslationEnabled(true);
    }

}
