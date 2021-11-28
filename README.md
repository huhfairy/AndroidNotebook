# AndroidNotebook

### 基础功能实现

##### 一、增加时间戳

先修改notelist_item.xml，添加一个TextView显示时间戳。

```
<TextView
        android:id="@+id/text2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:singleLine="true"
        />
```

然后再修改NoteList.java, String[] PROJECTION和String[] dataColumns中添加时间数据。

```
private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
    };
```

```
String[] dataColumns = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
        } ;
```

最后在viewID中加上R.id.text2(时间戳的资源)。

```
int[] viewIDs = { android.R.id.text1,android.R.id.text2};//添加时间戳R.text2
```

实现效果：

![1638103272710.png](image/README/1638103272710.png)

##### 二、模糊查询

先修改list_options_menu.xml，在顶部添加搜索的按键

```
<item
        android:id="@+id/menu_search"
        android:icon="@android:drawable/ic_search_category_default"
        android:showAsAction="always"
        android:title="search">
```

再增加note_search.xml的布局和用于查询的NoteSearch.java的activity

```
<SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false"
        android:layout_alignParentTop="true">
    </SearchView>
    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>
```

最后在NoteList中的onOptionsItemSelected()方法中添加跳转逻辑。

```
case R.id.menu_search:
          Intent intent = new Intent(this, NoteSearch.class);
          this.startActivity(intent);
          return true;
```

实现效果：

![1638103649854.png](image/README/1638103649854.png)
