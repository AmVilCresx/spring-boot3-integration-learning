package io.github.amvilcresx.langchain4j.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class LangChain4jConfiguration {

    @Autowired
    private RedisChatMemoryStore chatMemoryStore;

    /**
     * 向量模型
     */
    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore;

    /**
     * 会话记忆
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId) //实现会话隔离
                .chatMemoryStore(chatMemoryStore) // 会话持久化
                .maxMessages(20)
                .build();
    }

    // 数据切割、向量化、存储 Bean 对象
    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        // 构建向量数据库操作，基于内存存储
        //   InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        return EmbeddingStoreIngestor.builder()
                .embeddingStore(redisEmbeddingStore) // 向量存储数据库, 基于Redis
                .embeddingModel(embeddingModel)  // 指定向量模型
                // DocumentSplitters.recursive：第一个参数：每个片段最大容纳的字符数， 第二个参数：两个片段之间重叠字符的个数。
                .documentSplitter(DocumentSplitters.recursive(500, 100)) // 指定文档分割器
                .build();
    }

    // 构建向量数据库操作对象
    // @Bean
//    public EmbeddingStore<TextSegment> customEmbeddingStore() {
//        // 加载文件内容
////        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content/");
//        // 指定文档解析器 = PDF
//        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content/", new ApachePdfBoxDocumentParser());
//        // 构建文档分割器对象
//        // 第一个参数：每个片段最大容纳的字符数， 第二个参数：两个片段之间重叠字符的个数。
//        DocumentSplitter splitter = DocumentSplitters.recursive(500, 100);
//        // 构建向量数据库操作，基于内存存储
////        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
//        // 数据切割、向量化、存储
//        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
////                .embeddingStore(store)
//                .embeddingStore(redisEmbeddingStore)
//                .embeddingModel(embeddingModel) // 指定向量模型
//                .documentSplitter(splitter) // 指定文档分割器
//                .build();
//        ingestor.ingest(documents);
//
//        return redisEmbeddingStore;
//    }

    // 构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(/*EmbeddingStore<TextSegment> customEmbeddingStore*/) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .embeddingModel(embeddingModel) // 指定向量模型
                .maxResults(3)
                .minScore(0.5)
                .build();
    }


    @Bean
    public CommandLineRunner loadDocuments(EmbeddingStoreIngestor embeddingStoreIngestor) {
        return args -> {
            // 加载文件内容， 生成环境注意去重，【不能每次都进行切割存储操作】
            // List<Document> documents = ClassPathDocumentLoader.loadDocuments("content/");
            // 指定文档解析器 = PDF
            List<Document> documents = ClassPathDocumentLoader.loadDocuments("content/", new ApachePdfBoxDocumentParser());
            // 数据切割、向量化、存储
            embeddingStoreIngestor.ingest(documents);
        };
    }


    private List<Document> takeNotParseDocuments(String documentDirectory) {
        Path dirPath = Paths.get(documentDirectory);
        if (!Files.exists(dirPath)) {
            // 不是目录， 抛异常或者返回空list
            return Collections.emptyList();
        }
        // 从DB查询出已经解析过的文档记录，然后过滤掉
        List<String> excludesFileNames = new ArrayList<>();

        List<File> shouldParseFiles = FileUtil.loopFiles(documentDirectory, f -> excludesFileNames.add(FileNameUtil.getName(f)));

        if (CollectionUtils.isEmpty(shouldParseFiles)) {
            return Collections.emptyList();
        }

        List<Document> documents = new ArrayList<>();
        shouldParseFiles.forEach(item -> {
            Document doc = ClassPathDocumentLoader.loadDocument(item.getPath());
            documents.add(doc);
        });

        return documents;
    }
}
