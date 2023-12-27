package com.hibernate5.annotation.fetching.LazyCollection;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_company")
public class Company {

    @Id
    private Long id;

    // 在fetching集合数据的时候，使用定义的列字段来排序所有的元素
    // 延迟获取：只有在第一次访问数据的时候才会获取数据
    // 1. LazyCollectionOption.EXTRA behaves like any other FetchType.LAZY collection
    // 2. LazyCollectionOption.EXTRA works for ordered collections: 标注@OrderColumn或者Map(s)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @OrderColumn(name = "order_id")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Worker> workers = new ArrayList<>();

}
