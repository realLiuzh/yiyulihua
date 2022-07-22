package com.yiyulihua.oss.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.VoidResult;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.yiyulihua.common.utils.DateUtils;
import com.yiyulihua.common.utils.RRException;
import com.yiyulihua.oss.service.OssService;
import com.yiyulihua.oss.utils.AudioUtils;
import com.yiyulihua.oss.utils.ConstantPropertiesUtils;
import com.yiyulihua.oss.utils.FileUtils;
import com.yiyulihua.oss.utils.ImageWaterMarkUtil;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @author sunbo
 * @date 2022-07-18-18:25
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Override
    public Map<String, Object> uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return null;
        }
        String type = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        switch (type) {
            case ".png":
            case ".jpg":
            case ".jpeg":
                return uploadImage(file);
            case ".doc":
            case ".docx":
                return uploadWord(file);
            case ".mp3":
            case ".flac":
                return uploadAudio(file);
            default:
                throw new RRException("文件格式错误");
        }
    }

    private Map<String, Object> uploadAudio(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return null;
        }
        String prefix = "tempAudio";
        String suffix = filename.substring(filename.lastIndexOf("."));
        File tempFile = null;
        String realUrl;
        String previewUrl;
        Integer duration;
        File source = null;
        try {
            // 生成临时文件
            tempFile = File.createTempFile(prefix, ".mp3");
            // MultipartFile转File
            source = FileUtils.multipartFileToFile(file);

            //时长
            duration = AudioUtils.getTime(source);
            if (".flac".equals(suffix)) {
                // flac 格式转为mp3
                File tempMp3 = File.createTempFile("tempMp3", ".mp3");
                AudioUtils.ConvertingAnyAudioToMp3WithAProgressListener(source, tempMp3);
                // 截取1/3 或 40s
                AudioUtils.cut(tempMp3, tempFile, duration);
                previewUrl = upload(createFileName("audioPreview", filename + ".mp3"), Files.newInputStream(tempFile.toPath()));
                //删除临时文件
                if (!tempMp3.delete()) {
                    tempMp3.deleteOnExit();
                }
            } else if (".mp3".equals(suffix)) {
                assert source != null;
                AudioUtils.cut(source, tempFile, duration);
                previewUrl = upload(createFileName("audioPreview", filename), Files.newInputStream(tempFile.toPath()));
            } else {
                throw new RRException("格式错误");
            }

            realUrl = upload(createFileName("audio", filename), Files.newInputStream(source.toPath()));

            Map<String, Object> map = new HashMap<>();
            map.put("realUrl", realUrl);
            map.put("previewUrl", previewUrl);
            map.put("duration", duration);

            return map;
        } catch (IOException | CannotReadException | TagException | InvalidAudioFrameException |
                 ReadOnlyFileException e) {
            throw new RuntimeException(e);
        } finally {
            //删除临时文件
            assert source != null;
            if (!source.delete()) {
                source.deleteOnExit();
            }
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    private Map<String, Object> uploadImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return null;
        }
        //文件格式
        String suffix = filename.substring(filename.lastIndexOf("."));
        File tempFile = null;
        String imageUrl;
        String previewImageUrl;
        String resolution;


        try {
            //生成临时文件
            String prefix = "tempImage";
            tempFile = File.createTempFile(prefix, suffix);

            //加水印
            BufferedImage bufferedImage = ImageWaterMarkUtil.addFullTextWaterMark(file.getInputStream(), "一隅立画", suffix.substring(1));

            ImageIO.write(bufferedImage, suffix.substring(1), tempFile);

            //上传oss
            imageUrl = upload(createFileName("image", filename), file.getInputStream());
            previewImageUrl = upload(createFileName("previewImage", filename), Files.newInputStream(tempFile.toPath()));

            //获取像素
            resolution = ImageWaterMarkUtil.getResolution(file.getInputStream());

            Map<String, Object> map = new HashMap<>();
            map.put("realUrl", imageUrl);
            map.put("previewUrl", previewImageUrl);
            map.put("resolution", resolution);

            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //删除临时文件
            assert tempFile != null;
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }


    private Map<String, Object> uploadWord(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return null;
        }
        //文件后缀
        String prefix = "tempWord";
        String suffix = filename.substring(filename.lastIndexOf("."));
        File tempFile = null;
        int wordCount;
        String fileUrl;
        String previewUrl;

        try {
            //临时文件
            tempFile = File.createTempFile(prefix, suffix);

            //处理word
            Document document = new Document();
            if (".doc".equals(suffix)) {
                document.loadFromStream(file.getInputStream(), FileFormat.Doc);
            } else if (".docx".equals(suffix)) {
                document.loadFromStream(file.getInputStream(), FileFormat.Docx);
            }
            //获取文本
            String text = document.getText();
            //统计字数
            wordCount = text.replaceAll(" ", "").length();
            //删除后半部分
            int begin;
            if (text.length() >= 4000) {
                begin = 1000;
            } else {
                begin = text.length() / 4;
            }
            String removeString = text.substring(begin);

            String[] split = removeString.split("\r\n");
            if (!StringUtils.isEmpty(split[0])) {
                document.replace(split[0], "\r\n购买查看后续...", true, true);
            }
            for (int i = 1; i < split.length; i++) {
                if (!StringUtils.isEmpty(split[i])) {
                    document.replace(split[i], "", true, true);
                }
            }

            //保存到临时文件
            document.saveToFile(tempFile.getPath());
            document.dispose();

            // 上传
            fileUrl = upload(createFileName("word", filename), file.getInputStream());
            previewUrl = upload(createFileName("previewWord", filename), Files.newInputStream(tempFile.toPath()));

            Map<String, Object> map = new HashMap<>();
            map.put("realUrl", fileUrl);
            map.put("previewUrl", previewUrl);
            map.put("wordCount", wordCount);

            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert tempFile != null;
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }


    }

    private String createFileName(String prefix, String filename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String datePath = DateUtils.format(new Date(), "yyyy/MM/dd");
        return prefix + "/" + datePath + "/" + uuid + filename;
    }

    private String upload(String filename, InputStream inputStream) {
        OSS ossClient = new OSSClientBuilder().build(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        try {
            // 创建PutObject请求。
            ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME, filename, inputStream);
            return "https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT + "/" + filename;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason." +
                    "\nError Message:" + oe.getErrorMessage() +
                    "\nError Code:" + oe.getErrorCode() +
                    "\nRequest ID:" + oe.getRequestId() +
                    "\nHost ID:" + oe.getHostId()
            );
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public boolean removeFile(String url) {
        boolean flag = false;
        OSS ossClient = null;
        try {
            String filename = url.substring(("https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT + "/").length());
            ossClient = new OSSClientBuilder().build(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            VoidResult voidResult = ossClient.deleteObject(ConstantPropertiesUtils.BUCKET_NAME, filename);
            if (voidResult.getResponse().getStatusCode() == 204) {
                flag = true;
            }
            return flag;
        } catch (OSSException | ClientException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }

    @Override
    public List<String> removedFiles(List<String> urls) {
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            // 创建删除请求
            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(ConstantPropertiesUtils.BUCKET_NAME);
            //添加要删除的文件名
            urls.replaceAll(s -> s.substring(("https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT + "/").length()));

            deleteRequest.setKeys(urls);
            //调用删除
            DeleteObjectsResult objectsResult = ossClient.deleteObjects(deleteRequest);
            // 返回已删除
            return objectsResult.getDeletedObjects();
        } catch (OSSException | ClientException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
