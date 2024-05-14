package ch.zhaw.deeplearningjava.playground;

import com.azure.storage.blob.*;

import java.io.File;


public class UploadToAzureBlobStorage {

    public static void main(String[] args) {
        String connectionString = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
        String containerName = "ulricdo1emotiondetection";
        String sourceDir = "models";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        uploadFiles(containerClient, sourceDir);
    }

    private static void uploadFiles(BlobContainerClient containerClient, String sourceDir) {
        File dir = new File(sourceDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".params") || name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                BlobClient blobClient = containerClient.getBlobClient(file.getName());
                blobClient.uploadFromFile(file.getAbsolutePath(), true);
                System.out.println("Uploaded file: " + file.getName());
            }
        }
    }
}
