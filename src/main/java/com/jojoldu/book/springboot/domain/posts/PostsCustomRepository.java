package com.jojoldu.book.springboot.domain.posts;

import java.util.List;

public interface PostsCustomRepository {
    List<Posts> findAllDesc();
}
