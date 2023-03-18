package com.knoldus.radarservice.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.knoldus.radarservice.exception.CSVUploadException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class CSVHelperTest {
    /**
     * Method under test: {@link CSVHelper#hasCSVFormat(MultipartFile)}
     */
    @Test
    void testHasCSVFormat() throws IOException {
        assertFalse(
                CSVHelper.hasCSVFormat(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
    }

    /**
     * Method under test: {@link CSVHelper#csvToTechnologies(InputStream)}
     */
    @Test
    void testCsvToTechnologies() throws UnsupportedEncodingException {
        assertTrue(CSVHelper.csvToTechnologies(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))).isEmpty());
        assertTrue(
                CSVHelper.csvToTechnologies(new ByteArrayInputStream(new byte[]{1, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}))
                        .isEmpty());
        assertTrue(CSVHelper.csvToTechnologies(new ByteArrayInputStream(new byte[]{})).isEmpty());
    }

    /**
     * Method under test: {@link CSVHelper#csvToTechnologies(InputStream)}
     */
    @Test
    void testCsvToTechnologies2() throws IOException {
        DataInputStream dataInputStream = mock(DataInputStream.class);
        doNothing().when(dataInputStream).close();
        assertThrows(CSVUploadException.class, () -> CSVHelper.csvToTechnologies(dataInputStream));
        verify(dataInputStream).close();
    }

    /**
     * Method under test: {@link CSVHelper#csvToTechnologies(InputStream)}
     */
    @Test
    void testCsvToTechnologies3() throws IOException {
        DataInputStream dataInputStream = mock(DataInputStream.class);
        doThrow(new CSVUploadException("An error occurred")).when(dataInputStream).close();
        assertThrows(CSVUploadException.class, () -> CSVHelper.csvToTechnologies(dataInputStream));
        verify(dataInputStream).close();
    }
}

