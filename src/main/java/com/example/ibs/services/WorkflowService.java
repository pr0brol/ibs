package com.example.ibs.services;

import com.example.ibs.entities.Document;
import com.example.ibs.entities.DocumentSide;
import com.example.ibs.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowService {

    private DocumentSideService docSideService;
    private DocumentService docService;
    private OrganizationService orgService;

    @Autowired
    public WorkflowService(DocumentSideService docSideService, DocumentService docService, OrganizationService orgService) {
        this.docSideService = docSideService;
        this.docService = docService;
        this.orgService = orgService;
    }

    public void addDocument(Long firstOrgId, String firstContent, Long secondOrgId, String secondContent) throws Exception {
        if (firstOrgId == secondOrgId) throw new Exception("Одна компания не может создать весь документ");

        Organization firstOrg = orgService.findById(firstOrgId).get();
        Organization secondOrg = orgService.findById(secondOrgId).get();

        DocumentSide first = new DocumentSide();
        first.setContent(firstContent);
        first.setOrganization(firstOrg);
        docSideService.save(first);

        DocumentSide second = new DocumentSide();
        second.setContent(secondContent);
        second.setOrganization(secondOrg);
        docSideService.save(second);

        Document document = new Document();
        document.setFirstSide(first);
        document.setSecondSide(second);

        docService.save(document);
    }

    public void removeDocument(Long orgId, Long docId) throws Exception {
        Document document = docService.findById(docId).get();
        if (orgId == document.getFirstSide().getOrganization().getId()) {
            if (document.getFirstSide().isSigned() || document.getSecondSide().isSigned()) {
                throw new Exception("Документ начали подписывать, его нельзя удалить");
            }
            docService.deleteById(docId);
        } else {
            throw new Exception("Документ создан другой компанией, вы не можете его удалить");
        }
    }

    @Transactional
    public void signDocument(Long orgId, Long docId) throws Exception {
        Document document = docService.findById(docId).get();
        if (document.getFirstSide().isSigned() && document.getSecondSide().isSigned()) {
            throw new Exception("Документ подписан. Повторная подпись невозможна");
        }
        if (orgId != document.getFirstSide().getOrganization().getId() &&
                orgId != document.getSecondSide().getOrganization().getId()) {
            throw new Exception("Вы не числитесь в данном документе");
        }
        if (document.getFirstSide().getOrganization().getId() == orgId) {
            if (document.getFirstSide().isSigned()) {
                throw new Exception("Вы уже подписали документ");
            } else {
                document.getFirstSide().setSigned(true);
            }
        }
        if (document.getSecondSide().getOrganization().getId() == orgId) {
            if (!document.getFirstSide().isSigned()) {
                throw new Exception("Документ ещё не подписан первой стороной");
            } else {
                document.getSecondSide().setSigned(true);
            }
        }
        docService.save(document);
    }


}
