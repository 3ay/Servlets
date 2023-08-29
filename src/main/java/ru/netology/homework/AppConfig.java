package ru.netology.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.netology.homework.controller.PostController;
import ru.netology.homework.repository.PostRepository;
import ru.netology.homework.service.PostService;

@Configuration
@ComponentScan(basePackages = "ru.netology.homework")
public class AppConfig {
}
