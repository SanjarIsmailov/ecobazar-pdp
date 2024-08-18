package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.ecommerce.entity.BlogComment;
import uz.pdp.ecommerce.exeptions.ResourceNotFoundException;
import uz.pdp.ecommerce.repo.BlockCommentRepository;
import uz.pdp.ecommerce.repo.BlogRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogCommentService {
    private final BlockCommentRepository blogCommentRepository;

        private final BlogRepository blogRepository;

        public List<BlogComment> getCommentsByBlog(UUID blogId) {
            return blogCommentRepository.findByBlogId(blogId);
        }

        public BlogComment getCommentById(UUID id) {
            return blogCommentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        }

        @Transactional
        public BlogComment createComment(BlogComment blogComment) {
            if (!blogRepository.existsById(blogComment.getBlog().getId())) {
                throw new ResourceNotFoundException("Blog not found with id " + blogComment.getBlog().getId());
            }
            return blogCommentRepository.save(blogComment);
        }

        @Transactional
        public BlogComment updateComment(UUID id, BlogComment blogComment) {
            BlogComment existingComment = getCommentById(id);
            existingComment.setAuthor(blogComment.getAuthor());
            existingComment.setBlog(blogComment.getBlog());
            existingComment.setCreatedAt(blogComment.getCreatedAt());
            return blogCommentRepository.save(existingComment);
        }

        @Transactional
        public void deleteComment(UUID id) {
            if (!blogCommentRepository.existsById(id)) {
                throw new ResourceNotFoundException("Comment not found with id " + id);
            }
            blogCommentRepository.deleteById(id);
        }
    }
