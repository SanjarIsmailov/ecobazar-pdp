package uz.pdp.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommerce.entity.Blog;
import uz.pdp.ecommerce.entity.BlogComment;
import uz.pdp.ecommerce.service.BlogCommentService;
import uz.pdp.ecommerce.service.BlogService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final BlogCommentService blogCommentService;

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAllBlogs(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable UUID id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        return ResponseEntity.ok(blogService.createBlog(blog));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable UUID id, @RequestBody Blog blog) {
        return ResponseEntity.ok(blogService.updateBlog(id, blog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable UUID id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comment/{blogId}")
    public ResponseEntity<List<BlogComment>> getCommentsByBlog(@PathVariable UUID blogId) {
        return ResponseEntity.ok(blogCommentService.getCommentsByBlog(blogId));
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<BlogComment> getCommentById(@PathVariable UUID id) {
        return ResponseEntity.ok(blogCommentService.getCommentById(id));
    }

    @PostMapping("/comment")
    public ResponseEntity<BlogComment> createComment(@RequestBody BlogComment blogComment) {
        return ResponseEntity.ok(blogCommentService.createComment(blogComment));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<BlogComment> updateComment(@PathVariable UUID id, @RequestBody BlogComment blogComment) {
        return ResponseEntity.ok(blogCommentService.updateComment(id, blogComment));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        blogCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
