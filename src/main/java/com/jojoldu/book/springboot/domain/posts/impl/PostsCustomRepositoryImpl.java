package com.jojoldu.book.springboot.domain.posts.impl;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsCustomRepository;
import com.jojoldu.book.springboot.domain.posts.QPosts;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostsCustomRepositoryImpl implements PostsCustomRepository {
    JPAQueryFactory queryFactory;

    public PostsCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    QPosts qPosts = new QPosts("posts");

    @Override
    public List<Posts> findAllDesc() {
        return queryFactory
                .selectFrom(qPosts)
                .orderBy(qPosts.id.desc())
                .fetch();
    }
}
