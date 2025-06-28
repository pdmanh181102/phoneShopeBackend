package tttn_2025.phoneShop.common.services.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${file.upload.dir:uploads}")
    private String uploadDir;

    @Value("${file.max.size:5242880}")
    private long maxFileSize;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif",
            "image/bmp", "image/webp");

    public String uploadImage(MultipartFile file) throws IOException {
        // Validate file
        validateFile(file);

        // Create upload directory if not exists
        createUploadDirectory();

        // Generate unique filename
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        // Create file path
        Path filePath = Paths.get(uploadDir, fileName);

        // Copy file to destination
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public String uploadImageWithFolder(MultipartFile file, String folder) throws IOException {
        // Validate file
        validateFile(file);

        // Create upload directory with subfolder
        String fullUploadDir = uploadDir + File.separator + folder;
        createDirectory(fullUploadDir);

        // Generate unique filename
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        // Create file path
        Path filePath = Paths.get(fullUploadDir, fileName);

        // Copy file to destination
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return folder + "/" + fileName;
    }

    public boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        return Files.readAllBytes(filePath);
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new IOException("File size exceeds maximum allowed size of " + maxFileSize + " bytes");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Original filename is null");
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IOException("File extension not allowed. Allowed: " + ALLOWED_EXTENSIONS);
        }

        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new IOException("MIME type not allowed. Allowed: " + ALLOWED_MIME_TYPES);
        }
    }

    private void createUploadDirectory() throws IOException {
        createDirectory(uploadDir);
    }

    private void createDirectory(String dir) throws IOException {
        Path uploadPath = Paths.get(dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + "." + extension;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
