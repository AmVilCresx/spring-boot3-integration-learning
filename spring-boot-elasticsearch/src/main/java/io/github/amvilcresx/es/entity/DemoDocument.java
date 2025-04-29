package io.github.amvilcresx.es.entity;

import lombok.Data;
import org.dromara.easyes.annotation.HighLight;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.Analyzer;
import org.dromara.easyes.annotation.rely.FieldType;

@Data
@IndexName(value = "demo_document")
public class DemoDocument {

    /**
     * es中的唯一id
     */
    private String id;
    /**
     * 文档标题
     */
    @IndexField(fieldType = FieldType.TEXT, searchAnalyzer = Analyzer.IK_MAX_WORD, analyzer = Analyzer.IK_MAX_WORD)
    @HighLight
    private String title;

    /**
     * 文档内容
     */
    @HighLight(preTag = "<span style='color:red'>", postTag = "</span>")
    private String content;

    // 时间格式用Long类型，存储时间戳，避免时区问题 (试了好几种解决时区问题的办法，都没成功)
//    @IndexField(fieldType = FieldType.DATE, dateFormat = DateFormatPool.NORM_DATETIME_PATTERN)
    @IndexField(fieldType = FieldType.LONG)
//    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Long createDate;
}
