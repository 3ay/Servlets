package ru.netology.homework.repository;

import ru.netology.homework.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {

    private static ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private static AtomicInteger count = new AtomicInteger(posts.size());

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = count.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
            return post;
        } else {
            return posts.compute(post.getId(), (id, existingPost) -> {
                if (existingPost != null) {
                    existingPost.setContent(post.getContent());
                    existingPost.setPublicSend(post.isPublicSend());
                    return existingPost;
                } else {
                    return null;
                }
            });
        }
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
