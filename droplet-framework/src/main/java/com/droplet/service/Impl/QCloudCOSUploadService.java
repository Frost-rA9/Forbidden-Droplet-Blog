package com.droplet.service.Impl;

import com.droplet.domain.ResponseResult;
import com.droplet.enums.AppHttpCodeEnum;
import com.droplet.exception.SystemException;
import com.droplet.service.UploadService;
import com.droplet.utils.PathUtils;
import com.droplet.utils.QCloudCOSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class QCloudCOSUploadService implements UploadService {

    /**
     * 腾讯云 COS 图片上传
     *
     * @param img 图片
     * @return 结果
     */
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        String originalFilename = img.getOriginalFilename();
        assert originalFilename != null;
        if (!originalFilename.endsWith(".png")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
        File imgFile = null;
        try {
            imgFile = File.createTempFile(originalFilename, prefix);
            img.transferTo(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = QCloudCOSUtil.uploadFileTest(imgFile, filePath);
        return ResponseResult.okResult(url);
    }
}
