package com.rkasibhatla.noterservice.repository;

import com.rkasibhatla.noterservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    public Tag findTagByName(String name);
}
