package com.kadencelibrary.extension.context

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment




fun AppCompatActivity.replaceFragment(layout: Int, fragment: Fragment) {

    val transaction = this.getSupportFragmentManager().beginTransaction();
    transaction.replace(layout, fragment);
    transaction.commit();
}