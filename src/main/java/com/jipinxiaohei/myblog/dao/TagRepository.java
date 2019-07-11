package com.jipinxiaohei.myblog.dao;

import com.jipinxiaohei.myblog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long>{
    Tag findByName(String name);
}
