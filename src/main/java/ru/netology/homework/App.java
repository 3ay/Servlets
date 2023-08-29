package ru.netology.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.homework.controller.PostController;
import ru.netology.homework.repository.PostRepository;
import ru.netology.homework.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class App extends HttpServlet {
    private PostController controller;
    private ApplicationContext context;
    private static final String POSTS_API_REGEX = "/api/posts";
    private static final String POSTS_API_ID_REGEX = "/api/posts/\\d+";

    @Override
    public void init() throws ServletException {
        super.init();
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (isAllPostsRequest(method, path)) {
                controller.all(resp);
                return;
            }

            if (isSpecificPostRequest(method, path)) {
                final var id = extractIdFromPath(path);
                controller.getById(id, resp);
                return;
            }

            if (isSavePostRequest(method, path)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (isDeletePostRequest(method, path)) {
                final var id = extractIdFromPath(path);
                controller.removeById(id, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isAllPostsRequest(String method, String path) {
        return method.equals("GET") && path.matches(POSTS_API_REGEX);
    }

    private boolean isSpecificPostRequest(String method, String path) {
        return method.equals("GET") && path.matches(POSTS_API_ID_REGEX);
    }

    private boolean isSavePostRequest(String method, String path) {
        return method.equals("POST") && path.equals(POSTS_API_REGEX);
    }

    private boolean isDeletePostRequest(String method, String path) {
        return method.equals("DELETE") && path.matches(POSTS_API_ID_REGEX);
    }

    private long extractIdFromPath(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}
