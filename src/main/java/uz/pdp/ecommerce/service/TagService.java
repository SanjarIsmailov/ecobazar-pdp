package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Tag;
import uz.pdp.ecommerce.repo.TagRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(UUID id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(UUID id, Tag tag) {
        return tagRepository.findById(id)
                .map(existingTag -> {
                    existingTag.setName(tag.getName());
                    existingTag.setReview(tag.getReview());
                    return tagRepository.save(existingTag);
                }).orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }

    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }
}
