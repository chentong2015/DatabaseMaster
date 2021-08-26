package mysql_master.storage_engine;

// InnoDB底层架构的设计, 三种链表
public class InnoDBArchitect {

    // Buffer Pool: InnoDB在内存中存储Page页的一块区域，默认是128M
    // Select * from t1 where a=8;
    // 1. 先判断在Buffer Pool(数组)中是否有查找页的数据(16KB)，如果没有，则从内存中加载(复制)page
    // 2. 对于新加载的Page，依次放在数组的相应位置(不连续的位置)

    // TODO: Buffer Pool空闲区域的管理
    // free链表: 基结点(做统计，记录信息) --> 控制块 --> 控制块 --> 控制块 ... 有多少个空闲区域，就有多少个空白的Page
    // 1. 对应新添加的page页，从链表的头控制块找到位置，然后删除头控制块
    // 2. 对于新空出来的page位置，从链表的末尾添加新的控制块，执行空白位置

    // Update t1 set c=10 where a=8;
    // 1. 先将要更新的page加载到Buffer Pool中，然后进行修改
    // 2. 将修改的Page持久化回磁盘

    // TODO: Buffer Pool持久化的设计
    // 什么时候将修改后的数据重新持久化回磁盘 ? Mysql后台有一个线程，定时持久化"脏页"回磁盘
    // 后台线程该如何判断是否是"脏页" ?      直接在flush链表上面找，持久化之后再删除控制块
    // flush链表: 基结点(做统计，记录信息) --> 控制块 --> 控制块 ... 有多少个"脏页"就有多少个控制块, 控制块顺序是按照修改时间的顺序

    // TODO: Buffer Pool中淘汰机制
    // 如果Buffer Pool占满了，如何放置新的page ? 采用淘汰机制(LRU: 最近最少使用淘汰机制)
    // LRU链表: 基结点(做统计，记录信息) --> 控制块 --> 控制块 ... 新加入的page应该加到链表头部，被用到的在最前面，尾部是可以删除的
    // LRU问题?
    // Select * from t1; 全表扫描会清除原来Buffer Pool中以有的热点数据给清空，重新添加查询需要的page
}
