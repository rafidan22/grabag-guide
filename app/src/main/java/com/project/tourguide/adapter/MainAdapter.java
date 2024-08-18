package com.project.tourguide.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.tourguide.fragment.WisataAlamFragment;
import com.project.tourguide.fragment.FunFragment;
import com.project.tourguide.fragment.KulinerFragment;
import com.project.tourguide.fragment.PasarFragment;
import com.project.tourguide.fragment.PenginapanFragment;
import com.project.tourguide.fragment.ReligiFragment;
import com.project.tourguide.fragment.ShopingFragment;
import com.project.tourguide.fragment.TamanFragment;
import com.project.tourguide.fragment.VisitFragment;


public class MainAdapter extends FragmentStateAdapter{


    public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new WisataAlamFragment();
            case 1 :
                return new FunFragment();
            case 2 :
                return new KulinerFragment();
            case 3 :
                return new PasarFragment();
            case 4 :
                return new PenginapanFragment();
            case 5 :
                return new ReligiFragment();
            case 6 :
                return new ShopingFragment();
            case 7 :
                return new TamanFragment();
            case 8 :
                return new VisitFragment();
            default:
                return new WisataAlamFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
