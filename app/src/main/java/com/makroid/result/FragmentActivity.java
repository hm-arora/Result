package com.makroid.result;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makroid.result.informationclass.Model;
import com.makroid.result.informationclass.ListItem;
import com.makroid.result.adapters.ResultAdapter;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends Fragment implements OnMenuItemLongClickListener, OnMenuItemClickListener {
    private static final String JSON = "JSONOBJECT";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<ListItem> data;
    RecyclerView recView;
    ResultAdapter adapter;
    String message = ";";
    SharedPreferences settings;
    ContextMenuDialogFragment mMenuDialogFragment;
    JSONObject jsonObject;
    String JsonObjectString = "";
    FragmentManager fragmentManager;
    View view;
    boolean hasLoadedOnce = false;

    public FragmentActivity newInstance(String param1, String param2) {
        FragmentActivity fragment = new FragmentActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            settings = getActivity().getSharedPreferences(JSON, 0);
            message = getArguments().getString(ARG_PARAM1);
            JsonObjectString = settings.getString(getArguments().getString(ARG_PARAM2), "");
            try {
                jsonObject = new JSONObject(JsonObjectString).getJSONObject(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
        initMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_display, container, false);
        new loadData().execute();
        return view;
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_close);

        MenuObject send = new MenuObject("University Rank");
        send.setResource(R.drawable.ic_medal_blue);

        MenuObject like = new MenuObject("College Rank");
        like.setResource(R.drawable.ic_medal_blue);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        return menuObjects;
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu,inflater);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.overall_rank:
//                ArrayList<String> arrayList = new ArrayList<>();
//                arrayList.add(getArguments().getString(ARG_PARAM2));
//                arrayList.add(message);
//                arrayList.add("overall");
//                Intent intent = new Intent(getActivity(), ThirdActivity.class);
//                intent.putStringArrayListExtra("test", arrayList);
//                startActivity(intent);
////                getActivity().finish(); // therefore it never come after this from back button
//                return true;
//            case R.id.college_rank:
//                arrayList = new ArrayList<>();
//                arrayList.add(getArguments().getString(ARG_PARAM2));
//                arrayList.add(message);
//                arrayList.add("college");
//                intent = new Intent(getActivity(), ThirdActivity.class);
//                intent.putStringArrayListExtra("test", arrayList);
//                startActivity(intent);
////                getActivity().finish(); // therefore it never come after this from back button
//                return true;
//
//            case android.R.id.home:
//                getActivity().onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public class loadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                data = Model.getData(JsonObjectString,message); // used to get the data
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recView = (RecyclerView) view.findViewById(R.id.rec_list);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            adapter = new ResultAdapter(data, getActivity());
            recView.setLayoutManager(llm);
            recView.setAdapter(adapter);
            recView.setHasFixedSize(true);
            super.onPostExecute(aVoid);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.context, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        ArrayList<String> arrayList = new ArrayList<>();
        Intent intent = new Intent(getActivity(), ThirdActivity.class);
//        Toast.makeText(getActivity(), "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        if(position == 1){
                arrayList.add(getArguments().getString(ARG_PARAM2));
                arrayList.add(message);
                arrayList.add("overall");
                intent.putStringArrayListExtra("test", arrayList);
                startActivity(intent);
//                getActivity().finish(); // therefore it never come after this from back button
        }
        else if(position==2){
            arrayList = new ArrayList<>();
                arrayList.add(getArguments().getString(ARG_PARAM2));
                arrayList.add(message);
                arrayList.add("college");
                intent = new Intent(getActivity(), ThirdActivity.class);
                intent.putStringArrayListExtra("test", arrayList);
                startActivity(intent);
//                getActivity().finish(); // therefore it never come after this from back button
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(getActivity(), "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
