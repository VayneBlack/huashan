package com.guomin.stater.third.party.aliyun.oss;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.stater.third.party.properties.aliyun.AliyunOssProperties;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

public class AliOssService {
    private AliyunOssProperties aliyunOssProperties;

    public String uploadStr(String fileSavePath, MultipartFile file) throws Exception {
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());

        String saveName = fileSavePath+"/"+file.getOriginalFilename();
        try {
            String bucketName = aliyunOssProperties.getBucketName();
            client.putObject(bucketName, saveName, file.getInputStream());
        } catch (Exception e) {
           throw new BussinessException(-405,"上传阿里OSS储存过程时出错");
        }
        finally {
            client.shutdown();
        }
        return aliyunOssProperties.getHostPrefix()+"/"+saveName;
    }

    public Boolean checkExist(String fileName) throws Exception {
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        boolean result = client.doesObjectExist(aliyunOssProperties.getBucketName(), fileName);
        client.shutdown();
        return result;
    }

    public Boolean checkExistWithPrefix(String fileName) throws Exception {
        String queryName = StrUtil.subAfter(fileName, aliyunOssProperties.getHostPrefix()+"/", false);
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        boolean result = client.doesObjectExist(aliyunOssProperties.getBucketName(), queryName);
        client.shutdown();
        return result;
    }

    public Boolean delFile(String  fileName) throws Exception {
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        try {
            client.deleteObject(aliyunOssProperties.getBucketName(),fileName);
        }catch (Exception e){
            return false;
        }finally {
            client.shutdown();
        }
        return true;
    }

    public Boolean delFileWithPrefix(String  fileWithPrefix) throws Exception {
        String queryName = StrUtil.subAfter(fileWithPrefix, aliyunOssProperties.getHostPrefix()+"/", false);

        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        try {
            client.deleteObject(aliyunOssProperties.getBucketName(),queryName);
        }catch (Exception e){
            return false;
        }finally {
            client.shutdown();
        }
        return true;
    }

    public Boolean delFiles(String filePrefix) throws Exception {
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());

        List<String> keys = getFileList(filePrefix);
        try {
            client.deleteObjects(new DeleteObjectsRequest(aliyunOssProperties.getBucketName()).withKeys(keys));
        }catch (Exception e){
            return false;
        }finally {
            client.shutdown();
        }
        return true;
    }

    public List<String> getFileListWithHostPrefix(String filePrefix)throws Exception{
        ArrayList<String> files = new ArrayList<>();
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());

        try{
            ObjectListing objectListing = client.listObjects(aliyunOssProperties.getBucketName(), filePrefix);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                files.add(aliyunOssProperties.getHostPrefix()+"/"+s.getKey());
            }
        }
        catch (Exception e){
            throw new BussinessException(-405,"获取文件列表失败");
        }
        finally {
            client.shutdown();
        }
        return files;
    }

    public List<String> getFileList(String filePrefix)throws Exception{
        ArrayList<String> files = new ArrayList<>();
        OSS client = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());

        try{
            ObjectListing objectListing = client.listObjects(aliyunOssProperties.getBucketName(), filePrefix);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                files.add(s.getKey());
            }
        }
        catch (Exception e){
            throw new BussinessException(-405,"获取文件列表失败");
        }
        finally {
            client.shutdown();
        }
        return files;
    }

    public void setAliyunOssProperties(AliyunOssProperties aliyunOssProperties) {
        this.aliyunOssProperties = aliyunOssProperties;
    }
}
