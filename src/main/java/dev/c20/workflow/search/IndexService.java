package dev.c20.workflow.search;

import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.store.jdbc.dialect.MySQLDialect;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import javax.sql.DataSource;
import java.io.IOException;

@Component
public class IndexService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    Analyzer keywordAnalyzer = new KeywordAnalyzer();
    Analyzer analyzer = new SimpleAnalyzer( Version.LUCENE_36);
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, analyzer);
    IndexWriter indexWriter = null;

    CustomIndexJdbcDirectory indexJdbcDirectory;
    DataSource dataSource;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        indexJdbcDirectory = new CustomIndexJdbcDirectory(dataSource,new MySQLDialect(),"WKF_STG_INDEX");
        logger.info("set indexJdbcDirectory");
    }

    private void addIndex( Long id, String data) {
        try {
            Document document = new Document();
            document.add(new Field("_id", id.toString(), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("data", data, Field.Store.YES, Field.Index.ANALYZED));
            indexWriter.addDocument(document);
        } catch( IOException ex ) {
            logger.error(ex);
        }
    }

    private void removeIndex( Long id) {
    }

}
