package dev.c20.workflow.storage.services.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class StorageRequest<T> {

    String path;
    T request;

}
