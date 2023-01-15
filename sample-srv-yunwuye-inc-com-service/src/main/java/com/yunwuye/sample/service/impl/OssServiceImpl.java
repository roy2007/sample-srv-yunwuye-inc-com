package com.yunwuye.sample.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.yunwuye.sample.service.OssService;

/**
 *
 * @author Roy
 *
 * @date 2023年1月15日-下午3:03:31
 */
public class OssServiceImpl implements OssService{

    public void multipartUpload () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建InitiateMultipartUploadRequest对象。
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest (bucketName, objectName);
            // 如果需要在初始化分片时设置请求头，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // 指定该Object的网页缓存行为。
            // metadata.setCacheControl("no-cache");
            // 指定该Object被下载时的名称。
            // metadata.setContentDisposition("attachment;filename=oss_MultipartUpload.txt");
            // 指定该Object的内容编码格式。
            // metadata.setContentEncoding(OSSConstants.DEFAULT_CHARSET_NAME);
            // 指定初始化分片上传时是否覆盖同名Object。此处设置为true，表示禁止覆盖同名Object。
            // metadata.setHeader("x-oss-forbid-overwrite", "true");
            // 指定上传该Object的每个part时使用的服务器端加密方式。
            // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_ENCRYPTION, ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION);
            // 指定Object的加密算法。如果未指定此选项，表明Object使用AES256加密算法。
            // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_DATA_ENCRYPTION,
            // ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION);
            // 指定KMS托管的用户主密钥。
            // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_ENCRYPTION_KEY_ID, "9468da86-3509-4f8d-a61e-6eab1eac****");
            // 指定Object的存储类型。
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard);
            // 指定Object的对象标签，可同时设置多个标签。
            // metadata.setHeader(OSSHeaders.OSS_TAGGING, "a:1");
            // request.setObjectMetadata(metadata);
            // 初始化分片。
            InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload (request);
            // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
            String uploadId = upresult.getUploadId ();
            // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
            List<PartETag> partETags = new ArrayList<PartETag> ();
            // 每个分片的大小，用于计算文件有多少个分片。单位为字节。
            final long partSize = 1 * 1024 * 1024L; // 1 MB。
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            final File sampleFile = new File ("D:\\localpath\\examplefile.txt");
            long fileLength = sampleFile.length ();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            // 遍历分片上传。
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                InputStream instream = new FileInputStream (sampleFile);
                // 跳过已经上传的分片。
                instream.skip (startPos);
                UploadPartRequest uploadPartRequest = new UploadPartRequest ();
                uploadPartRequest.setBucketName (bucketName);
                uploadPartRequest.setKey (objectName);
                uploadPartRequest.setUploadId (uploadId);
                uploadPartRequest.setInputStream (instream);
                // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
                uploadPartRequest.setPartSize (curPartSize);
                // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
                uploadPartRequest.setPartNumber (i + 1);
                // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                UploadPartResult uploadPartResult = ossClient.uploadPart (uploadPartRequest);
                // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
                partETags.add (uploadPartResult.getPartETag ());
            }
            // 创建CompleteMultipartUploadRequest对象。
            // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest (
                            bucketName, objectName, uploadId, partETags);
            // 如果需要在完成分片上传的同时设置文件访问权限，请参考以下示例代码。
            // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.Private);
            // 指定是否列举当前UploadId已上传的所有Part。如果通过服务端List分片数据来合并完整文件时，以上CompleteMultipartUploadRequest中的partETags可为null。
            // Map<String, String> headers = new HashMap<String, String>();
            // 如果指定了x-oss-complete-all:yes，则OSS会列举当前UploadId已上传的所有Part，然后按照PartNumber的序号排序并执行CompleteMultipartUpload操作。
            // 如果指定了x-oss-complete-all:yes，则不允许继续指定body，否则报错。
            // headers.put("x-oss-complete-all","yes");
            // completeMultipartUploadRequest.setHeaders(headers);
            // 完成分片上传。
            CompleteMultipartUploadResult completeMultipartUploadResult = ossClient
                            .completeMultipartUpload (completeMultipartUploadRequest);
            System.out.println (completeMultipartUploadResult.getETag ());
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (ClientException ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void breakpointContinuationUpload () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            ObjectMetadata meta = new ObjectMetadata ();
            // 指定上传的内容类型。
            meta.setContentType ("text/plain");
            // 文件上传时设置访问权限ACL。
            // meta.setObjectAcl(CannedAccessControlList.Private);
            // 通过UploadFileRequest设置多个参数。
            // 依次填写Bucket名称（例如examplebucket）以及Object完整路径（例如exampledir/exampleobject.txt），Object完整路径中不能包含Bucket名称。
            UploadFileRequest uploadFileRequest = new UploadFileRequest ("examplebucket",
                            "exampledir/exampleobject.txt");
            // 通过UploadFileRequest设置单个参数。
            // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            uploadFileRequest.setUploadFile ("D:\\localpath\\examplefile.txt");
            // 指定上传并发线程数，默认值为1。
            uploadFileRequest.setTaskNum (5);
            // 指定上传的分片大小，单位为字节，取值范围为100 KB~5 GB。默认值为100 KB。
            uploadFileRequest.setPartSize (1 * 1024 * 1024);
            // 开启断点续传，默认关闭。
            uploadFileRequest.setEnableCheckpoint (true);
            // 记录本地分片上传结果的文件。上传过程中的进度信息会保存在该文件中，如果某一分片上传失败，再次上传时会根据文件中记录的点继续上传。上传完成后，该文件会被删除。
            // 如果未设置该值，默认与待上传的本地文件同路径，名称为${uploadFile}.ucp。
            uploadFileRequest.setCheckpointFile ("yourCheckpointFile");
            // 文件的元数据。
            uploadFileRequest.setObjectMetadata (meta);
            // 设置上传回调，参数为Callback类型。
            // uploadFileRequest.setCallback("yourCallbackEvent");
            // 断点续传上传。
            ossClient.uploadFile (uploadFileRequest);
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (Throwable ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void breakpointContinuationDownload () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            // 请求10个任务并发下载。
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest (bucketName, objectName);
            // 指定Object下载到本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
            downloadFileRequest.setDownloadFile ("D:\\localpath\\examplefile.txt");
            // 设置分片大小，单位为字节，取值范围为100 KB~5 GB。默认值为100 KB。
            downloadFileRequest.setPartSize (1 * 1024 * 1024);
            // 设置分片下载的并发数，默认值为1。
            downloadFileRequest.setTaskNum (10);
            // 开启断点续传下载，默认关闭。
            downloadFileRequest.setEnableCheckpoint (true);
            // 设置断点记录文件的完整路径，例如D:\\localpath\\examplefile.txt.dcp。
            // 只有当Object下载中断产生了断点记录文件后，如果需要继续下载该Object，才需要设置对应的断点记录文件。下载完成后，该文件会被删除。
            // downloadFileRequest.setCheckpointFile("D:\\localpath\\examplefile.txt.dcp");
            // 下载文件。
            DownloadFileResult downloadRes = ossClient.downloadFile (downloadFileRequest);
            // 下载成功时，会返回文件元信息。
            ObjectMetadata objectMetadata = downloadRes.getObjectMetadata ();
            System.out.println (objectMetadata.getETag ());
            System.out.println (objectMetadata.getLastModified ());
            System.out.println (objectMetadata.getUserMetadata ().get ("meta"));
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (Throwable ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void createBucket () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建存储空间。
            ossClient.createBucket (bucketName);
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (ClientException ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void uploadOssFile () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            String content = "Hello OSS";
            ossClient.putObject (bucketName, objectName, new ByteArrayInputStream (content.getBytes ()));
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (ClientException ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void downloadOssFile () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
            OSSObject ossObject = ossClient.getObject (bucketName, objectName);
            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
            InputStream content = ossObject.getObjectContent ();
            if (content != null) {
                BufferedReader reader = new BufferedReader (new InputStreamReader (content));
                while (true) {
                    String line = reader.readLine ();
                    if (line == null)
                        break;
                    System.out.println ("\n" + line);
                }
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                content.close ();
            }
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (ClientException ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }

    public void listOSSFile () {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ().build (endpoint, accessKeyId, accessKeySecret);
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects (bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries ()) {
                System.out.println (" - " + objectSummary.getKey () + "  " +
                                "(size = " + objectSummary.getSize () + ")");
            }
        } catch (OSSException oe) {
            System.out.println ("Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.");
            System.out.println ("Error Message:" + oe.getErrorMessage ());
            System.out.println ("Error Code:" + oe.getErrorCode ());
            System.out.println ("Request ID:" + oe.getRequestId ());
            System.out.println ("Host ID:" + oe.getHostId ());
        } catch (ClientException ce) {
            System.out.println ("Caught an ClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with OSS, "
                            + "such as not being able to access the network.");
            System.out.println ("Error Message:" + ce.getMessage ());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown ();
            }
        }
    }
}
