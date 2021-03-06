package com.jojoldu.book.springboot.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    EntityManager entityManager;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    @Transactional
    public void save_result() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("cth8787@gmail.com")
                .build());

        //when
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPosts qPosts = new QPosts("posts");

        Posts result = queryFactory
                .selectFrom(qPosts)
                .fetchOne();
//        List<Posts> postsList = postsRepository.findAll();
//
//        //then
//        Posts posts = postsList.get(0);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_save() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 2, 24, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title2")
                .content("content2")
                .author("cth8787@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>>>> createdDate : " + posts.getCreatedDate());
        System.out.println(">>>>>>>>>>> ModifiedDate : " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
