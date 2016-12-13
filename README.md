# SideMenuDrawer
A fast small-code Android Activity with DrawerLayout implementation.

Supported API: 14-24

##Set up<br>

1.Create folder SideMenuDrawerModule inside your Android Studio project root folder, then put there all github files (better to exclude example SideMenuDrawerExample project folder).

2.Add ':SideMenuDrawerModule' in settings.gradle file:
```java
include ':app', ':SideMenuDrawerModule'
```
3.Add this line in build.gradle file:
```java
compile project(':SideMenuDrawerModule')
```

##Usage

1.Do extends:
```java
public class MainActivity extends SideMenuDrawer {
```
2.Override init(), don't override OnCreate():
```java
@Override
    public void init(Bundle savedInstanceState) {
```
3.Put in init() all you need to implement (below)

4.Add menu item:
```java
addSideMenuItem(Fragment fragment, int res_id, String item_text);
```
5.Add bottom menu item:
```java
addSideMenuBottomItem(Fragment fragment, int res_id, String item_text);
```
6.Set menu title:
```java
setMenuTitle(String title_text);
```
7.Set menu subtitle:
```java
setMenuSubTitle(String subtitle_text);
```
8.Finally, do init:
```java
initSideMenu();
```

##How about example?
See [this](https://github.com/kasenna/SideMenuDrawer/tree/master/SideMenuDrawerExample)!
