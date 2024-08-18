package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Blog;
import uz.pdp.ecommerce.repo.BlogRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Blog> getAllBlogs(int page, int size) {
        return blogRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Blog getBlogById(UUID id) {
        return blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(UUID id, Blog blog) {
        Blog existingBlog = getBlogById(id);
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setDescription(blog.getDescription());
        return blogRepository.save(existingBlog);
    }

    public void deleteBlog(UUID id) {
        blogRepository.deleteById(id);
    }
}
