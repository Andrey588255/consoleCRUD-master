package org.example;

import org.example.model.Writer;
import org.example.repository.WriterRepository;
import org.example.service.WriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WriterServiceImplTest {
    @Mock
    private WriterRepository writerMock;
    @InjectMocks
    private WriterServiceImpl writerService = new WriterServiceImpl();
    private ArgumentCaptor<Writer> writerCaptor;
    private ArgumentCaptor<Long> idCaptor;
    private Writer testWriter;
    private List<Writer> testWritersList;
    private long testWriterId;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(writerMock);
        writerCaptor = ArgumentCaptor.forClass(Writer.class);
        idCaptor = ArgumentCaptor.forClass(Long.class);
        testWriterId = 100L;
        testWriter = new Writer(testWriterId, "testName", "testSoName");
        testWritersList = Arrays.asList(
                new Writer(1L, "test1", "testSoName1"),
                new Writer(2L, "test2", "testSoName2"),
                new Writer(3L, "test3", "testSoName3"));
    }

    @Test
    void removeWriter() {
        writerService.remove(testWriterId);
        Mockito.verify(writerMock).delete(idCaptor.capture());
        assertEquals(testWriterId, idCaptor.getValue());
    }

    @Test
    void addWriterTest() {
        Mockito.when(writerMock.save(testWriter)).thenReturn(testWriterId);
        Long id = writerService.add(testWriter);
        Mockito.verify(writerMock).save(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
        assertEquals(testWriterId, id);
    }

    @Test
    void addWriterTestWithException() {
        Mockito.when(writerMock.save(testWriter, 1L)).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> writerService.add(testWriter, 1L));
    }

    @Test
    void updateWriterTest() {
        Mockito.when(writerMock.save(testWriter)).thenReturn(testWriterId);
        Long id = writerService.update(testWriter);
        Mockito.verify(writerMock).save(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
        assertEquals(testWriterId, id);
    }

    @Test
    void getWriter() {
        Mockito.when(writerMock.get(testWriterId)).thenReturn(testWriter);
        Writer currentWriter = writerService.get(testWriterId);
        Mockito.verify(writerMock).get(idCaptor.capture());
        assertEquals(testWriter, currentWriter);
    }

    @Test
    void getAllWriters() {
        Mockito.when(writerMock.getAll()).thenReturn(testWritersList);
        List<Writer> currentList = writerService.getAll();
        Mockito.verify(writerMock).getAll();
        assertEquals(testWritersList, currentList);
    }
}