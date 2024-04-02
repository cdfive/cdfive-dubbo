package com.cdfive.mp3.controller;

import com.cdfive.framework.util.FileUtil;
import com.cdfive.mp3.exception.Mp3ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author cdfive
 */
@Slf4j
@RestController
public class UploadController {

    @ResponseBody
    @PostMapping("/api/v1/mp3/upload")
    public String upload(HttpServletRequest request) {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new Mp3ServiceException("请发送HTTP的multipart/form-data请求上传文件");
        }

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        if (fileMap == null || fileMap.size() == 0) {
            throw new Mp3ServiceException("至少上传一个文件");
        }
        if (fileMap.size() > 1) {
            throw new Mp3ServiceException("一次只能传一个文件");
        }
        Set<Map.Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
        Map.Entry<String, MultipartFile> entry = entrySet.iterator().next();
        String fileKey = entry.getKey();
        String[] mp3FileKeys = new String[]{"fileMp3", "fileLrc", "fileKrc"};
        if (!Arrays.asList(mp3FileKeys).contains(fileKey)) {
            throw new Mp3ServiceException("非法上传文件");
        }

        MultipartFile multipartFile = entry.getValue();

        String oriFileName = multipartFile.getOriginalFilename();
        String oriFileNameWithoutExt = FileUtil.getFileNameWithoutExt(oriFileName);
        String[] tmpNames = oriFileNameWithoutExt.split("-");
        String name = tmpNames[0].trim();
        String path = "/mp3" + "/" + name.length() + "/" + oriFileName;
        String savefilePath = "/Users/cdfive/learning/upload/" + name.length() + "/" + oriFileName;

        File file = new File(savefilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            log.error("上传文件传输错误", e);
            throw new Mp3ServiceException("上传文件传输错误");
        }

        return path;
    }
}
