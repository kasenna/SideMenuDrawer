package karpen74us.sidemenudrawerexample;
/*
MainActivity.java
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import karpen74us.sidemenudrawer.SideMenuDrawer;


public class MainActivity extends SideMenuDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
        addSideMenuItem(new Section1(), R.drawable.ic_settings_black_24dp, "Section1");
        addSideMenuItem(new Section2(), R.drawable.ic_settings_black_24dp, "Section2");
        addSideMenuItem(new Section3(), R.drawable.ic_settings_black_24dp, "Section3");
        addSideMenuItem(new Section4(), R.drawable.ic_settings_black_24dp, "Section4");
        addSideMenuBottomItem(new ExitSection(), R.drawable.ic_settings_black_24dp, "ExitSection");
        setMenuTitle("test@testdomain.com");
        setMenuSubTitle("TestApp, version 1");
        initSideMenu();
    }

    public static class Section1 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View root_view = inflater.inflate(R.layout.fragment_empty, container, false);
            TextView mTV = (TextView) root_view.findViewById(R.id.fragment_name);
            mTV.setText("Section1");
            return root_view;
        }
    }

    public static class Section2 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View root_view = inflater.inflate(R.layout.fragment_empty, container, false);
            TextView mTV = (TextView) root_view.findViewById(R.id.fragment_name);
            mTV.setText("Section2");
            return root_view;
        }
    }

    public static class Section3 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View root_view = inflater.inflate(R.layout.fragment_empty, container, false);
            TextView mTV = (TextView) root_view.findViewById(R.id.fragment_name);
            mTV.setText("Section3");
            return root_view;
        }
    }

    public static class Section4 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View root_view = inflater.inflate(R.layout.fragment_empty, container, false);
            TextView mTV = (TextView) root_view.findViewById(R.id.fragment_name);
            mTV.setText("Section4");
            return root_view;
        }
    }

    public static class ExitSection extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View root_view = inflater.inflate(R.layout.fragment_empty, container, false);
            TextView mTV = (TextView) root_view.findViewById(R.id.fragment_name);
            mTV.setText("ExitSection");
            return root_view;
        }
    }

}
