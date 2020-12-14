package dev.c20.workflow.search;

import com.github.lucene.store.jdbc.JdbcDirectory;
import com.github.lucene.store.jdbc.JdbcDirectorySettings;
import com.github.lucene.store.jdbc.dialect.Dialect;
import com.github.lucene.store.jdbc.dialect.OracleDialect;
import com.github.lucene.store.jdbc.support.JdbcTable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class CustomIndexJdbcDirectory extends JdbcDirectory {

    public CustomIndexJdbcDirectory() {
        super();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/AMT_STG?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("rn6jt2");

        final Dialect dialect = new OracleDialect();
        final String tableName = "WKF_STG_INDEX";
        initialize(dataSource, new JdbcTable(new JdbcDirectorySettings(), dialect, tableName));


    }
}
