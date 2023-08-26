package ru.netology.homework.service;

import ru.netology.homework.exception.PostNotFoundException;
import ru.netology.homework.model.Post;
import ru.netology.homework.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService (PostRepository repository)
    {
        this.repository = repository;
    }
    public List<Post> all()
    {
        return repository.all();
    }
    public Post getById(long id) throws ClassNotFoundException {
        return repository.getById(id).orElseThrow(ClassNotFoundException::new);
    }
    public Post save(Post post)
    {
        Post result = repository.save(post);
        if(result == null)
        {
            throw new PostNotFoundException("Post не найден по ID: " + post.getId());
        }
        return result;
    }
    public void removeById(Long id)
    {
        repository.removeById(id);
    }
}
