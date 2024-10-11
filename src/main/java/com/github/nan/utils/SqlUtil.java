package com.github.nan.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NanNan Wang
 */
public class SqlUtil {


    /**
     * 使用 JSqlParser 根据 SQL 和数据库类型解析 SQL 中的所有表名
     * @param sql SQL 语句
     * @return 表名列表
     * @throws Exception 解析异常
     */
    public static List<String> extractTableNames(String sql) {
        // 使用 JSqlParser 解析 SQL 语句
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }

        // 使用 TablesNamesFinder 提取表名
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(statement);
    }

    /**
     * 使用Druid解析SQL语句，获取表名
     *
     * @param sql
     * @param dbType
     * @return
     */
    public static List<String> getTableNamesFromSQL(String sql, String dbType) {
        List<String> tableNames = new ArrayList<>();

        // 解析 SQL 语句
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        for (SQLStatement stmt : stmtList) {
            if (stmt instanceof SQLSelectStatement) {
                SQLSelectStatement selectStatement = (SQLSelectStatement) stmt;
                SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) selectStatement.getSelect().getQuery();

                extractTableNames(queryBlock.getFrom(), tableNames);
            } else if (stmt instanceof SQLInsertStatement) {
                SQLInsertStatement insertStatement = (SQLInsertStatement) stmt;
                tableNames.add(insertStatement.getTableName().getSimpleName());
            } else if (stmt instanceof SQLUpdateStatement) {
                SQLUpdateStatement updateStatement = (SQLUpdateStatement) stmt;
                tableNames.add(updateStatement.getTableName().getSimpleName());
            } else if (stmt instanceof SQLDeleteStatement) {
                SQLDeleteStatement deleteStatement = (SQLDeleteStatement) stmt;
                tableNames.add(deleteStatement.getTableName().getSimpleName());
            }
        }
        return tableNames;
    }

    private static void extractTableNames(SQLTableSource tableSource, List<String> tableNames) {
        if (tableSource instanceof SQLExprTableSource) {
            // 如果是简单表名，直接添加
            SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
            tableNames.add(exprTableSource.getTableName());
        } else if (tableSource instanceof SQLJoinTableSource) {
            // 如果是 JOIN，递归获取左表和右表
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            extractTableNames(joinTableSource.getLeft(), tableNames);
            extractTableNames(joinTableSource.getRight(), tableNames);
        } else if (tableSource instanceof SQLSubqueryTableSource) {
            // 如果是子查询，递归处理子查询中的表
            SQLSelect subSelect = ((SQLSubqueryTableSource) tableSource).getSelect();
            extractTableNames(subSelect.getQueryBlock().getFrom(), tableNames);
        }
    }
}
