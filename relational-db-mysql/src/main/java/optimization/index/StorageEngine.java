package optimization.index;

public class StorageEngine {

    // TODO: 不同的"存储引擎"底层索引的存储结构有所区别
    // 数据库往往是一个比较丰富完整的系统, 提供了SQL查询语言，事务和水平扩展等支持，底层往往会使用某种存储引擎
    // MySQL数据库的数据实际存储在安装目录下/data/数据库名称/...(一个库对于一个文件夹)
    // 1. 存储引擎则是小而精, 纯粹专注于单机的读/写/存储 ===> 形容数据库表的 !!
    // 2. MySQL底层的四种存储引擎
    //    MyISAM存储引擎：一个表的存储文件: db.frm(存表结构)，db.MYD(存数据)，db.MYI(存索引, B+树的组织结构)
    //                  Select * from where col1=30;
    //                  > 如果col1是索引，则先从.MYI文件中找到指定行的磁盘文件地址
    //                  > 使用查找到的文件地址，在db.MYD文件中找到指定的数据内容
    //    InnoDB存储引擎: 标配，支撑事务，行锁...
    //                  一个表的存储文件: db.frm(存表结构), db.ibd(同时存数据+索引)
    //                  直接用B+树来组织数据，直接在叶子结点位置存储data数据，而不是磁盘地址 !!
    //                  TODO: 什么是聚集索引(聚簇索引)? 叶子结点包含完整的数据结构
    //                        什么是非聚集索引(非聚簇索引, 稀疏索引)? 叶子结点存储的是树对应的磁盘文件地址，Index和data是分开在两个文件中
    //    MEMORY存储引擎
    //    ARCHIVE存储引擎

    // TODO: 为什么建议InnoDB表必须建主键，并且推荐使用整型的自增主键 ? ===> 避免使用UUID(非整型，非自增)
    // 1. 因为数据需要通过B+树来组织数据来存储，如果没有设置主键，则会找一列数据不重复的列来组织，如果都没有找到则会建一列隐藏列RowId !!
    //    所设置的primary key就是B+树中的key索引
    // 2. 整型: 减少存储的空间，同时比字符串大小比较效率更高
    // 3. 自增: 为了保证有序性，如果要在一个结点已经插满的情况下(>16KB)，插入中间值会导致结点分裂或者再左平衡，效率降低
    //         如果自增，永远在后面添加元素，不会导致结点分裂 !!
}
