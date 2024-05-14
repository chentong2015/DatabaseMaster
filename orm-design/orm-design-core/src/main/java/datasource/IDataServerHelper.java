package datasource;

// DB Server的辅助类，提供关于DB Metadata相关的操作，实现跨数据库的兼容性
public interface IDataServerHelper {

    // 由于不同DB的列名称约束问题，namePattern的大小写需要根据underlying DB而确定
    boolean getColumn(String tablePattern, String namePattern);
}
