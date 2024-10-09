package com.taskflow.task_service.Service.ClientService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileServiceClient {
    @Autowired
    RestTemplate restTemplate;

    public String uploadFileUsingFileService(MultipartFile file) throws Exception {
        String attachmentId = null;
        String url = "http://file-service/file/upload";
        Resource fileUploadResource = file.getResource();
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("file",fileUploadResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,requestEntity,Object.class);
        if(response.getStatusCode().is2xxSuccessful())
        {
           attachmentId = fetchAttachmentIdFromObject(response.getBody());
        }
        else
          throw new Exception("Failed to Upload file");
        return attachmentId;
    }

    public String fetchAttachmentIdFromObject(Object object)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> objectMap = objectMapper.convertValue(object, Map.class);
        String attachmentId =  (String) objectMap.get("fileId");
        return attachmentId;
    }

    public ResponseEntity<Resource> downloadFileUsingFileService(String attachmentId)
    {
        String url = "http://file-service/file/download{fileId}";
        return restTemplate.exchange(url,HttpMethod.GET,null,Resource.class,attachmentId);
    }
}
