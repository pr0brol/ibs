package com.example.ibs;

import com.example.ibs.services.DocumentService;
import com.example.ibs.services.WorkflowService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbsApplicationTests {

    private static WorkflowService service;
    private static DocumentService docService;

    @Autowired
    public IbsApplicationTests(WorkflowService service, DocumentService docService) {
        this.service = service;
        this.docService = docService;
    }

    @Test
    public void addDocumentTest() {
        for (long i = 1; i <= 10; i++) {
            Long id1 = i;
            Long id2 = 11 - i;
            try {
                service.addDocument(id1, "content1", id2, "content2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(10, docService.findAll().size());

        try {
            service.addDocument(5L, "content1", 5L, "content2");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Одна компания не может создать весь документ"));
        }
    }

    @Test
    public void signTest() {
        try {
            service.signDocument(1L, 1L);
        } catch (Exception e) {
            Assert.assertNull(e.getMessage());
        }
        try {
            service.signDocument(1L, 1L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Вы уже подписали документ"));
        }
        try {
            service.signDocument(10L, 1L);
        } catch (Exception e) {
            Assert.assertNull(e.getMessage());
        }
        try {
            service.signDocument(10L, 1L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Документ подписан. Повторная подпись невозможна"));
        }
        try {
            service.signDocument(5L, 2L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Вы не числитесь в данном документе"));
        }
        try {
            service.signDocument(9L, 2L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Документ ещё не подписан первой стороной"));
        }


    }

    @Test
    public void removeDocumentTest() {
        try {
            service.removeDocument(9L, 2L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Документ создан другой компанией, вы не можете его удалить"));
        }
        try {
            service.removeDocument(1L, 1L);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("Документ начали подписывать, его нельзя удалить"));
        }
        try {
            service.removeDocument(2L, 2L);
        } catch (Exception e) {
            Assert.assertNull(e.getMessage());
        }
    }

}
