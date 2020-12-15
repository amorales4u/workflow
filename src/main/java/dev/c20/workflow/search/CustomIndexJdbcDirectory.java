package dev.c20.workflow.search;

import org.apache.lucene.store.jdbc.dialect.Dialect;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectorySettings;
import org.apache.lucene.store.jdbc.JdbcStoreException;
import org.apache.lucene.store.jdbc.support.JdbcTable;

public class CustomIndexJdbcDirectory extends JdbcDirectory {

    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource
     *            the data source
     * @param dialect
     *            the dialect
     * @param settings
     *            the settings
     * @param tableName
     *            the table name
     */
    public CustomIndexJdbcDirectory(DataSource dataSource, Dialect dialect, JdbcDirectorySettings settings, String tableName) {
        super(dataSource, dialect, settings, tableName);
    }

    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource the data source
     * @param dialect the dialect
     * @param tableName the table name
     */
    public CustomIndexJdbcDirectory(DataSource dataSource, Dialect dialect, String tableName) {
        super(dataSource, dialect, tableName);
    }

    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource the data source
     * @param settings the settings
     * @param tableName the table name
     * @throws JdbcStoreException the jdbc store exception
     */
    public CustomIndexJdbcDirectory(DataSource dataSource, JdbcDirectorySettings settings, String tableName) throws JdbcStoreException {
        super(dataSource, settings, tableName);
    }

    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource the data source
     * @param table the table
     */
    public CustomIndexJdbcDirectory(DataSource dataSource, JdbcTable table) {
        super(dataSource, table);
    }

    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource the data source
     * @param tableName the table name
     * @throws JdbcStoreException the jdbc store exception
     */
    public CustomIndexJdbcDirectory(DataSource dataSource, String tableName) throws JdbcStoreException {
        super(dataSource, tableName);
    }

    /**
     * (non-Javadoc).
     *
     * @return the string[]
     * @throws IOException Signals that an I/O exception has occurred.
     * @see org.apache.lucene.store.Directory#listAll()
     */
    //@Override
    public String[] listAll() throws IOException {
        return super.list();
    }
}
