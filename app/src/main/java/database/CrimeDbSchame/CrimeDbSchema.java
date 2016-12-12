package database.CrimeDbSchame;

/**
 * Created by Administrator on 2016/12/10.
 * 在java中安全引用
 */

public class CrimeDbSchema {
    //定义一个内部类来描述数据表的String常量
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        //定义数据表字段
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }

}
