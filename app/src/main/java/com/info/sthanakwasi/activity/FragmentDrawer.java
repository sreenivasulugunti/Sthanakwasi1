package com.info.sthanakwasi.activity;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.info.sthanakwasi.adapter.NavigationDrawerAdapter;
import com.info.sthanakwasi.R;
import com.info.sthanakwasi.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView,recyclerView1;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
//    private NavigationDrawerAdapter1 adapter1;
    private View containerView;
    private static String[] titles = null,titles1=null;
    private static TypedArray navMenuIcons;

    private FragmentDrawerListener drawerListener;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        data.add(new NavDrawerItem(titles[0], navMenuIcons.getResourceId(0, -1)));
        data.add(new NavDrawerItem(titles[1], navMenuIcons.getResourceId(1, -1)));
        data.add(new NavDrawerItem(titles[2], navMenuIcons.getResourceId(2, -1)));
        data.add(new NavDrawerItem(titles[3], navMenuIcons.getResourceId(3, -1)));
        data.add(new NavDrawerItem(titles[4], navMenuIcons.getResourceId(4, -1)));
        data.add(new NavDrawerItem(titles[5], navMenuIcons.getResourceId(5, -1)));
        data.add(new NavDrawerItem(titles[6], navMenuIcons.getResourceId(6, -1)));
        data.add(new NavDrawerItem(titles[7], navMenuIcons.getResourceId(7, -1)));
        data.add(new NavDrawerItem(titles[8], navMenuIcons.getResourceId(8, -1)));
        data.add(new NavDrawerItem(titles[9], navMenuIcons.getResourceId(9, -1)));
        data.add(new NavDrawerItem(titles[10], navMenuIcons.getResourceId(10, -1)));
        data.add(new NavDrawerItem(titles[11], navMenuIcons.getResourceId(11, -1)));
        data.add(new NavDrawerItem(titles[12], navMenuIcons.getResourceId(12, -1)));
        data.add(new NavDrawerItem(titles[13], navMenuIcons.getResourceId(13, -1)));

        return data;
    }

//    public static List<NavDrawerItem> getData1() {
//        List<NavDrawerItem> data = new ArrayList<>();
//
//
//        // preparing navigation drawer items
//        data.add(new NavDrawerItem(titles1[0]));
//        data.add(new NavDrawerItem(titles1[1]));
//        data.add(new NavDrawerItem(titles1[2]));
//        data.add(new NavDrawerItem(titles1[3]));
//        data.add(new NavDrawerItem(titles1[4]));
//
//        return data;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        titles1 = getActivity().getResources().getStringArray(R.array.nav_drawer_labels1);
        navMenuIcons = getActivity().getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
//        adapter1 = new NavigationDrawerAdapter1(getActivity(), getData1());
        recyclerView.setAdapter(adapter);
//        recyclerView1.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
