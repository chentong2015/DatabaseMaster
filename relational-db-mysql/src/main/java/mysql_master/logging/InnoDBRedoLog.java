package mysql_master.logging;

public class InnoDBRedoLog {

    // TODO: redo log的作用以及为什么性能比较好 ?
    // 记录事务对数据库做了那些更改，一般运用在数据库的奔溃恢复
    // 由于redo log持久化是顺序IO写，直接在文件中写，因此比随机IO性能高

    // 如果修改后的"脏页"还没来得及持久化，Mysql挂了，如何处理 ?
    //    Update t1 set c=10 where a=8;
    //    1. 先将要更新的page加载到Buffer Pool中，修改内存中的page
    //    2. 为update语句这个操作生成redo log日志对象
    //    3. redo log持久化
    //       >> 将修改的Page持久化回磁盘(随机IO，每行的数据存储位置不连续)
    //    4. 返回客户端，修改成功
    // Mysql重启之后，从磁盘读取到的数据是没有被修改的，还没来得及持久化page
    // 再结合磁盘中持久化的redo log，整合成前面修改后的数据，自动恢复 !!

    // Update语句没有选择的执行流程：
    // 1. 先将要更新的page加载到Buffer Pool中，修改内存中的page
    // 2. 将修改的Page持久化回磁盘
    // 3. 返回客户端，修改成功

    // redo log为什么有两个文件ib_logfile0; ib_logfile1 (48M)?   ==> 调优文件大小和文件数量，但是重启会更久
    // 1. 当两个文件都写满之后，触发"Check Point"，找Buffer Pool中相关联的page持久到磁盘中
    // 2. 然后再添加新的redo log日志

    // TODO: 当事务commit提交的时候，MySQL才会做redo log持久化处理 !!
    //       在内存中对应的存储区域是Log Buffer
    // begin;
    // update ...  在过程中不需要持久化
    // ...         如果是rollback回滚了，前面的持久化就没用了
    // commit;
}
