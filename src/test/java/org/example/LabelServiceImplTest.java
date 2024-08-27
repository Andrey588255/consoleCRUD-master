package org.example;

import java.util.List;
import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.service.LabelServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LabelServiceImplTest {
    @Mock
    private LabelRepository labelMock;
    @InjectMocks
    LabelServiceImpl labelService = new LabelServiceImpl();
    private ArgumentCaptor<Label> labelCaptor;
    private ArgumentCaptor<Long> postIdCaptor;
    private ArgumentCaptor<Long> labelIdCaptor;

    private List<Label> testLabelsList;
    private Label testLabel;
    private Long postId;
    private Long labelId;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(labelMock);
        testLabel = new Label("testName");
        postId = 10L;
        labelId = 100L;
        labelCaptor = ArgumentCaptor.forClass(Label.class);
        postIdCaptor = ArgumentCaptor.forClass(Long.class);
        labelIdCaptor = ArgumentCaptor.forClass(Long.class);
        testLabelsList = List.of(testLabel);
    }

    @Test
    void addLabelTest() {
        Mockito.when(labelMock.save(testLabel, postId)).thenReturn(labelId);
        Long id = labelService.add(testLabel, postId);
        Mockito.verify(labelMock).save(labelCaptor.capture(), postIdCaptor.capture());
        assertEquals(testLabel, labelCaptor.getValue());
        assertEquals(postId, postIdCaptor.getValue());
        assertEquals(labelId, id);
    }

    @Test
    void removeLabelByIdTest() {
        labelService.remove(postId);
        Mockito.verify(labelMock).delete(postIdCaptor.capture());
        assertEquals(postId, postIdCaptor.getValue());
    }

    @Test
    void getLabelTest() {
        Mockito.when(labelMock.get(labelId)).thenReturn(testLabel);
        Label currentLabel = labelService.get(labelId);
        Mockito.verify(labelMock).get(labelIdCaptor.capture());
        assertEquals(labelId, labelIdCaptor.getValue());
        assertEquals(testLabel, currentLabel);
    }

    @Test
    void getByPostIdTest() {
        Mockito.when(labelMock.getByPostId(postId)).thenReturn(testLabelsList);
        List<Label> labelsList = labelService.getByPostId(postId);
        Mockito.verify(labelMock).getByPostId(postIdCaptor.capture());
        assertEquals(testLabelsList, labelsList);
        assertEquals(postId, postIdCaptor.getValue());
    }

    @Test
    void updateLabelTest() {
        Mockito.when(labelMock.save(testLabel, postId)).thenReturn(labelId);
        Long id = labelService.update(testLabel, postId);
        Mockito.verify(labelMock).save(labelCaptor.capture(), postIdCaptor.capture());
        assertEquals(testLabel, labelCaptor.getValue());
        assertEquals(postId, postIdCaptor.getValue());
        assertEquals(labelId, id);
    }
}