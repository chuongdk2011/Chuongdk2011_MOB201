package com.example.chuongdkph26546_mob201_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.chuongdkph26546_mob201_asm.Fragment.MenuFragment;
import com.example.chuongdkph26546_mob201_asm.Fragment.MusicFragment;
import com.example.chuongdkph26546_mob201_asm.Fragment.ReadFragment;
import com.example.chuongdkph26546_mob201_asm.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        MenuFragment menuFragment = new MenuFragment();
        MusicFragment musicFragment = new MusicFragment();
        UserFragment userFragment = new UserFragment();

        fragmentManager.beginTransaction().add(R.id.container_frag,menuFragment).commit();

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_Menu:
                        replaceFragment(menuFragment.newInstance());
                        break;

                    case R.id.nav_music:
                        replaceFragment(musicFragment.newInstance());
                        break;
                    case R.id.nav_user:
                        replaceFragment(userFragment.newInstance());
                        break;
                    default:
                        replaceFragment(menuFragment.newInstance());
                        break;
                }

                return true;
            }
        });

    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frag,fragment);
        transaction.commit();
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CustomItem customItem) {
        if (customItem.getMessage().equals("updateViews")) {
            reloadFragment("updateViews");
        }


    }
    protected void reloadFragment(String TAG){
        // Reload current fragment
        Fragment frg = null;
        frg = this.getSupportFragmentManager().findFragmentByTag(TAG);
        FragmentTransaction ft =this.getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}