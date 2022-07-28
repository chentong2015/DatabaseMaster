package com.hibernate5.testing.query;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.hql.spi.QueryTranslator;

import java.util.Collections;
import java.util.Map;

// TODO. Hibernate提供了QueryTranslatorImpl转换器的实现
// 使用QueryTranslator将HQL转成SQL的标准格式, 以下为支持的版本 -> HqlTranslator v6版本
// - org.hibernate:hibernate-core:v5.6.9.Final
// - org.hibernate:hibernate-core:v5.4.24.Final
public class DemoQueryTranslator {

    private String hqlQuery;
    private SessionFactoryImplementor sessionFactory;

    public DemoQueryTranslator(String hqlQuery, SessionFactory sessionFactory) {
        this.hqlQuery = hqlQuery;
        this.sessionFactory = (SessionFactoryImplementor) sessionFactory;
    }

    public String toSQL() {
        return toSQL(null);
    }

    public String toSQL(Map<String, String> replacements) {
        QueryTranslator translator = new QueryTranslatorImpl(
                null, hqlQuery, Collections.EMPTY_MAP, sessionFactory);
        translator.compile(replacements, true);
        String sqlQuery = translator.getSQLString();
        String[][] columnNames = translator.getColumnNames();
        sqlQuery = removeColumnAliases(columnNames, sqlQuery);
        return sqlQuery;
    }

    // 移除掉列名称的别名
    private String removeColumnAliases(String[][] columnNamesArray, String sqlStatement) {
        if (columnNamesArray != null) {
            for (String[] columnNames : columnNamesArray) {
                if (columnNames != null) {
                    for (String columnName : columnNames) {
                        sqlStatement = sqlStatement.replaceFirst(" as " + columnName, "");
                    }
                }
            }
        }
        return sqlStatement;
    }
}
