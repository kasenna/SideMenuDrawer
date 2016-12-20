package karpen74us.sidemenudrawer;
/*
SideMenuDrawer.java
Copyright (C) 2016  Max Karpenkov

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import karpen74us.sidemenudecor.RippleButton;


public abstract class SideMenuDrawer extends AppCompatActivity {

    Context mcontext;
    CharSequence mTitle;
    ListView mDrawerListView;
    List<ItemObject> mitemList = new ArrayList<>();
    ItemListAdapter madapter;
    ActionBarDrawerToggle mDrawerToggle = null;
    DrawerLayout mDrawerLayout;
    RelativeLayout mLeftDrawerMain;
    RippleButton mSideMenuBottomItem;
    TextView mMenuTitle, mMenuSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mcontext = getApplicationContext();
        init(savedInstanceState);
    }

    public abstract void init(Bundle savedInstanceState);

    public void setMenuTitle(String txt){
        mMenuTitle = (TextView) findViewById(R.id.drawer_title);
        mMenuTitle.setText(txt);
    }

    public void setMenuSubTitle(String txt){
        mMenuSubTitle = (TextView) findViewById(R.id.drawer_subtitle);
        mMenuSubTitle.setText(txt);
    }

    public void initSideMenu(){
        madapter = new ItemListAdapter(mcontext, R.layout.drawer_list_item, mitemList);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer_items);
        mDrawerListView.setAdapter(madapter);
        mLeftDrawerMain = (RelativeLayout) findViewById(R.id.left_drawer_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, 0, 0) {
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(mLeftDrawerMain);
                }
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(mLeftDrawerMain);
                }
            };
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        setFirstSideMenuItem();
    }

    public void addSideMenuItem(Fragment fr, int res, String txt){
        mitemList.add(new ItemObject(fr, res,"   "+txt));
    }

    public void addSideMenuBottomItem(final Fragment fr, int res, String txt){
        final String item_text = "   "+txt;
        Typeface mainTypeface = Typeface.createFromAsset(mcontext.getAssets(), "font/RobotoCondensed-Light.ttf");
        mSideMenuBottomItem = (RippleButton) findViewById(R.id.side_menu_item_bottom);
        mSideMenuBottomItem.setTypeface(mainTypeface);
        mSideMenuBottomItem.setRippleColor(Color.parseColor("#0f3d4c"));
        mSideMenuBottomItem.setRippleDuration(150);
        mSideMenuBottomItem.setEnabled(true);
        mSideMenuBottomItem.setClickable(true);
        mSideMenuBottomItem.setLongClickable(true);
        mSideMenuBottomItem.setText(item_text);
        Drawable img_drw = ContextCompat.getDrawable(mcontext, res);
        img_drw.setAlpha(70);
        mSideMenuBottomItem.setCompoundDrawablesWithIntrinsicBounds(img_drw,null,null,null);
        mSideMenuBottomItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fr!=null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_place, fr).commitAllowingStateLoss();
                    setTitle(item_text.substring(3));
                    mDrawerLayout.closeDrawer(mLeftDrawerMain,true);
                }
            }
        });
    }

    public class ItemListAdapter extends ArrayAdapter<ItemObject> {

        Context context;
        int layoutResourceId;
        List<ItemObject> data = null;

        ItemListAdapter(Context context, int layoutResourceId, List<ItemObject> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        public int getCount() {
            return data.size();
        }

        public long getItemId(int position) {
            return position;
        }

        @Override @NonNull
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            Holder holder;
            Typeface mainTypeface = Typeface.createFromAsset(context.getAssets(), "font/RobotoCondensed-Light.ttf");
            if (row == null) {
                row = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutResourceId, parent, false);
                holder = new Holder();
                holder.itm_btn = (RippleButton) row.findViewById(R.id.item_button);
                row.setTag(holder);
            } else {
                holder = (Holder) row.getTag();
            }
            ItemObject cur_item = data.get(position);
            holder.itm_btn.setTypeface(mainTypeface);
            holder.itm_btn.setRippleColor(Color.parseColor("#8b8682"));
            holder.itm_btn.setRippleDuration(150);
            holder.itm_btn.setEnabled(true);
            holder.itm_btn.setClickable(true);
            holder.itm_btn.setLongClickable(true);
            holder.itm_btn.setText(cur_item.item_text);
            Drawable img_drw = ContextCompat.getDrawable(context, cur_item.resource_id);
            img_drw.setAlpha(70);
            holder.itm_btn.setCompoundDrawablesWithIntrinsicBounds(img_drw,null,null,null);
            holder.itm_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selectSideMenuItem(position);
                }
            });
            return row;
        }

        class Holder {
            RippleButton itm_btn;
        }

    }

    @SuppressWarnings("all")
    public class ItemObject extends Object{
        Fragment item_fragment;
        int resource_id;
        String item_text;
        public ItemObject(Fragment fr, int res, String txt){
            this.item_fragment = fr;
            this.resource_id = res;
            this.item_text = txt;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle!=null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    if (mDrawerLayout.isDrawerOpen(mLeftDrawerMain)){
                        mDrawerLayout.closeDrawer(mLeftDrawerMain,true);
                    } else {
                        mDrawerLayout.openDrawer(mLeftDrawerMain, false);
                    }
                    return true;
                default:
                    return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle!=null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle!=null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTitle);
        }else {
            setTitle(mTitle);
        }
    }

    public void selectSideMenuItem(int position){
        ItemObject req_item = mitemList.get(position);
        Fragment req_fragment = req_item.item_fragment;
        if (req_fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_place, req_fragment).commitAllowingStateLoss();
            setTitle(req_item.item_text.substring(3));
            mDrawerLayout.closeDrawer(mLeftDrawerMain,true);
        }
    }

    public void setFirstSideMenuItem(){
        ItemObject first_item = mitemList.get(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_place, first_item.item_fragment).commitAllowingStateLoss();
        setTitle(first_item.item_text.substring(3));
    }

    public CharSequence getCurrentItemName(){
        return mTitle;
    }

    public void setItemFragmentByItemName(final String item_name){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String actual_item_name = "   "+item_name;
                for (int i=0;i<mitemList.size();++i){
                    ItemObject cur_item = mitemList.get(i);
                    if (cur_item.item_text.equals(actual_item_name)){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_place, cur_item.item_fragment).commitAllowingStateLoss();
                        setTitle(cur_item.item_text.substring(3));
                        i = mitemList.size();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mTitle.equals(mitemList.get(0).item_text.substring(3))) {
            super.onBackPressed();
        } else {
            setFirstSideMenuItem();
        }
    }

}
