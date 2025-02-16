package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.entity.Media;
import al.golocal.entity.User;
import al.golocal.repository.MediaRepository;
import al.golocal.service.JwtService;
import al.golocal.service.MediaService;
import al.golocal.service.UserService;
import al.golocal.service.storage.MinioStorageService;
import al.golocal.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MediaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MediaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

    @MockBean
    private JwtService jwtService;

    @Test
    void uploadMediaTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "file content".getBytes());
        Media mockMedia = new Media();
        mockMedia.setMediaId(1L);
        mockMedia.setUrl("http://example.com/media/1");

        when(mediaService.uploadFile(Mockito.any(MultipartFile.class),Mockito.anyLong(),Mockito.anyString(),Mockito.anyBoolean(),Mockito.anyString())).thenReturn(mockMedia);
        when(mediaService.prepareFullUrl(anyString())).thenReturn("http://example.com/media/1");

        // Act & Assert
        mockMvc.perform(multipart("/api/media")
                        .file(file)
                        .param("refId", "1")
                        .param("refTable", "product")
                        .param("main", "true")
                        .param("filename", "test.jpg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.url").value("http://example.com/media/1"))
                .andExpect(jsonPath("$.message").value("Media uploaded successfully"));

        verify(mediaService, times(1)).uploadFile(any(), any(), any(), any(), any());

    }

}