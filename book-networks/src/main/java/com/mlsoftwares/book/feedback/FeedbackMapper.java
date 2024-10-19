package com.mlsoftwares.book.feedback;

import com.mlsoftwares.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback tofeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .archived(false)
                        .shareable(false)
                        .build())
                .build();
    }

    public FeedbackResponse tofeedbackResponse(Feedback f, Integer id) {
       return FeedbackResponse.builder()
               .note(f.getNote())
               .comment(f.getComment())
               .ownFeedback(Objects.equals(f.getCreatedBy(), id))
                .build();
    }
}
