package io.github.amvilcresx.es.service;

import io.github.amvilcresx.es.entity.DemoDocument;
import io.github.amvilcresx.es.mapper.DemoDocumentMapper;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoDocumentService {

    @Autowired
    private DemoDocumentMapper documentMapper;

    public Boolean createIndex() {
        return documentMapper.createIndex();
    }

    public Integer insert() {
        // 2.初始化-> 新增数据
        DemoDocument document = new DemoDocument();
        document.setTitle("汉高祖");
        document.setContent("刘邦，开国志君");
//        document.setCreateDate(DateUtil.format(new Date(), DateFormatPool.NORM_DATETIME_PATTERN));
        document.setCreateDate(System.currentTimeMillis());
        return documentMapper.insert(document);
    }

    public List<DemoDocument> search() {
        // 3.查询出所有标题为老汉的文档列表
        LambdaEsQueryWrapper<DemoDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(DemoDocument::getTitle, "汉")
//        wrapper.gt("createDate", new Date(), ZoneId.of("GMT-8"), DateFormatPool.NORM_DATETIME_PATTERN);
                .gt(DemoDocument::getCreateDate, System.currentTimeMillis());
        return documentMapper.selectList(wrapper);
    }

    public Integer delete(String id) {
        LambdaEsQueryWrapper<DemoDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(DemoDocument::getId, id);
        return documentMapper.delete(wrapper);
    }

    public Integer insertBatch() {
        List<DemoDocument> demoDocuments = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            DemoDocument document = new DemoDocument();
            document.setTitle("title_" + i);
            document.setContent("第" + i + "个-----" + RandomUtil.randomString(9));
            document.setCreateDate(System.currentTimeMillis());
            demoDocuments.add(document);
        }

        return documentMapper.insertBatch(demoDocuments);
    }


    public EsPageInfo<DemoDocument> page() {
        LambdaEsQueryWrapper<DemoDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.match(DemoDocument::getTitle, "title1 title_2@title_3 2");
        wrapper.or().matchPhrase("content", "8个");
        wrapper.orderByDesc("createDate"); // 排序字段只能是【keyword】
        EsPageInfo<DemoDocument> documentPageInfo = documentMapper.pageQuery(wrapper, 1, 100);
        System.out.println(documentPageInfo);
        return documentPageInfo;
    }
}
