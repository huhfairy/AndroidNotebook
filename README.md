# AndroidNotebook

### 基础功能实现

##### 一、增加时间戳

先修改notelist_item.xml，添加一个TextView显示时间戳。

然后再修改NoteList.java, String[] PROJECTION和String[] dataColumns中添加时间数据。

最后在viewID中加上R.id.text2(时间戳的资源)。

实现效果：

![1638103272710.png](image/README/1638103272710.png)

##### 二、模糊查询

先修改list_options_menu.xml，在顶部添加搜索的按键

再增加note_search.xml的布局和用于查询的NoteSearch.java的activity

最后在NoteList中的onOptionsItemSelected()方法中添加跳转逻辑。

实现效果：

![1638103649854.png](image/README/1638103649854.png)
