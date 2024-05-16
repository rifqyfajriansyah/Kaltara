package com.example.roadmanagement.kaltara.FormTab.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.helper.Session;

public class AnimationTab {

    public static int getPosisiAtribut(String tipe){

            int value = 0;

            switch (tipe){

                case "Segment" : value = 0;
                    break;

                case "Median" : value = 1;
                    break;

                case "Lahan" : value = 2;
                    break;

                case "Saluran" : value = 3;
                    break;

                case "Bahu" : value = 4;
                    break;

                case "Lane" : value = 5;
                    break;

                case "Perkerasan" : value = 6;
                    break;

                case "SPD" : value = 7;
                    break;

                case "SPL" : value = 8;
                    break;

                case "Inlet" : value = 9;
                    break;

                case "Outlet" : value = 10;
                    break;

                case "Gorong" : value = 11;
                    break;

            }


            return value;

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setFormShow(LinearLayoutCompat line,  boolean param, ImageView iconExpand, ViewGroup parent){

        setAnimation(line, param, line.getId(), Gravity.TOP, parent);
        iconExpand.setRotation(param? 180 : 0);

    }


    public static  void setFormEdit(LinearLayoutCompat line,  boolean param, ImageView iconEdit, ImageView iconDone, ImageView iconCancel, ImageView iconExpand, ViewGroup parent){

        if(param){

            iconEdit.setVisibility(View.GONE);
            iconDone.setVisibility(View.VISIBLE);
            iconCancel.setVisibility(View.VISIBLE);
            iconExpand.setVisibility(View.VISIBLE);

        }else{

            iconEdit.setVisibility(View.VISIBLE);
            iconDone.setVisibility(View.GONE);
            iconCancel.setVisibility(View.GONE);
            iconExpand.setVisibility(View.GONE);
        }

        Session session = new Session(line.getContext());
        session.saveSPBoolean(Session.FLAG_FORM, param);

        //Toast.makeText(parent.getContext(), String.valueOf(param), Toast.LENGTH_SHORT).show();

        setAnimation(line, param, line.getId(), Gravity.TOP, parent);

    }

    private static void setAnimation(LinearLayoutCompat line, boolean param, int id, int gravity, ViewGroup parent){

        Transition transition = new Slide(gravity);
        transition.setDuration(300);
        transition.addTarget(id);

        TransitionManager.beginDelayedTransition(parent, transition);
        line.setVisibility(param ? View.VISIBLE : View.GONE);
    }

}
