package dev.c20.workflow.search;

import com.github.lucene.store.jdbc.dialect.MySQLDialect;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/*
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

 */

@Component
public class IndexService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    Analyzer keywordAnalyzer = new KeywordAnalyzer();
    Analyzer analyzer = new SimpleAnalyzer();
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
    IndexWriter indexWriter = null;

    CustomIndexJdbcDirectory indexJdbcDirectory;
    DataSource dataSource;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        indexJdbcDirectory = new CustomIndexJdbcDirectory(dataSource,new MySQLDialect(),"WKF_STG_INDEX");
        logger.info("set indexJdbcDirectory");
    }

    public List<Long> search(String queryStr, int maxHits ) throws IOException {
        /*
        indexJdbcDirectory = new CustomIndexJdbcDirectory(dataSource,new MySQLDialect(),"WKF_STG_INDEX");
        IndexSearcher searcher = new IndexSearcher(indexJdbcDirectory);
        QueryParser parser = new QueryParser(Version.LUCENE_30,"data", analyzer);
        Query query = parser.parse(queryStr);

        TopDocs topDocs = searcher.search(query,maxHits);
        ScoreDoc[] hits = topDocs.scoreDocs;

        List<Long> docsIds = new ArrayList<>();

        for( int i = 0; i < hits.length; i++ ) {
            int docId = hits[i].doc;
            Document document = searcher.doc(docId);
            logger.info( "Doc ID:" + docId + " data:" + document.get("data"));
            docsIds.add( Long.valueOf(document.get("_id")));
        }

        return docsIds;
        */
        return null;

    }

    public void delete( String queryStr )  throws IOException {
        /*
        indexJdbcDirectory = new CustomIndexJdbcDirectory(dataSource,new MySQLDialect(),"WKF_STG_INDEX");
        IndexSearcher searcher = new IndexSearcher(indexJdbcDirectory);
        QueryParser parser = new QueryParser(Version.LUCENE_30, "data", analyzer);
        Query query = parser.parse(queryStr);

        IndexReader indexReader = IndexReader.open(indexJdbcDirectory,false);

        TopDocs topDocs = searcher.search(query, 10000);
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (ScoreDoc doc: hits) {
            logger.info("Delete Doc ID:" + doc.doc);
            indexReader.deleteDocument( doc.doc );
        }
        */

    }

    /*
    https://lucene.apache.org/core/5_3_1/demo/index.html
     */
    public void add( Long id, String data) {
        try {
            indexJdbcDirectory = new CustomIndexJdbcDirectory(dataSource,new MySQLDialect(),"WKF_STG_INDEX");
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

            indexWriter = new IndexWriter( indexJdbcDirectory, iwc);
            Document document = new Document();
            document.add(new LongField("_id", id, Field.Store.YES));
            document.add(new StringField("data", data, Field.Store.YES));
            indexWriter.addDocument(document);
            indexWriter.close();
        } catch( IOException ex ) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }


}
