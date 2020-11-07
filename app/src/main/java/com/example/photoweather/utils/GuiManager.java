package com.example.photoweather.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RestrictTo;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class GuiManager
{

    private final String TAG = this.getClass().getSimpleName();

    private static GuiManager gui_manger = new GuiManager();

    private FragmentManager fragmentManager;

    private int fragmentFrame;

    private int numberOfSubFragments;


    public static GuiManager getInstance() {
        return gui_manger;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(fragmentFrame);
    }

    public void setCurrentFragment(Fragment currentFragment, int slideInAnim, int slideOutAnim)
    {
        fragmentManager.beginTransaction()
                .setCustomAnimations(slideInAnim, slideOutAnim)
                .replace(fragmentFrame, currentFragment)
                .commitAllowingStateLoss();
    }


    public void setCurrentFragment(Fragment fragment, Bundle bundle, int slideInAnim, int slideOutAnim){
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .setCustomAnimations(slideInAnim, slideOutAnim)
                .replace(fragmentFrame, fragment)
                .commitAllowingStateLoss();
    }

    public void setSubFragment(Fragment fragment, int slideInAnim, int slideOutAnim)
    {
        fragmentManager.beginTransaction()
                .setCustomAnimations(slideInAnim, slideOutAnim)
                .replace(fragmentFrame, fragment)
                .addToBackStack(null)
                .commit();
        numberOfSubFragments++;
    }

    public void removeSubFragment()
    {
        if(numberOfSubFragments > 0)
        {
            numberOfSubFragments--;
            fragmentManager.popBackStack();
        }
    }

    public void refreshCurrentFragment()
    {
        fragmentManager.beginTransaction()
                .detach(getCurrentFragment())
                .attach(getCurrentFragment())
                .commitAllowingStateLoss();
    }

    public void refreshCurrentFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .detach(getCurrentFragment())
                .attach(fragment)
                .commitAllowingStateLoss();
    }


    public void setFragmentFrame(int fragmentFrame)
    {
        this.fragmentFrame = fragmentFrame;
    }

    public int getNumberOfSubFragments()
    {
        return numberOfSubFragments;
    }


}
