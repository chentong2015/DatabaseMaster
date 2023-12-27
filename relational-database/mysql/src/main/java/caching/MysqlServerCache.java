package caching;

// MySql Server中具有"查询缓存"
// 1. 缓存命中: 通过哈希值引用缓存存放的引用列表，保证SQL语句一致才能命中
// 2. 缓存失效问题: 只要对某表进行更新，则那个表对应的缓存会被清空 > 在MySQL 8.0之后废弃
// 3. 缓存配置参数
public class MysqlServerCache {
}
