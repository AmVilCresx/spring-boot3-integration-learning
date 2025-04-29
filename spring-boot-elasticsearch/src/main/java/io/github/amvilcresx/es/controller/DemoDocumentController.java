package io.github.amvilcresx.es.controller;

import io.github.amvilcresx.es.entity.DemoDocument;
import io.github.amvilcresx.es.service.DemoDocumentService;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doc")
public class DemoDocumentController {

    @Autowired
    private DemoDocumentService demoDocumentService;

    @GetMapping("/create")
    public Boolean createIndex() {
        return demoDocumentService.createIndex();
    }

    @GetMapping("/insert")
    public Integer insert() {
        return demoDocumentService.insert();
    }

    @GetMapping("/search")
    public List<DemoDocument> search() {
        return demoDocumentService.search();
    }

    @GetMapping("/delete")
    public Integer delete(String id) {
        return demoDocumentService.delete(id);
    }

    @GetMapping("/insertBatch")
    public Integer insertBatch() {
        return demoDocumentService.insertBatch();
    }

    @GetMapping("/page")
    public EsPageInfo<DemoDocument> page() {
        return demoDocumentService.page();
    }
}
