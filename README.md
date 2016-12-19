# SideMenuDrawer
A fast small-code Android Activity with DrawerLayout implementation (with using [SideMenuDecor](https://github.com/kasenna/SideMenuDecor) library).

Supported API: 14+

##Set up<br>

Just add this to your build.gradle:
```java
repositories {
    mavenCentral()
}

dependencies {
    compile 'com.github.kasenna:SideMenuDrawerLibrary:1.0.4'
}
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
See [this](https://github.com/kasenna/SideMenuDrawer/tree/master/SideMenuDrawer-example)!
