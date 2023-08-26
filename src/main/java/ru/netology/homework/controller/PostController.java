package ru.netology.homework.controller;

import com.google.gson.Gson;
import ru.netology.homework.exception.PostNotFoundException;
import ru.netology.homework.model.Post;
import ru.netology.homework.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var data = service.getById(id);
            response.getWriter().print(gson.toJson(data));
        } catch (PostNotFoundException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(gson.toJson("Post with ID: " + id + " not found."));
        }
    }


    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        }
        catch (PostNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(gson.toJson("Post not found."));
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            service.removeById(id);
            response.getWriter().print(gson.toJson("Post with ID: " + id + " was removed."));
        } catch (PostNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(gson.toJson("Post with ID: " + id + " not found."));
        }
    }

}