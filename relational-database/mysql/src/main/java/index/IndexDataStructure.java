package index;

// MySQL索引底层选择的数据结构 B+Tree
public class IndexDataStructure {

    // 1. 二叉树: 结点存储<key, value> value存储的是磁盘文件地址
    //           不适用场景: 单边增长的二叉树，退化成链表
    // 2. 红黑树: 平衡二叉树，自旋平衡
    //           不适用场景: 大数据量，树的高度不可控，找叶子结点不高效，查找一次和磁盘做一次IO交互
    // 3. Hash表: hash(key) --> Table(Entry<key,value>) 数组加链表存储的存储方式，value存储磁盘文件地址
    //            对索引key进行一次hash计算便可以定位出数据的位置，速度及其高效
    //            > 不支持范围查找, 仅能满足"="或"in"
    //            > 存在hash冲突问题
    // 4. B-Tree: 控制树的高度，存储大量的数据
    //            一个结点上存储更多的元素，其中每个node存(key,value)
    //            所有索引元素不重复，结点中的数据索引从左到右递增
    //            由于在叶子结点之间没有指针链接，因此不支持范围的查找
    //                               (node, node, node, node, node ...)
    //            (node, node, node, node, node ...)      (node, node, node, node, node ...)
    //           .....                          .....                  (node, node, node, node, node ...)
    //
    // 5. B+Tree: 1. 上层的key构成的一组结点，从左到右依次递增，左子树都小，右子树都大
    //            2. 提取叶子结点的一组元素中的第一个值(冗余值)，构成第二层
    //            3. 叶子结点具有指针的连接，从左到右递增，支持对范围的查找
    //               叶子结点包含整张表的所有索引元素，data是索引所在行的磁盘文件地址
    //    B+Tree如何查找?
    //            为了提高查询的效率，一般页(根结点)是常驻内存的
    //            1. 首先将整个root根结点load到内存中RAM(访问延时约100ns)，使用二分法查找(根结点中key是排序的)
    //            2. 再加载左结点到内存中，二分法查找
    //            3. 再加载第三层的叶子结点数据
    //    B+Tree为什么能使树的高度可控?
    //            1. 默认给Page页分配的大小是16KB
    //               查询语句：Show global status like 'Innodb_page_size'; 查找页的大小设置
    //               8bits + 6bits@address + 8bits + 6bits@address ....  总共可以存1100个key值
    //            2. 第二层单个页可以放1170个key值
    //            3. 第三层单个页可以放16个(key, value)组合值
    //               全部放满叶子结点：1100 * 1100 * 16 = 千万级的key值 ===> 再往上则需要做分库分表 !!

    // 0. 主键索引 / 唯一索引(unique列)
    // 1. 二级索引: 构建非主键索引，在叶子结点上存储的是主键索引
    // 2. 联合索引: 构建组合字段的索引，按照字段逐个比对进行排序
    // 3. 最左前缀法则: 一种限制
    //    要使用联合索引，必须从前面的字段开始使用，不能跳过前面的直接使用后面的字段
    //    因为联合索引的B+树的构建是根据逐个字段排号序的，后面的单个字段不是排好序的
    //
    // 4. 为什么非主键索引结构(二级索引)，叶子结点存储的是主键值?
    //    TODO: 存在一致性(数据更新困难)和节省存储空间问题
    //    对于创建的非主键索引，在叶子结点上如果存储的是表的实际数据，则会和主键索引生成的B+树中的叶子结点上的数据一致，存在数据的冗余 !!
    //    因此在叶子结点存储的是主键值，通过主键去主键索引B+树上查询完整的一行数据信息 ==> "回表"到聚集索引上去查询
    // 5. TODO: 锁失效的一般问题 ? MySQL会做出最优性能的选择，走索引 & 全表扫描
    //      即使在没有添加where的情况下，也可能从已有的索引中找出能走的索引，并且找出效率最高的那个索引(比如走二级索引，叶子页包含更多)
    //      在联合索引的场景下，如果走索引所执行的性能更低，没有办法直接在B+树中拿到数据，造成回表操作
    //    Select * from b > 1;    All
    //    Select b from b > 1;    Index  利用B+树存储结构中的数据，判断是否有回表造成的性能损失
    //    Select * from b = 1;    Index
    //    Select a from b > 1;    Index
    //    Select b from table1;   All
    //
    //    Create index idx_table1_e on table1(e);        e是varchar(20)数据类型
    //    Select * from table1 where e = 1;              ALL
    //    Select * from table1 where e = '1';            Index
    //    Select * from table1 where a = 1;              Index
    //    Select * from table1 where a = '1';            Index 可以在char和int直接直接大小比较，因此会走索引
    //    Select * FROM t_demo_index where a = 'chen';   ALL   如果类型之间需要比较强的转换，则不会走索引
}
