package dev.c20.workflow.storage.services;


import dev.c20.workflow.storage.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    StorageRepository storageRepository;


}
