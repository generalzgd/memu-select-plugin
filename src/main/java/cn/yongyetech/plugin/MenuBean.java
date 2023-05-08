package cn.yongyetech.plugin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuBean {
    public int id = -1;
    public int system_id;
    public int pid;
    public String name;
    public String url;
    public String description;
    public int level;
    public int    sort;
    public int menu;
    public String icon;
    public int state;

    public MenuBean(ResultSet row) throws SQLException {
        id =  row.getInt("id");
        system_id = row.getInt("system_id");
        pid = row.getInt("pid");
        name = row.getString("name");
        url = row.getString("url");
        description = row.getString("description");
        level = row.getInt("level");
        sort = row.getInt("sort");
        menu = row.getInt("menu");
        icon = row.getString("icon");
        state = row.getInt("state");
    }
}
