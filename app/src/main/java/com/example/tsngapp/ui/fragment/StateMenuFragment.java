package com.example.tsngapp.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tsngapp.R;
import com.example.tsngapp.ui.fragment.listener.BaseFragmentActionListener;
import com.example.tsngapp.ui.view.StateMenuItem;

/**
 * Fragment to hold menu of the house state fragment
 */
public class StateMenuFragment extends BaseFragment {
    /**
     * Listened specific to this fragment (defined on the bottom)
     */
    private FragmentActionListener actionListener;

    @Override
    protected void onCreateViewActions(@NonNull LayoutInflater inflater,
                                       @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {
        setupMenuActions();
        onAttachToParentFragment(getParentFragment());
    }

    private void onAttachToParentFragment(Fragment fragment) {
        try {
            actionListener = (FragmentActionListener)fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement FragmentActionListener");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_state_menu;
    }

    private void setupMenuActions() {
        rootView.findViewById(R.id.menu_item_current)
                .setOnClickListener(v -> actionListener.onMenuItemClicked(MenuItem.CURRENT));
        rootView.findViewById(R.id.menu_item_bed);
        rootView.findViewById(R.id.menu_item_door)
                .setOnClickListener(v -> actionListener.onMenuItemClicked(MenuItem.DOOR));
        rootView.findViewById(R.id.menu_item_camera);
        rootView.findViewById(R.id.menu_item_divisions);
        rootView.findViewById(R.id.menu_item_internal_temp);
        rootView.findViewById(R.id.menu_item_windows);
        rootView.findViewById(R.id.menu_item_lights);
        rootView.findViewById(R.id.menu_item_sos);
    }

    public enum MenuItem {
        CURRENT(R.string.label_current_state),
        BED(R.string.label_bed_state),
        DOOR(R.string.label_door_state),
        CAMERA(R.string.label_camera_state),
        DIVISIONS(R.string.label_division_state),
        INTERNAL_TEMP(R.string.label_internal_temp_state),
        WINDOWS(R.string.label_window_state),
        LIGHTS(R.string.label_lights_state),
        SOS(R.string.label_sos_state);

        private @StringRes int title;

        MenuItem(int title) {
            this.title = title;
        }

        public int getTitle() {
            return title;
        }
    }

    public interface FragmentActionListener {
        void onMenuItemClicked(MenuItem item);
    }
}

