package mysql_master.logging;

// TODO: redo log的作用以及为什么性能比较好 ?
// 记录事务对数据库做了那些更改，一般运用在数据库的奔溃恢复
// 由于redo log持久化是顺序IO写，直接在文件中写，比随机IO性能高
public class InnoDBRedoLog {

    // Update t1 set c=10 where a=8;

    // ## 错误的步骤
    // 1. 先将要更新的page加载到Buffer Pool中，修改内存中的page
    // 2. 将修改的Page持久化回磁盘
    //    >> 着会导致每一次细微的更改都会造成16KB的IO交互，Page页中存储的行信息在磁盘中是不连续的，会造成随机IO
    // 3. 返回客户端，修改成功

    // ## 正确的执行步骤
    // 1. 先将要更新的page加载到Buffer Pool中，修改内存中的page
    // 2. 为update语句这个操作生成redo log日志对象
    // 3. redo log持久化
    //    >> 将修改的Page持久化回磁盘(随机IO，每行的数据存储位置不连续)
    // 4. 返回客户端，修改成功

    // TODO: 如果修改后的"脏页"还没来得及持久化，Mysql挂了，如何处理 ?
    // Mysql重启之后，从磁盘读取到的数据是没有被修改的，还没来得及持久化page
    // 再结合磁盘中持久化的redo log，整合成前面修改后的数据，自动恢复 !!

    // redo log为什么有两个文件ib_logfile0; ib_logfile1 (48M)?
    // 1. 当两个文件都写满之后，触发"Check Point"，找Buffer Pool中相关联的page持久到磁盘中
    // 2. 然后再添加新的redo log日志
    // 3. 可以调优文件大小和文件数量，但是重启会更久
}
