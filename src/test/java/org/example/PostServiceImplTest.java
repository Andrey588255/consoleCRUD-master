package org.example;

import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.model.Writer;
import org.example.repository.PostRepository;
import org.example.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)

class PostServiceImplTest {
    private final PostRepository postMock = Mockito.mock(PostRepository.class);
    private final PostServiceImpl postService = new PostServiceImpl(postMock);
    private ArgumentCaptor<Long> postIdCaptor;
    private ArgumentCaptor<Long> writerIdCaptor;
    private ArgumentCaptor<Post> postCaptor;
    private long testPostId;
    private long testWriterId;
    private Post testPost;
    private Writer testWriter;
    private List<Post> testPostList;

    @BeforeEach
    void setUp() {
        Mockito.reset(postMock);
        testPostId = 10L;
        testWriterId = 100L;
        testPost = new Post(testPostId, "testContent", LocalDateTime.now());
        testPost.setStatus(PostStatus.DELETED);

        testWriter = new Writer(testWriterId, "testName", "testSoName");
        testWriter.addPost(testPost);
        postIdCaptor = ArgumentCaptor.forClass(Long.class);
        writerIdCaptor = ArgumentCaptor.forClass(Long.class);
        postCaptor = ArgumentCaptor.forClass(Post.class);
        testPostList = List.of(testPost);
    }

    @Test
    public void removePostTest() {
        postService.remove(testPostId);
        Mockito.verify(postMock).delete(postIdCaptor.capture());
        assertEquals(testPostId, postIdCaptor.getValue());
    }

    @Test
    public void addPostTest() {
        Mockito.when(postMock.save(testPost, testWriterId)).thenReturn(testPostId);
        Long id = postService.add(testPost, testWriterId);
        Mockito.verify(postMock).save(postCaptor.capture(), writerIdCaptor.capture());
        assertEquals(testPost, postCaptor.getValue());
        assertEquals(testWriterId, writerIdCaptor.getValue());
        assertEquals(testPostId, id);
    }

    @Test
    public void updatePostTest() {
        Mockito.when(postMock.save(testPost, testWriterId)).thenReturn(testPostId);
        Long id = postService.update(testPost, testWriterId);
        Mockito.verify(postMock).save(postCaptor.capture(), writerIdCaptor.capture());
        assertEquals(testPost, postCaptor.getValue());
        assertEquals(testWriterId, writerIdCaptor.getValue());
        assertEquals(testPostId, id);
    }

    @Test
    public void getPostTest() {
        Mockito.when(postMock.get(testPostId)).thenReturn(testPost);
        Post currentPost = postService.get(testPostId);
        Mockito.verify(postMock).get(postIdCaptor.capture());
        assertEquals(testPostId, postIdCaptor.getValue());
        assertEquals(testPost, currentPost);
    }

    @Test
    public void getByWriterIdTest() {
        Mockito.when(postMock.getByWriterId(testWriterId)).thenReturn(testPostList);
        List<Post> postsList = postService.getByWriterId(testWriterId);
        Mockito.verify(postMock).getByWriterId(writerIdCaptor.capture());
        assertEquals(testPostList, postsList);
        assertEquals(testWriterId, writerIdCaptor.getValue());
    }
}
