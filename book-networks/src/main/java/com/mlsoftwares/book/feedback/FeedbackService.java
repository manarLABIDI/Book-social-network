package com.mlsoftwares.book.feedback;

import com.mlsoftwares.book.book.Book;
import com.mlsoftwares.book.book.BookRepository;
import com.mlsoftwares.book.common.PageResponse;
import com.mlsoftwares.book.exception.OperationNotPermittedException;
import com.mlsoftwares.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID::" + request.bookId()));
        if(book.isArchived() || !book.isShareable()){
            throw  new OperationNotPermittedException("ou cannot give feedback for an archived or not shareable book");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot  give feedback for your own book!");

        }
        Feedback feedback = feedbackMapper.tofeedback(request);
        return  feedbackRepository.save(feedback).getId();

    }

    public PageResponse<FeedbackResponse> findAllFeedBackByBook(Integer bookId, Integer page, Integer size, Authentication connectedUser) {

        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback>  feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f ->feedbackMapper.tofeedbackResponse(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );


    }
}
