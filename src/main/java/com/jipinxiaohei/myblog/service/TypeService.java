package com.jipinxiaohei.myblog.service;

import com.jipinxiaohei.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TypeService {

    Type saveType(Type type);

    Optional<Type> getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
