package com.example.ibs.services;

import com.example.ibs.entities.DocumentSide;
import com.example.ibs.repositories.DocumentSideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentSideService {

    private DocumentSideRepository documentSideRepository;

    @Autowired
    public void setDocumentSideRepository(DocumentSideRepository documentSideRepository) {
        this.documentSideRepository = documentSideRepository;
    }

    public void save(DocumentSide documentSide) {
        documentSideRepository.save(documentSide);
    }
}
